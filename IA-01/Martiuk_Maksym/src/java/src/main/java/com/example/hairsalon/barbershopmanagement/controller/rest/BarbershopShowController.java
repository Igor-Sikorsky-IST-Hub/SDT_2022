package com.example.hairsalon.barbershopmanagement.controller.rest;

import com.example.hairsalon.barbershopmanagement.converter.BarbershopResponseDtoConverter;
import com.example.hairsalon.barbershopmanagement.converter.ClientDtoConverter;
import com.example.hairsalon.barbershopmanagement.converter.EmployeeDtoConverter;
import com.example.hairsalon.barbershopmanagement.dto.BarbershopEmployeeDto;
import com.example.hairsalon.barbershopmanagement.dto.BarbershopResponseDto;
import com.example.hairsalon.barbershopmanagement.dto.ClientResponseDto;
import com.example.hairsalon.barbershopmanagement.service.BarbershopShowService;
import com.example.hairsalon.entity.Barbershop;
import com.example.hairsalon.entity.Client;
import com.example.hairsalon.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/barbershops")
@RequiredArgsConstructor
public class BarbershopShowController {

    private final BarbershopShowService barbershopShowService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<BarbershopResponseDto> showAll() {
        List<Barbershop> barbershops = barbershopShowService.showAllBarbershops();

        return BarbershopResponseDtoConverter.toDtoList(barbershops);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'BARBERSHOP_ADMIN')")
    @GetMapping("/{barbershopId}/employees")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<BarbershopEmployeeDto> showBarbershopEmployees(
            @PathVariable("barbershopId") Integer barbershopId
    ) {
        List<Employee> employees = barbershopShowService.showBarbershopEmployees(barbershopId);

        return EmployeeDtoConverter.toDtoList(employees);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'BARBERSHOP_ADMIN')")
    @GetMapping("/{barbershopId}/clients")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ClientResponseDto> showBarbershopClients(
            @PathVariable("barbershopId") Integer barbershopId
    ) {
        List<Client> clients = barbershopShowService.showBarbershopClients(barbershopId);

        return ClientDtoConverter.toDtoList(clients);
    }

}
