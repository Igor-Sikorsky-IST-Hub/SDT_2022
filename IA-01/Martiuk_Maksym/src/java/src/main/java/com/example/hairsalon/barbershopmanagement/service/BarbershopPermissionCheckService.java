package com.example.hairsalon.barbershopmanagement.service;

public interface BarbershopPermissionCheckService {

    void checkForPlatformAdminRoleOrBarbershopAdmin(Integer barbershopId);

}
