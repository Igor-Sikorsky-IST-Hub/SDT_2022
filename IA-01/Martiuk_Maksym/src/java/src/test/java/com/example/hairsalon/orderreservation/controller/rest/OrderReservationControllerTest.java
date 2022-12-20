package com.example.hairsalon.orderreservation.controller.rest;

import com.example.hairsalon.container.IntegrationTestBase;
import com.example.hairsalon.orderreservation.dto.OrderReservationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(
        value = {"/user-profile-test-data.sql",
                "/barbershops-test-data.sql",
                "/orders-test-data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public class OrderReservationControllerTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithUserDetails("vvlad")
    void testMakeReservationSuccessfulWithClientRoleTest() throws Exception {
        var orderReservationDto = getOrderReservationDto();

        var request =
                MockMvcRequestBuilders.post("/api/v1/order-reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(orderReservationDto)));

        this.mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    @WithUserDetails("mmaks")
    void updateOrderSuccessfulWithAdminRoleTest() throws Exception {
        var orderReservationDto = getOrderReservationDto();
        orderReservationDto.setOrderId(1);

        var request =
                MockMvcRequestBuilders.put("/api/v1/order-reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderReservationDto));

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @NotNull
    private OrderReservationDto getOrderReservationDto() {
        var orderReservationDto = new OrderReservationDto();
        orderReservationDto.setBarbershopName("first");
        orderReservationDto.setBarberProfileId(7);
        orderReservationDto.setClientProfileId(1);
        orderReservationDto.setStart(LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 0)));
        orderReservationDto.setFinish(LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 45)));

        return orderReservationDto;
    }

}
