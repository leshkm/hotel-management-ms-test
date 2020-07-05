package ua.mohylin.hotel.reservations.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder=true)
@Entity
public class Reservation {

    @Id
    @GeneratedValue
    private Integer id;

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
