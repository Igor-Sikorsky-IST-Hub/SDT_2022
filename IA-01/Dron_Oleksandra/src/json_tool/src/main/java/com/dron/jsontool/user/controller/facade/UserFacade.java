package com.dron.jsontool.user.controller.facade;

import com.dron.jsontool.user.controller.dto.UserDto;

import java.util.UUID;

public interface UserFacade {

	UserDto findById(UUID id);

	UserDto save(UserDto userDto);

	UserDto findByEmail(String email);

}
