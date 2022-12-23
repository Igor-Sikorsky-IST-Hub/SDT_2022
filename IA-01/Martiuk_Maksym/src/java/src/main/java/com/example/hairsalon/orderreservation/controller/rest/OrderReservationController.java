package com.example.hairsalon.orderreservation.controller.rest;

import com.example.hairsalon.entity.Order;
import com.example.hairsalon.orderreservation.converter.BusySlotDtoConverter;
import com.example.hairsalon.orderreservation.dto.BusySlotResponseDto;
import com.example.hairsalon.orderreservation.dto.OrderReservationDto;
import com.example.hairsalon.orderreservation.service.OrderReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/order-reservation")
@RequiredArgsConstructor
public class OrderReservationController {

    private final OrderReservationService reservationService;

    @PreAuthorize("hasAuthority('CLIENT')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public List<BusySlotResponseDto> makeReservations(
            @Valid @RequestBody List<OrderReservationDto> reservationDtoList
    ) {
        List<Order> reservations = reservationService.makeReservations(reservationDtoList);

        return BusySlotDtoConverter.fromOrdersToResponseDtoList(reservations);
    }

    @PreAuthorize("hasAnyAuthority('BARBERSHOP_ADMIN', 'ADMIN', 'CLIENT', 'BARBER')")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BusySlotResponseDto updateReservation(@Valid @RequestBody OrderReservationDto dto) {
        Order order = reservationService.updateReservation(dto);

        return BusySlotDtoConverter.fromOrderToResponseDto(order);
    }

    @PreAuthorize("hasAnyAuthority('BARBERSHOP_ADMIN', 'ADMIN', 'CLIENT', 'BARBER')")
    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteReservation(@PathVariable("orderId") Integer orderId) {
        reservationService.deleteReservation(orderId);
    }

}
