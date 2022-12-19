package com.example.hairsalon.orderreservation.service;

import com.example.hairsalon.repository.BarbershopRepository;
import com.example.hairsalon.repository.OrderRepository;
import com.example.hairsalon.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

public class SomeTest {

    private OrderCorrectnessService orderCorrectnessService;

    @MockBean
    private ScheduleRepository scheduleRepository;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private BarbershopRepository barbershopRepository;

    @BeforeEach
    void initService() {

    }

    @Test
    void testQuantityOfBusyPlaces() {

    }

}
