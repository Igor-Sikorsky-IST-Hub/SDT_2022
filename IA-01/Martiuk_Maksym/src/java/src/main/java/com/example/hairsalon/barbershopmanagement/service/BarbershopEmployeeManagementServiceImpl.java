package com.example.hairsalon.barbershopmanagement.service;

import com.example.hairsalon.barbershopmanagement.dto.SetupBarbershopEmployeeDto;
import com.example.hairsalon.entity.Employee;
import com.example.hairsalon.entity.User;
import com.example.hairsalon.exception.ResourceNotFoundException;
import com.example.hairsalon.repository.BarbershopRepository;
import com.example.hairsalon.repository.EmployeeRepository;
import com.example.hairsalon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BarbershopEmployeeManagementServiceImpl implements BarbershopEmployeeManagementService {

    private final EmployeeRepository employeeRepository;
    private final BarbershopRepository barbershopRepository;
    private final UserRepository userRepository;
    private final BarbershopPermissionCheckService permissionCheckService;

    @Override
    public Employee setBarbershopEmployee(SetupBarbershopEmployeeDto dto) {
        var barbershopId = getBarbershopId(dto);
        var user = getUser(dto.getUsername());

        permissionCheckService.checkForPlatformAdminRoleOrBarbershopAdmin(barbershopId);

        var employee = Employee.builder()
                .barbershopId(barbershopId)
                .employeeUserId(user.getId())
                .position(dto.getPosition())
                .build();

        setUserPositionRole(dto, user);
        userRepository.save(user);

        return employeeRepository.save(employee);
    }

    @Override
    public void removeEmployeeFromPosition(SetupBarbershopEmployeeDto dto) {
        var barbershopId = getBarbershopId(dto);
        var user = getUser(dto.getUsername());

        permissionCheckService.checkForPlatformAdminRoleOrBarbershopAdmin(barbershopId);

        var employee = Employee.builder()
                .barbershopId(barbershopId)
                .employeeUserId(user.getId())
                .position(dto.getPosition())
                .build();

        employeeRepository.delete(employee);
    }

    private User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException(String.format(
                        "Cannot find user with username: %s",
                        username
                ))
        );
    }

    private Integer getBarbershopId(SetupBarbershopEmployeeDto dto) {
        return barbershopRepository.findByName(dto.getBarbershopName()).orElseThrow(
                () -> new ResourceNotFoundException(String.format(
                        "Cannot find barbershop with name: %s",
                        dto.getBarbershopName())
                )
        ).getId();
    }

    private void setUserPositionRole(SetupBarbershopEmployeeDto dto, User user) {
        user.getRoles().add(dto.getPosition());
    }

}
