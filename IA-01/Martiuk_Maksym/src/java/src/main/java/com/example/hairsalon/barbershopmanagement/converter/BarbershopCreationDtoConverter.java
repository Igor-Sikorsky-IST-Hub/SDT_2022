package com.example.hairsalon.barbershopmanagement.converter;

import com.example.hairsalon.barbershopmanagement.dto.BarbershopCreationDto;
import com.example.hairsalon.entity.Barbershop;

public final class BarbershopCreationDtoConverter {

    private BarbershopCreationDtoConverter() {
    }

    public static Barbershop fromDto(BarbershopCreationDto dto) {
        Barbershop barbershop = new Barbershop();
        barbershop.setId(dto.getBarbershopId());
        barbershop.setCity(dto.getCity());
        barbershop.setName(dto.getName());
        barbershop.setAmountOfWorkplaces(dto.getAmountOfWorkplaces());

        return barbershop;
    }

}
