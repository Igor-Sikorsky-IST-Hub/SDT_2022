package com.example.hairsalon.outofbusiness.controller.rest;

import com.example.hairsalon.barbershopmanagement.service.BarbershopShowService;
import com.example.hairsalon.outofbusiness.converter.OutOfBusinessResponseDtoConverter;
import com.example.hairsalon.outofbusiness.dto.OutOfBusinessResponseDto;
import com.example.hairsalon.outofbusiness.service.OutOfBusinessCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/out-of-business/check")
@RequiredArgsConstructor
public class OutOfBusinessCheckController {

    private final OutOfBusinessCheckService outOfBusinessCheckService;
    private final BarbershopShowService barbershopShowService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<OutOfBusinessResponseDto> getAll() {
        var outOfBusinesses = outOfBusinessCheckService.getAllFromOutOfBusinessCategory();
        var barbershops = barbershopShowService.showAllBarbershopsByOutOfBusinessList(outOfBusinesses);

        return OutOfBusinessResponseDtoConverter.toResponseDtoList(outOfBusinesses, barbershops);
    }

    @GetMapping("/{barbershopName}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public OutOfBusinessResponseDto getOutOfBusinessInfoAboutBarbershop(
            @PathVariable("barbershopName") String barbershopName
    ) {
        var outOfBusiness = outOfBusinessCheckService.getOutOfBusinessInfoIfInThisCategory(barbershopName);
        var barbershop = barbershopShowService.showAllBarbershopsByOutOfBusinessList(List.of(outOfBusiness)).get(0);

        return OutOfBusinessResponseDtoConverter.toResponseDto(outOfBusiness, barbershop);
    }

}
