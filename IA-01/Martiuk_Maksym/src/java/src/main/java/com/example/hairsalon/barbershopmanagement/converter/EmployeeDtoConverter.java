package com.example.hairsalon.barbershopmanagement.converter;

import com.example.hairsalon.barbershopmanagement.dto.BarbershopEmployeeDto;
import com.example.hairsalon.entity.Employee;

import java.util.List;

public final class EmployeeDtoConverter {

    private EmployeeDtoConverter() {
    }

    public static BarbershopEmployeeDto toEmployeeResponseDto(Employee employee) {
        BarbershopEmployeeDto dto = new BarbershopEmployeeDto();
        dto.setBarbershopId(employee.getBarbershopId());
        dto.setEstablishedRole(employee.getPosition());
        dto.setUserId(employee.getEmployeeUserId());

        return dto;
    }

    public static List<BarbershopEmployeeDto> toDtoList(List<Employee> employees) {
        return employees.stream()
                .map(EmployeeDtoConverter::toEmployeeResponseDto)
                .toList();
    }

}
