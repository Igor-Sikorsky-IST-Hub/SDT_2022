package com.example.hairsalon.registration.service;

import com.example.hairsalon.entity.User;
import com.example.hairsalon.registration.dto.UserRegistrationDto;

public interface UserRegistrationService {

    User registerUser(UserRegistrationDto dto);

}
