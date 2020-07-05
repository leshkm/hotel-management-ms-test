package ua.mohylin.hotel.reservations.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import ua.mohylin.hotel.reservations.dto.ReservationDto;
import ua.mohylin.hotel.reservations.model.Reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(ReservationsApplication.class)
@AutoConfigureMockMvc
public class ReservationsSearchControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ReservationSearchController controller;

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
    void getAll_positive() throws Exception {

        LocalDate startDate = LocalDate.now().plusDays(3);
        LocalDate endDate = LocalDate.now().plusDays(9);

        List<Reservation> repositoryResponse = new ArrayList<>();
        List<ReservationDto> serviceResponse = new ArrayList<>();

        Collections.addAll(repositoryResponse,
                new Reservation(1, "John", "Smith", 13, startDate, endDate),
                new Reservation(2, "Abraham", "Goldman", 12, startDate, endDate)
        );
        Collections.addAll(serviceResponse,
                new ReservationDto(1, "John", "Smith", 13, startDate, endDate),
                new ReservationDto(2, "Abraham", "Goldman", 12, startDate, endDate)
        );

        when(reservationServiceMock.getAll()).thenReturn(repositoryResponse);

        MvcResult result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        ReservationDto[] actualResult = MAPPER.readValue(result.getResponse().getContentAsString(), ReservationDto[].class);

        assertEquals(2, actualResult.length);

        verify(reservationServiceMock, times(1)).getAll();

    }

    @Test
    void getAllInRange_invalidQuery() throws Exception {

        mockMvc.perform(get("/").queryParam("startDateFrom", "2020-12-12"))
                .andExpect(status().isBadRequest());

    }



    @Test
    void getAllInRange_positive() throws Exception {

        LocalDate startDate = LocalDate.now().plusDays(3);
        LocalDate endDate = LocalDate.now().plusDays(9);

        List<Reservation> repositoryResponse = new ArrayList<>();
        List<ReservationDto> serviceResponse = new ArrayList<>();

        Collections.addAll(repositoryResponse,
                new Reservation(1, "John", "Smith", 13, startDate, endDate),
                new Reservation(2, "Abraham", "Goldman", 12, startDate, endDate)
        );
        Collections.addAll(serviceResponse,
                new ReservationDto(1, "John", "Smith", 13, startDate, endDate),
                new ReservationDto(2, "Abraham", "Goldman", 12, startDate, endDate)
        );

        when(reservationServiceMock.getAllInRange(any(), any())).thenReturn(repositoryResponse);

        final LocalDate queryDateFrom = LocalDate.now().plusDays(2);
        final LocalDate queryDateTo = LocalDate.now().plusDays(4);

        MvcResult result = mockMvc.perform(
                    get("/")
                            .queryParam("startDateFrom", queryDateFrom.format(DateTimeFormatter.ISO_DATE))
                            .queryParam("startDateTo", queryDateTo.format(DateTimeFormatter.ISO_DATE))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        ReservationDto[] actualResult = MAPPER.readValue(result.getResponse().getContentAsString(), ReservationDto[].class);

        assertEquals(2, actualResult.length);

        verify(reservationServiceMock, times(1)).getAllInRange(eq(queryDateFrom), eq(queryDateTo));

    }
}
