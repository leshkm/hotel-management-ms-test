package ua.mohylin.hotel.reservations.exception;


/**
 * Designates situation when given reservation ID was not found in the system.
 *
 * @author Oleksii Mohylin
 */
public class ReservationNotFoundException extends ReservationException {

    public ReservationNotFoundException(int reservationId) {
        super(String.format("Reservation not found for id %d", reservationId));
    }

}
