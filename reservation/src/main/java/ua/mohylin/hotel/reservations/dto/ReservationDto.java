package ua.mohylin.hotel.reservations.dto;

import java.time.LocalDate;

import lombok.*;

/**
 *
 *
 *
 * @author Oleksii Mohylin
 */
@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder=true)
@Data
@NoArgsConstructor
public class ReservationDto {

    private Integer id;

    private String firstName;

    private String lastName;

    private Integer roomNumber;

    private LocalDate startDate;

    private LocalDate endDate;

}
