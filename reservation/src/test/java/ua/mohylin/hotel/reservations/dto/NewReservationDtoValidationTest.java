package ua.mohylin.hotel.reservations.dto;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.mohylin.hotel.reservations.ValidationTestBase;

public class NewReservationDtoValidationTest extends ValidationTestBase {

    private NewReservationDto reservationDto;

    @BeforeEach
    public void setUp() {
        reservationDto = new NewReservationDto("John", "Smith", 13, LocalDate.now().plusDays(3), LocalDate.now().plusDays(9));
        assertValidationSuccessful(reservationDto);
    }

    @Test
    public void testRoomNumberShouldBeGreaterThanZero() {
        reservationDto.setRoomNumber(0);
        assertViolationFound(reservationDto, "roomNumber", "must be greater than 0");
    }

    @Test
    public void testRoomNumberShouldNotBeNull() {
        reservationDto.setRoomNumber(null);
        assertViolationFound(reservationDto, "roomNumber", "must not be null");
    }

    @Test
    public void testStartDateShouldNotBeNull() {
        reservationDto.setStartDate(null);
        assertViolationFound(reservationDto, "startDate", "must not be null");
    }

    @Test
    public void testEndDateShouldNotBeNull() {
        reservationDto.setEndDate(null);
        assertViolationFound(reservationDto, "endDate", "must not be null");
    }

    @Test
    public void testFirstNameShouldNotBeEmpty() {
        reservationDto.setFirstName(null);
        assertViolationFound(reservationDto, "firstName", "must not be empty");
    }

    @Test
    public void testLastNameShouldNotBeEmpty() {
        reservationDto.setLastName(null);
        assertViolationFound(reservationDto, "lastName", "must not be empty");
    }

    @Test
    public void testVeryShortFirstName() {
        reservationDto.setFirstName("A");
        assertViolationFound(reservationDto, "firstName", "size must be between 2 and 50");
    }

    @Test
    public void testVeryShortLastName() {
        reservationDto.setLastName("A");
        assertViolationFound(reservationDto, "lastName", "size must be between 2 and 50");
    }
}
