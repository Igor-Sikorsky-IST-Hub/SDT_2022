package com.example.hairsalon.registration.controller;

import com.example.hairsalon.registration.converter.UserResponseDtoConverter;
import com.example.hairsalon.registration.dto.UserRegistrationDto;
import com.example.hairsalon.registration.dto.UserResponseDto;
import com.example.hairsalon.registration.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/registration")
@RequiredArgsConstructor
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserResponseDto registerUser(@Valid @RequestBody UserRegistrationDto dto) {
        var user = userRegistrationService.registerUser(dto);

        return UserResponseDtoConverter.toResponseDto(user);
    }

}
