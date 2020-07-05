package ua.mohylin.hotel.reservations.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import lombok.*;


@Data
@Builder(toBuilder=true)
@AllArgsConstructor
@NoArgsConstructor
public class NewReservationDto {

    @NotEmpty
    @Size(min=2, max=50)
    private String firstName;

    @NotEmpty
    @Size(min=2, max=50)
    private String lastName;

    @NotNull
    @Range(min = 1, message = "must be greater than 0")
    private Integer roomNumber;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

}
