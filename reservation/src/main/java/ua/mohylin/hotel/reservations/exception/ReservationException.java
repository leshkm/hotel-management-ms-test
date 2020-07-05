package ua.mohylin.hotel.reservations.exception;

/**
 * Base exception class for api exceptions
 *
 * @author Oleksii Mohylin
 */
public abstract class ReservationException extends Exception {

    protected ReservationException(String message) {
        super(message);
    }

}
