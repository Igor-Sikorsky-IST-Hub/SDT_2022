package com.example.hairsalon.barbershopmanagement.service;

import com.example.hairsalon.barbershopmanagement.dto.SetupBarbershopEmployeeDto;
import com.example.hairsalon.entity.Employee;

public interface BarbershopEmployeeManagementService {

    Employee setBarbershopEmployee(SetupBarbershopEmployeeDto dto);

    void removeEmployeeFromPosition(SetupBarbershopEmployeeDto dto);

}
