package com.example.hairsalon.orderreservation.service;

import com.example.hairsalon.entity.Order;

public interface ChangeOrderPermissionService {

    void checkAuthenticationPermission(Order order);

}
