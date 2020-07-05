package ua.mohylin.hotel.reservations.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import ua.mohylin.hotel.reservations.api.IsoDate;
import ua.mohylin.hotel.reservations.api.ReservationService;
import ua.mohylin.hotel.reservations.dto.ReservationDto;
import ua.mohylin.hotel.reservations.mapping.ReservationMapper;

import static java.util.Objects.isNull;

/**
 * Implements handling of reservation search requests
 *
 * @author Oleksii Mohylin
 */
@RestController
public class ReservationSearchController {

    private static final Logger LOG = LoggerFactory.getLogger(ReservationSearchController.class);

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationMapper mapper;

    @GetMapping
    public List<ReservationDto> searchReservations(
            @RequestParam(required = false) @IsoDate LocalDate startDateFrom,
            @RequestParam(required = false) @IsoDate LocalDate startDateTo) {

        if (isValidSearch(startDateFrom, startDateTo))
            return getReservationsWithinDateRange(startDateFrom, startDateTo);

        return getAllReservations();
    }

    private boolean isValidSearch(LocalDate startDateFrom, LocalDate startDateTo) {
        //both are either nulls or not
        if (isNull(startDateFrom) != isNull(startDateTo))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "both startDateFrom and startDateTo parameters must be present or skipped");
        return !isNull(startDateFrom);
    }

    private List<ReservationDto> getAllReservations() {
        LOG.info("getAllReservations");
        return reservationService.getAll().stream()
                .map(mapper::reservationToDto)
                .collect(Collectors.toList());
    }

    private List<ReservationDto> getReservationsWithinDateRange(LocalDate startDateFrom, LocalDate startDateTo) {
        LOG.info("searchReservations between {} and {}", startDateFrom, startDateTo);
        return reservationService.getAllInRange(startDateFrom, startDateTo)
                .stream()
                .map(mapper::reservationToDto)
                .collect(Collectors.toList());
    }

    void setReservationService(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    void setMapper(ReservationMapper mapper) {
        this.mapper = mapper;
    }
}
