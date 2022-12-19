package com.dron.jsontool.user.controller.facade;

import com.dron.jsontool.user.controller.dto.AuthDto;
import com.dron.jsontool.user.controller.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserFacade {

	UserDto findById(UUID id);

	UserDto save(UserDto userDto);

	UserDto findByEmail(String email);

    List<UserDto> findAll();

	UserDto getAuthUser();

	UserDto auth(AuthDto authDto);

}
