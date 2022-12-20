package com.example.hairsalon.orderreservation.service;

import com.example.hairsalon.entity.Order;
import com.example.hairsalon.entity.Role;
import com.example.hairsalon.entity.User;
import com.example.hairsalon.exception.PermissionDeniedException;
import com.example.hairsalon.exception.ResourceNotFoundException;
import com.example.hairsalon.repository.EmployeeRepository;
import com.example.hairsalon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class ChangeOrderPermissionServiceImpl implements ChangeOrderPermissionService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public void checkAuthenticationPermission(Order order) {
        var principalUsername = getPrincipalUsername();
        var principal = getUser(principalUsername);

        if (!isPermitted(principal, order)) {
            throw new PermissionDeniedException("Sorry, You don't have access to this operation");
        }
    }

    private boolean isPermitted(User principal, Order order) {
        var principalRoles = principal.getRoles();
        Map<Role, Boolean> checkPermissionByRoleList = new LinkedHashMap<>();

        for (Role role : principalRoles) {
            switch (role) {
                case BARBER -> checkPermissionByRoleList.put(
                        Role.BARBER, order.getBarberProfileId().equals(principal.getProfileId())
                );
                case CLIENT -> checkPermissionByRoleList.put(
                        Role.CLIENT, order.getClientProfileId().equals(principal.getProfileId())
                );
                case BARBERSHOP_ADMIN -> addTrueToMapIfIsAdminOfGivenBarbershop(principal, order, checkPermissionByRoleList);
                case ADMIN -> {
                    return true;
                }
                default -> {
                    return false;
                }
            }
        }

        return checkPermissionByRoleList.values().stream()
                .anyMatch(Boolean.TRUE::equals);
    }

    private void addTrueToMapIfIsAdminOfGivenBarbershop(User principal, Order order, Map<Role, Boolean> checkPermissionByRoleMap) {
        employeeRepository.findByBarbershopId(order.getBarbershopId()).stream()
                .filter(employee -> employee.getPosition().equals(Role.BARBERSHOP_ADMIN))
                .filter(employee -> employee.getEmployeeUserId().equals(principal.getId()))
                .findFirst()
                .ifPresent(employee -> checkPermissionByRoleMap.put(Role.BARBERSHOP_ADMIN, true));
    }

    private String getPrincipalUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException(String.format(
                        "Cannot find user with username: %s",
                        username
                ))
        );
    }

}
