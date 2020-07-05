package ua.mohylin.hotel.reservations.service;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.mohylin.hotel.reservations.exception.ReservationException;
import ua.mohylin.hotel.reservations.exception.ReservationNotFoundException;
import ua.mohylin.hotel.reservations.model.Reservation;
import ua.mohylin.hotel.reservations.respository.ReservationRepository;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ReservationServiceImplTest {

    ReservationServiceImpl testedService = new ReservationServiceImpl();

    ReservationRepository repositoryMock = mock(ReservationRepository.class);


    LocalDate startDate = LocalDate.now().plusDays(3);
    LocalDate endDate = LocalDate.now().plusDays(9);

    Reservation testReservationNoId =
            new Reservation(null, "John", "Smith", 13, startDate, endDate);

    Reservation testReservationWithId =
            new Reservation(1, "John", "Smith", 13, startDate, endDate);

    @BeforeEach
    public void setUp() {
        testedService.setRepository(repositoryMock);
    }

    @Test
    public void testCreatePositive() throws ReservationException {
        when(
                repositoryMock.checkRoomIsAvailableNewReservation(anyInt(), any(LocalDate.class), any(LocalDate.class))
        ).thenReturn(true);

        when(repositoryMock.save(any(Reservation.class))).thenReturn(testReservationWithId);

        Reservation createdReservation = testedService.create(testReservationNoId);

        verify(repositoryMock, times(1))
                .checkRoomIsAvailableNewReservation(eq(13), any(LocalDate.class), any(LocalDate.class));
        verify(repositoryMock, times(1)).save(eq(testReservationNoId));
        assertSame(createdReservation, testReservationWithId);
    }

    @Test
    public void testGetPositive() throws ReservationException {

        when(repositoryMock.findById(anyInt())).thenReturn(Optional.of(testReservationWithId));

        Reservation retrievedReservation = testedService.get(1);

        verify(repositoryMock, times(1)).findById(eq(1));
        assertSame(retrievedReservation, testReservationWithId);
    }

    @Test()
    public void testGetNotFound() {

        when(repositoryMock.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(ReservationNotFoundException.class,
                () -> testedService.get(1));

    }

    @Test
    public void testUpdatePositive() throws ReservationException {

        when(repositoryMock.existsById(anyInt())).thenReturn(true);
        when(
                repositoryMock.checkRoomIsAvailableUpdatedReservation(anyInt(), any(LocalDate.class), any(LocalDate.class), anyInt())
        ).thenReturn(true);
        when(repositoryMock.save(any(Reservation.class))).thenReturn(testReservationWithId);

        testedService.update(testReservationWithId);

        verify(repositoryMock, times(1)).existsById(eq(1));
        verify(repositoryMock, times(1))
                .checkRoomIsAvailableUpdatedReservation(eq(13), any(LocalDate.class), any(LocalDate.class), eq(1));
        verify(repositoryMock, times(1)).save(eq(testReservationWithId));
    }

    @Test()
    public void testUpdateNotFound() {

        when(repositoryMock.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(ReservationNotFoundException.class,
                () -> testedService.update(testReservationWithId));

    }

    @Test
    public void testDeletePositive() throws ReservationException {

        when(repositoryMock.existsById(anyInt())).thenReturn(true);

        testedService.delete(1);

        verify(repositoryMock, times(1)).existsById(eq(1));
        verify(repositoryMock, times(1)).deleteById(eq(1));

    }

    @Test()
    public void testDeleteNotFound() {

        when(repositoryMock.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(ReservationNotFoundException.class,
                () -> testedService.delete(1));

    }

}
