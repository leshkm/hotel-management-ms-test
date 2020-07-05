package ua.mohylin.hotel.reservations.mapping;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import ua.mohylin.hotel.reservations.dto.NewReservationDto;
import ua.mohylin.hotel.reservations.dto.ReservationDto;
import ua.mohylin.hotel.reservations.model.Reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationMapperTest {

    static ReservationMapper mapper = new ReservationMapperImpl();

    @Test
    public void testMappingDtoToReservation() {

        NewReservationDto reservationDto =
                new NewReservationDto("John", "Smith", 13,
                        LocalDate.now().plusDays(3),
                        LocalDate.now().plusDays(9));

        Reservation testReservationNoId =
                new Reservation(null, "John", "Smith", 13,
                        LocalDate.now().plusDays(3),
                        LocalDate.now().plusDays(9));

        assertEquals(testReservationNoId, mapper.dtoToReservation(reservationDto));
    }


    @Test
    public void testMappingReservationToDto() {

        Reservation testReservation =
                new Reservation(1, "John", "Smith", 13,
                        LocalDate.now().plusDays(3),
                        LocalDate.now().plusDays(9));

        ReservationDto reservationDtos =
                new ReservationDto(1, "John", "Smith", 13,
                        LocalDate.now().plusDays(3),
                        LocalDate.now().plusDays(9));


        assertEquals(reservationDtos, mapper.reservationToDto(testReservation));
    }

}
