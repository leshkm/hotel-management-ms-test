package ua.mohylin.hotel.reservations.model;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.mohylin.hotel.reservations.ValidationTestBase;

public class ReservationValidationTest extends ValidationTestBase {

    private Reservation testReservation;

    @BeforeEach
    public void setUp() {
        testReservation = new Reservation(1, "John", "Smith", 13, LocalDate.now().plusDays(3), LocalDate.now().plusDays(9));
        assertValidationSuccessful(testReservation);
    }

    @Test
    public void testRoomNumberShouldBeGreaterThanZero() {
        testReservation.setRoomNumber(0);
        assertViolationFound(testReservation, "roomNumber", "must be greater than 0");
    }

    @Test
    public void testRoomNumberShouldNotBeNull() {
        testReservation.setRoomNumber(null);
        assertViolationFound(testReservation, "roomNumber", "must not be null");
    }

    @Test
    public void testStartDateShouldNotBeNull() {
        testReservation.setStartDate(null);
        assertViolationFound(testReservation, "startDate", "must not be null");
    }

    @Test
    public void testEndDateShouldNotBeNull() {
        testReservation.setEndDate(null);
        assertViolationFound(testReservation, "endDate", "must not be null");
    }

    @Test
    public void testFirstNameShouldNotBeEmpty() {
        testReservation.setFirstName(null);
        assertViolationFound(testReservation, "firstName", "must not be empty");
    }

    @Test
    public void testLastNameShouldNotBeEmpty() {
        testReservation.setLastName(null);
        assertViolationFound(testReservation, "lastName", "must not be empty");
    }

    @Test
    public void testVeryShortFirstName() {
        testReservation.setFirstName("A");
        assertViolationFound(testReservation, "firstName", "size must be between 2 and 50");
    }

    @Test
    public void testVeryShortLastName() {
        testReservation.setLastName("A");
        assertViolationFound(testReservation, "lastName", "size must be between 2 and 50");
    }
}
