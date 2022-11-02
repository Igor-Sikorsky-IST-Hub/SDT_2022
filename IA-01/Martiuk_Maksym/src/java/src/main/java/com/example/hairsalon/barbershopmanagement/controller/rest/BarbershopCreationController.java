package com.example.hairsalon.barbershopmanagement.controller.rest;

import com.example.hairsalon.barbershopmanagement.converter.BarbershopResponseDtoConverter;
import com.example.hairsalon.barbershopmanagement.dto.BarbershopCreationDto;
import com.example.hairsalon.barbershopmanagement.dto.BarbershopResponseDto;
import com.example.hairsalon.barbershopmanagement.service.BarbershopCreationService;
import com.example.hairsalon.entity.Barbershop;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/barbershop-creation")
@RequiredArgsConstructor
public class BarbershopCreationController {

    private final BarbershopCreationService barbershopCreationService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public BarbershopResponseDto createBarbershop(@Valid @RequestBody BarbershopCreationDto dto) {
        Barbershop barbershop = barbershopCreationService.createBarbershop(dto);

        return BarbershopResponseDtoConverter.fromBarbershop(barbershop);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'BARBERSHOP_ADMIN')")
    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BarbershopResponseDto updateBarbershop(@Valid @RequestBody BarbershopCreationDto dto) {
        Barbershop barbershop = barbershopCreationService.updateBarbershop(dto);

        return BarbershopResponseDtoConverter.fromBarbershop(barbershop);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{barbershopId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBarbershop(@PathVariable("barbershopId") Integer barbershopId) {
        barbershopCreationService.deleteBarbershop(barbershopId);
    }

}
