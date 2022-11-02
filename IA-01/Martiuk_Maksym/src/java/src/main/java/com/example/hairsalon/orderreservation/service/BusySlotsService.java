package com.example.hairsalon.orderreservation.service;

import com.example.hairsalon.entity.Order;

import java.util.List;

public interface BusySlotsService {

    List<Order> busySlots(String barbershopName, int quantityOfAcceptedDays, List<Integer> barberProfileIdList);

}
