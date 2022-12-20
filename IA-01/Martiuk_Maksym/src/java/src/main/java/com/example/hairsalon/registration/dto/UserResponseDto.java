package com.example.hairsalon.registration.dto;

import com.example.hairsalon.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {

    private Integer userId;
    private String username;
    private Set<Role> roles;

}
