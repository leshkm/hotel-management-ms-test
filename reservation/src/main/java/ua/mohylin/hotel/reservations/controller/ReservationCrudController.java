package ua.mohylin.hotel.reservations.controller;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ua.mohylin.hotel.reservations.api.ReservationService;
import ua.mohylin.hotel.reservations.dto.NewReservationDto;
import ua.mohylin.hotel.reservations.dto.ReservationDto;
import ua.mohylin.hotel.reservations.exception.ReservationException;
import ua.mohylin.hotel.reservations.exception.ReservationNotFoundException;
import ua.mohylin.hotel.reservations.mapping.ReservationMapper;
import ua.mohylin.hotel.reservations.model.Reservation;

/**
 * Implements Create-Read-Update-Delete operations on reservation
 *
 * @author Oleksii Mohylin
 */
@RestController
public class ReservationCrudController {

    private static final Logger LOG = LoggerFactory.getLogger(ReservationCrudController.class);

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationMapper mapper;

    /**
     * Handling API request to create a new reservation
     *
     * @param dto reservation details
     * @return the same reservation details along with reservation ID
     */
    @Operation(summary = "Create a new reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reservation created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservationDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied or room is already occupied",
                    content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDto createReservation(@RequestBody @Valid NewReservationDto dto) {
        LOG.info("createReservation " + dto);
        Reservation reservation = mapper.dtoToReservation(dto);
        try {
            Reservation reservationWithId = reservationService.create(reservation);
            return mapper.reservationToDto(reservationWithId);
        } catch (ReservationException ex) {
            throw convertToResponseStatusException(ex);
        }
    }

    /**
     * Handling API request to retrieve reservation by an ID
     *
     * @param id ID of reservation in the system
     * @return reservation details DTO
     */
    @Operation(summary = "Get a reservation by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservationDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Reservation not found",
                    content = @Content) })
    @GetMapping("{id}")
    public ReservationDto getReservation(@PathVariable("id") @NotNull Integer id) {
        LOG.info("getReservation " + id);
        try {
            Reservation reservation = reservationService.get(id);
            return mapper.reservationToDto(reservation);
        } catch (ReservationNotFoundException ex) {
            throw convertToResponseStatusException(ex);
        }
    }

    /**
     * Handling API request to update an existing reservation
     *
     * @param id ID of reservation in the system
     * @param dto updated reservation details
     */
    @Operation(summary = "Update reservation with given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input supplied or room is already occupied"),
            @ApiResponse(responseCode = "404", description = "Reservation not found") })
    @PutMapping("{id}")
    public void updateReservation(@PathVariable("id") @NotNull Integer id,
                                  @RequestBody @Valid NewReservationDto dto) {
        LOG.info("updateReservation " + id);
        Reservation reservation = mapper.dtoToReservation(dto);
        reservation.setId(id);
        try {
            reservationService.update(reservation);
        } catch (ReservationException ex) {
            throw convertToResponseStatusException(ex);
        }
    }

    /**
     * Handling API request to delete reservation with a specific ID
     *
     * @param id ID of reservation in the system
     */
    @Operation(summary = "Delete reservation by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
            @ApiResponse(responseCode = "404", description = "Reservation not found") })
    @DeleteMapping("{id}")
    public void deleteReservation(@PathVariable("id") @NotNull Integer id) {
        LOG.info("deleteReservation " + id);
        try {
            reservationService.delete(id);
        } catch (ReservationException ex) {
            throw convertToResponseStatusException(ex);
        }
    }

    public ResponseStatusException convertToResponseStatusException(ReservationException ex) {
        if (ex instanceof ReservationNotFoundException) {
            return new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
    }

    void setReservationService(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    void setMapper(ReservationMapper mapper) {
        this.mapper = mapper;
    }
}
