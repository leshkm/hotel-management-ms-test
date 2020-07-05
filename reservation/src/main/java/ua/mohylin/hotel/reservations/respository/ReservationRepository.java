package ua.mohylin.hotel.reservations.respository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ua.mohylin.hotel.reservations.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query("SELECT r FROM Reservation r WHERE r.roomNumber = :roomNumber and " +
            "(r.startDate < :date2 and r.endDate > :date1)")
    List<Reservation> findByRoomNumberInDateRange(
            @Param("roomNumber") Integer roomNumber,
            @Param("date1") LocalDate date1,
            @Param("date2") LocalDate date2);


    @Query("SELECT count(r) = 0 FROM Reservation r WHERE r.roomNumber = :roomNumber and " +
            "(r.startDate < :date2 and r.endDate > :date1)")
    boolean checkRoomIsAvailableNewReservation(
            @Param("roomNumber") Integer roomNumber,
            @Param("date1") LocalDate date1,
            @Param("date2") LocalDate date2);

    @Query("SELECT count(r) = 0 FROM Reservation r WHERE r.roomNumber = :roomNumber" +
            " and (r.startDate < :date2 and r.endDate > :date1)" +
            " and r.id != :reservationId")
    boolean checkRoomIsAvailableUpdatedReservation(
            @Param("roomNumber") Integer roomNumber,
            @Param("date1") LocalDate date1,
            @Param("date2") LocalDate date2,
            @Param("reservationId") Integer reservationId);

}
