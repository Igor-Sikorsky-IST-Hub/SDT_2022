package com.example.hairsalon.orderreservation.service;

import com.example.hairsalon.entity.Order;
import com.example.hairsalon.orderreservation.dto.OrderReservationDto;

import java.util.List;

public interface OrderReservationService {

    List<Order> makeReservations(List<OrderReservationDto> listOfDto);

    Order updateReservation(OrderReservationDto dto);

    void deleteReservation(Integer orderId);

}
