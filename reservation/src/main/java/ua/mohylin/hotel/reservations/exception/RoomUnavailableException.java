package ua.mohylin.hotel.reservations.exception;

/**
 * Exception is thrown upon attempt to make a reservation of a room which is already booked
 *
 * @author Oleksii Mohylin
 */
public class RoomUnavailableException extends ReservationException {

    public RoomUnavailableException(int roomNumber) {
        super(String.format("Room #%d is not available in the selected days", roomNumber));
    }

}
