package ua.mohylin.hotel.reservations.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ua.mohylin.hotel.reservations.dto.ReservationDto;
import ua.mohylin.hotel.reservations.mapping.ReservationMapper;
import ua.mohylin.hotel.reservations.respository.ReservationRepository;

/**
 * Implements handling of reservation search requests
 *
 * @author Oleksii Mohylin
 */
@RestController
public class ReservationSearchController {

    private static final Logger LOG = LoggerFactory.getLogger(ReservationSearchController.class);

    @Autowired
    private ReservationRepository repository;

    @Autowired
    private ReservationMapper mapper;

    @GetMapping
    public List<ReservationDto> searchReservations() {
        LOG.info("searchReservations");
        return repository.findAll().stream().map(mapper::reservationToDto).collect(Collectors.toList());
    }

    void setRepository(ReservationRepository repository) {
        this.repository = repository;
    }

    void setMapper(ReservationMapper mapper) {
        this.mapper = mapper;
    }
}
