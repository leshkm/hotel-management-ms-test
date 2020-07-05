package ua.mohylin.hotel.reservations.service;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.mohylin.hotel.reservations.api.ReservationService;
import ua.mohylin.hotel.reservations.exception.ReservationException;
import ua.mohylin.hotel.reservations.exception.ReservationNotFoundException;
import ua.mohylin.hotel.reservations.exception.RoomUnavailableException;
import ua.mohylin.hotel.reservations.model.Reservation;
import ua.mohylin.hotel.reservations.respository.ReservationRepository;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository repository;


    @Override
    public Reservation create(@Valid @NotNull Reservation reservation) throws ReservationException {
        Objects.requireNonNull(reservation);

        validateRoomIsAvailable(reservation, this::checkRoomAvalabilityNewReservation);

        return repository.save(reservation);
    }

    @Override
    public Reservation get(int id) throws ReservationNotFoundException {
        Optional<Reservation> reservation = repository.findById(id);
        if (!reservation.isPresent())
            throw new ReservationNotFoundException(id);
        return reservation.get();
    }

    @Override
    public void update(@Valid @NotNull Reservation reservation) throws ReservationException {
        Objects.requireNonNull(reservation);

        Integer reservationId = reservation.getId();

        validateReservationExists(reservationId);
        validateRoomIsAvailable(reservation, this::checkRoomAvalabilityUpdatedReservation);

        repository.save(reservation);
    }

    @Override
    public void delete(int id) throws ReservationNotFoundException {
        validateReservationExists(id);
        repository.deleteById(id);
    }

    private void validateReservationExists(int reservationId) throws ReservationNotFoundException {
        if (!repository.existsById(reservationId)) {
            throw new ReservationNotFoundException(reservationId);
        }
    }

    private void validateRoomIsAvailable(Reservation reservation, Function<Reservation, Boolean> availabilityChecker)
            throws ReservationException {

        boolean roomIsAvailable = availabilityChecker.apply(reservation);

        if (!roomIsAvailable) {
            throw new RoomUnavailableException(reservation.getRoomNumber());
        }
    }

    private boolean checkRoomAvalabilityUpdatedReservation(Reservation reservation) {
        return repository.checkRoomIsAvailableUpdatedReservation(
                reservation.getRoomNumber(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getId());
    }

    private boolean checkRoomAvalabilityNewReservation(Reservation reservation) {
        return repository.checkRoomIsAvailableNewReservation(
                reservation.getRoomNumber(),
                reservation.getStartDate(),
                reservation.getEndDate());
    }

    void setRepository(ReservationRepository repository) {
        this.repository = repository;
    }
}
