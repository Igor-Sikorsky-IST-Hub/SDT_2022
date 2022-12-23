package com.example.hairsalon.outofbusiness.controller.rest;

import com.example.hairsalon.barbershopmanagement.service.BarbershopShowService;
import com.example.hairsalon.outofbusiness.converter.OutOfBusinessResponseDtoConverter;
import com.example.hairsalon.outofbusiness.dto.OutOfBusinessRequestDto;
import com.example.hairsalon.outofbusiness.dto.OutOfBusinessResponseDto;
import com.example.hairsalon.outofbusiness.service.OutOfBusinessSavingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/out-of-business")
@RequiredArgsConstructor
public class OutOfBusinessSavingController {

    private final OutOfBusinessSavingService outOfBusinessSavingService;
    private final BarbershopShowService barbershopShowService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public OutOfBusinessResponseDto saveToOutOfBusiness(@Valid @RequestBody OutOfBusinessRequestDto dto) {
        var outOfBusiness = outOfBusinessSavingService.saveToOutOfBusinessCategory(dto);
        var barbershop = barbershopShowService.showAllBarbershopsByOutOfBusinessList(List.of(outOfBusiness)).get(0);

        return OutOfBusinessResponseDtoConverter.toResponseDto(outOfBusiness, barbershop);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public OutOfBusinessResponseDto updateOutOfBusiness(@Valid @RequestBody OutOfBusinessRequestDto dto) {
        var outOfBusiness = outOfBusinessSavingService.updateOutOfBusinessCategory(dto);
        var barbershop = barbershopShowService.showAllBarbershopsByOutOfBusinessList(List.of(outOfBusiness)).get(0);

        return OutOfBusinessResponseDtoConverter.toResponseDto(outOfBusiness, barbershop);
    }

}
