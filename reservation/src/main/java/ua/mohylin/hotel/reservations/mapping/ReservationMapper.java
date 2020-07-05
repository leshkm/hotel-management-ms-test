package ua.mohylin.hotel.reservations.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ua.mohylin.hotel.reservations.dto.NewReservationDto;
import ua.mohylin.hotel.reservations.dto.ReservationDto;
import ua.mohylin.hotel.reservations.model.Reservation;

/**
 * Conversions between DTO and model objects using MapStruct
 *
 * @author Oleksii Mohylin
 */
@Mapper(componentModel = "spring")
public interface ReservationMapper {

    /**
     * Converts reservation details (new or updated) to model
     *
     * @param dto reservation details
     * @return model object
     */
    @Mapping(target = "id", ignore = true)
    Reservation dtoToReservation(NewReservationDto dto);

    /**
     * Converts model object to a DTO representation
     *
     * @param reservation Reservation object
     * @return DTO representation
     */
    ReservationDto reservationToDto(Reservation reservation);

}
