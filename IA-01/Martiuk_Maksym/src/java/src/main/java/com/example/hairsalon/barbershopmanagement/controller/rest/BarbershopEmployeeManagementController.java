package com.example.hairsalon.barbershopmanagement.controller.rest;

import com.example.hairsalon.barbershopmanagement.converter.EmployeeDtoConverter;
import com.example.hairsalon.barbershopmanagement.dto.BarbershopEmployeeDto;
import com.example.hairsalon.barbershopmanagement.dto.SetupBarbershopEmployeeDto;
import com.example.hairsalon.barbershopmanagement.service.BarbershopEmployeeManagementService;
import com.example.hairsalon.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/employee-management")
@RequiredArgsConstructor
public class BarbershopEmployeeManagementController {

    private final BarbershopEmployeeManagementService employeeManagementService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'BARBERSHOP_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public BarbershopEmployeeDto setPositionForUser(@Valid @RequestBody SetupBarbershopEmployeeDto dto) {
        Employee employee = employeeManagementService.setBarbershopEmployee(dto);

        return EmployeeDtoConverter.toEmployeeResponseDto(employee);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'BARBERSHOP_ADMIN')")
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void removeUserFromPosition(@Valid @RequestBody SetupBarbershopEmployeeDto dto) {
        employeeManagementService.removeEmployeeFromPosition(dto);
    }

}
