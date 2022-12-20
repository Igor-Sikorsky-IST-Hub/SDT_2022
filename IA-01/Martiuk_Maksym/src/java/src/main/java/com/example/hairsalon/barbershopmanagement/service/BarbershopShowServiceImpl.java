package com.example.hairsalon.barbershopmanagement.service;

import com.example.hairsalon.entity.*;
import com.example.hairsalon.repository.BarbershopRepository;
import com.example.hairsalon.repository.ClientRepository;
import com.example.hairsalon.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BarbershopShowServiceImpl implements BarbershopShowService {

    private final BarbershopRepository barbershopRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<Barbershop> showAllBarbershops() {
        return barbershopRepository.findAll();
    }

    @Override
    public List<Client> showBarbershopClients(Integer barbershopId) {
        return clientRepository.findByBarbershopId(barbershopId);
    }

    @Override
    public List<Employee> showBarbershopEmployees(Integer barbershopId) {
        return employeeRepository.findByBarbershopId(barbershopId);
    }

    @Override
    public List<Integer> getBarbershopAdminIdList(Integer barbershopId) {
        return employeeRepository.findByBarbershopId(barbershopId).stream()
                .filter(employee -> employee.getPosition().equals(Role.BARBERSHOP_ADMIN))
                .map(Employee::getEmployeeUserId)
                .toList();
    }

    @Override
    public List<Barbershop> showAllBarbershopsByOutOfBusinessList(List<OutOfBusiness> outOfBusinesses) {
        var barbershopIdList = outOfBusinesses.stream()
                .map(OutOfBusiness::getBarbershopId)
                .toList();

        if (barbershopIdList.isEmpty()) {
            return Collections.emptyList();
        }

        return barbershopRepository.findAllByIdList(barbershopIdList);
    }

}
