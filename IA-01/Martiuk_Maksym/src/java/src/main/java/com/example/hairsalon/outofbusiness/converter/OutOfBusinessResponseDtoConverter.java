package com.example.hairsalon.outofbusiness.converter;

import com.example.hairsalon.entity.Barbershop;
import com.example.hairsalon.entity.OutOfBusiness;
import com.example.hairsalon.exception.ResourceNotFoundException;
import com.example.hairsalon.outofbusiness.dto.OutOfBusinessResponseDto;

import java.util.List;
import java.util.function.Supplier;

public final class OutOfBusinessResponseDtoConverter {

    private OutOfBusinessResponseDtoConverter() {
    }

    public static OutOfBusinessResponseDto toResponseDto(
            OutOfBusiness outOfBusiness,
            Barbershop barbershop
    ) {
        var responseDto = new OutOfBusinessResponseDto();
        responseDto.setId(outOfBusiness.getId());
        responseDto.setCause(outOfBusiness.getCause());
        responseDto.setStart(outOfBusiness.getStart());
        responseDto.setFinish(outOfBusiness.getFinish());
        responseDto.setBarbershopName(barbershop.getName());

        return responseDto;
    }

    public static List<OutOfBusinessResponseDto> toResponseDtoList(
            List<OutOfBusiness> outOfBusinesses,
            List<Barbershop> barbershops
    ) {
        return outOfBusinesses.stream()
                .map(outOfBusiness -> {
                    var associatedBarbershop =
                            getAssociatedBarbershop(barbershops, outOfBusiness);

                    return toResponseDto(outOfBusiness, associatedBarbershop);
                })
                .toList();
    }

    private static Barbershop getAssociatedBarbershop(
            List<Barbershop> barbershops,
            OutOfBusiness outOfBusiness
    ) {
        return barbershops.stream()
                .filter(barbershop -> barbershop.getId().equals(outOfBusiness.getBarbershopId()))
                .findFirst()
                .orElseThrow(getResourceNotFoundExceptionSupplier(outOfBusiness));
    }

    private static Supplier<ResourceNotFoundException> getResourceNotFoundExceptionSupplier(
            OutOfBusiness outOfBusiness
    ) {
        return () -> new ResourceNotFoundException(
                String.format("Cannot find barbershop with id: %s", outOfBusiness.getBarbershopId())
        );
    }
}
