package com.dron.jsontool.user.controller.facade.impl;

import com.dron.jsontool.user.controller.dto.UserDto;
import com.dron.jsontool.user.controller.facade.UserFacade;
import com.dron.jsontool.user.controller.mapper.UserMapper;
import com.dron.jsontool.user.repository.entity.User;
import com.dron.jsontool.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class UserFacadeImpl implements UserFacade {

	UserService userService;

	UserMapper userMapper;

	@Override
	public UserDto findById(UUID id) {
		User user = userService.findById(id);
		return userMapper.toDto(user);
	}

	@Override
	public UserDto save(UserDto userDto) {
		User user = userMapper.toEntity(userDto);
		User saved = userService.save(user);
		return userMapper.toDto(saved);
	}

	@Override
	public UserDto findByEmail(String email) {
		User user = userService.findByEmail(email);
		return userMapper.toDto(user);
	}

}
