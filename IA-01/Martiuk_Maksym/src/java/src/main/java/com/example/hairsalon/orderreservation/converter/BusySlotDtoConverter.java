package com.example.hairsalon.orderreservation.converter;

import com.example.hairsalon.entity.Order;
import com.example.hairsalon.orderreservation.dto.BusySlotResponseDto;

import java.util.List;

public final class BusySlotDtoConverter {

    private BusySlotDtoConverter() {
    }

    public static BusySlotResponseDto fromOrderToResponseDto(Order order) {
        BusySlotResponseDto busySlotResponseDto = new BusySlotResponseDto();
        busySlotResponseDto.setStart(order.getStart().toLocalTime());
        busySlotResponseDto.setFinish(order.getFinish().toLocalTime());
        busySlotResponseDto.setBarberProfileId(order.getBarberProfileId());
        busySlotResponseDto.setLocalDate(order.getStart().toLocalDate());

        return busySlotResponseDto;
    }

    public static List<BusySlotResponseDto> fromOrdersToResponseDtoList(List<Order> orders) {
        return orders.stream()
                .map(BusySlotDtoConverter::fromOrderToResponseDto)
                .toList();
    }

}
