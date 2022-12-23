package com.example.hairsalon.registration.converter;

import com.example.hairsalon.entity.User;
import com.example.hairsalon.registration.dto.UserResponseDto;

public final class UserResponseDtoConverter {

    private UserResponseDtoConverter() {
    }

    public static UserResponseDto toResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setUserId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRoles(user.getRoles());

        return dto;
    }

}
