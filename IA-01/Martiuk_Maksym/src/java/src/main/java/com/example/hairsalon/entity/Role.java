package com.example.hairsalon.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

public enum Role implements GrantedAuthority {
    CLIENT, BARBER, BARBERSHOP_ADMIN, ADMIN;

    public static Set<Role> getPositionRoles() {
        return Set.of(BARBER, BARBERSHOP_ADMIN);
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
