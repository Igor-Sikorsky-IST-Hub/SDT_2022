package com.example.hairsalon.orderreservation.converter;

import com.example.hairsalon.entity.Order;
import com.example.hairsalon.orderreservation.dto.OrderReservationDto;

public final class OrderReservationDtoConverter {

    private OrderReservationDtoConverter() {
    }

    public static Order fromDto(
            OrderReservationDto dto,
            Integer barberId,
            Integer clientId,
            Integer barbershopId
    ) {
        var order = new Order();
        order.setId(dto.getOrderId());
        order.setBarberProfileId(barberId);
        order.setClientProfileId(clientId);
        order.setBarbershopId(barbershopId);
        order.setStart(dto.getStart());
        order.setFinish(dto.getFinish());

        return order;
    }

}
