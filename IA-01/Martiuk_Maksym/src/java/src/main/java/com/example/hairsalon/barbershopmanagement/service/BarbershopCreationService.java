package com.example.hairsalon.barbershopmanagement.service;

import com.example.hairsalon.barbershopmanagement.dto.BarbershopCreationDto;
import com.example.hairsalon.entity.Barbershop;

public interface BarbershopCreationService {

    Barbershop createBarbershop(BarbershopCreationDto dto);

    Barbershop updateBarbershop(BarbershopCreationDto dto);

    void deleteBarbershop(Integer barbershopId);

}
