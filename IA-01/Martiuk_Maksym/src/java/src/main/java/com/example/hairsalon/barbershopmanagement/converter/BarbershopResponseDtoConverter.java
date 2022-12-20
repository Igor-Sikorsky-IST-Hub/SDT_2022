package com.example.hairsalon.barbershopmanagement.converter;

import com.example.hairsalon.barbershopmanagement.dto.BarbershopResponseDto;
import com.example.hairsalon.entity.Barbershop;

import java.util.List;

public final class BarbershopResponseDtoConverter {

    private BarbershopResponseDtoConverter() {
    }

    public static BarbershopResponseDto fromBarbershop(Barbershop barbershop) {
        BarbershopResponseDto dto = new BarbershopResponseDto();
        dto.setId(barbershop.getId());
        dto.setCity(barbershop.getCity());
        dto.setName(barbershop.getName());
        dto.setAmountOfWorkplaces(barbershop.getAmountOfWorkplaces());

        return dto;
    }

    public static List<BarbershopResponseDto> toDtoList(List<Barbershop> barbershopList) {
        return barbershopList.stream()
                .map(BarbershopResponseDtoConverter::fromBarbershop)
                .toList();
    }

}
