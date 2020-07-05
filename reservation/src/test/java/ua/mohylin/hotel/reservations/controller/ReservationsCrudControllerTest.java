package ua.mohylin.hotel.reservations.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ua.mohylin.hotel.reservations.ReservationsApplication;
import ua.mohylin.hotel.reservations.api.ReservationService;
import ua.mohylin.hotel.reservations.dto.NewReservationDto;
import ua.mohylin.hotel.reservations.dto.ReservationDto;
import ua.mohylin.hotel.reservations.exception.ReservationNotFoundException;
import ua.mohylin.hotel.reservations.model.Reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(ReservationsApplication.class)
@AutoConfigureMockMvc
public class ReservationsCrudControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ReservationCrudController controller;

    static final ObjectMapper MAPPER = new ObjectMapper();
    static {
        MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        MAPPER.registerModule(new JavaTimeModule());
    }

    private ReservationService reservationServiceMock;

    @BeforeEach
    void setUp() {
        reservationServiceMock = Mockito.mock(ReservationService.class);
        controller.setReservationService(reservationServiceMock);
    }

    @Test
    void get_noReservationReturned() throws Exception {

        when(reservationServiceMock.get(anyInt()))
                .thenThrow(new ReservationNotFoundException(1));

        mockMvc.perform(get("/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    void post_reserationCreated() throws Exception {

        LocalDate startDate = LocalDate.now().plusDays(3);
        LocalDate endDate = LocalDate.now().plusDays(9);

        NewReservationDto createInput =
                new NewReservationDto("John", "Smith", 13, startDate, endDate);
        Reservation testReservationWithId =
                new Reservation(1, "John", "Smith", 13, startDate, endDate);
        ReservationDto expectedResult =
                new ReservationDto(1, "John", "Smith", 13, startDate, endDate);

        when(reservationServiceMock.create(any(Reservation.class)))
                .thenReturn(testReservationWithId);

        MvcResult result = mockMvc.perform(
                    post("/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(MAPPER.writeValueAsString(createInput)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        ReservationDto actualResult = MAPPER.readValue(result.getResponse().getContentAsString(), ReservationDto.class);

        assertEquals(1, expectedResult.getId());

    }

    @Test
    void post_invalidInput() throws Exception {
        // bad request with empty names
        NewReservationDto createInput =
                new NewReservationDto("", "", 13, LocalDate.now(), LocalDate.now());

        mockMvc.perform(
                post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(createInput)))
                .andExpect(status().isBadRequest());

    }
}
