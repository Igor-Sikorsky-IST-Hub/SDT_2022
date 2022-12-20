package com.dron.jsontool.user.controller.facade;

import com.dron.jsontool.config.exception.BusinessException;
import com.dron.jsontool.config.exception.NotFoundException;
import com.dron.jsontool.config.security.service.SecurityService;
import com.dron.jsontool.user.controller.dto.AuthDto;
import com.dron.jsontool.user.controller.dto.UserDto;
import com.dron.jsontool.user.controller.mapper.UserMapper;
import com.dron.jsontool.user.repository.entity.User;
import com.dron.jsontool.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static com.dron.jsontool.config.utils.Constants.User.USER_ALREADY_EXISTS;
import static com.dron.jsontool.config.utils.Constants.User.WRONG_PASSWORD;
import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class UserFacadeImpl implements UserFacade {

	PasswordEncoder passwordEncoder;
	SecurityService securityService;
	UserService userService;

	UserMapper userMapper;

	@Override
	public UserDto findById(UUID id) {
		User user = userService.findById(id);
		return userMapper.toDto(user);
	}

	@Override
	public UserDto save(UserDto userDto) {
		try {
			userService.findByEmail(userDto.getEmail());
			throw new BusinessException(USER_ALREADY_EXISTS);
		} catch (NotFoundException ignored) {
		}

		User user = userMapper.toEntity(userDto);

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		User saved = userService.save(user);

		return userMapper.toDto(saved);
	}

	@Override
	public UserDto findByEmail(String email) {
		User user = userService.findByEmail(email);
		return userMapper.toDto(user);
	}

	@Override
	public List<UserDto> findAll() {
		List<User> all = userService.findAll();
		return all.stream().map(userMapper::toDto).toList();
	}

	@Override
	public UserDto getAuthUser() {
		return userMapper.toDto(securityService.getAuthUser());
	}

	@Override
	public UserDto auth(AuthDto authDto) {
		User user = userService.findByEmail(authDto.getEmail());

		boolean isNotPasswordMatches = !passwordEncoder.matches(authDto.getPassword(), user.getPassword());
		if (isNotPasswordMatches) {
			throw new BusinessException(WRONG_PASSWORD);
		}

		return userMapper.toDto(user);
	}

}
