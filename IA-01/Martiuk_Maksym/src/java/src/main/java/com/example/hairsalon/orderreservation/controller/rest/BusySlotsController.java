package com.example.hairsalon.orderreservation.controller.rest;

import com.example.hairsalon.entity.Order;
import com.example.hairsalon.orderreservation.converter.BusySlotDtoConverter;
import com.example.hairsalon.orderreservation.dto.BusySlotResponseDto;
import com.example.hairsalon.orderreservation.service.BusySlotsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/busy-slots")
@RequiredArgsConstructor
public class BusySlotsController {

    private final BusySlotsService busySlotsService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<BusySlotResponseDto> getBusySlots(
            @RequestParam("barbershopName") String barbershopName,
            @RequestParam("quantityOfAcceptedDays") int quantityOfAcceptedDays,
            @RequestParam(value = "barberIds", required = false) List<Integer> barberProfileIdList
    ) {
        List<Order> busySlots = busySlotsService.busySlots(barbershopName, quantityOfAcceptedDays, barberProfileIdList);

        return BusySlotDtoConverter.fromOrdersToResponseDtoList(busySlots);
    }

}
