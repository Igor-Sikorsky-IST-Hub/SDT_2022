package com.dron.jsontool.user.controller;


import com.dron.jsontool.user.controller.dto.AuthDto;
import com.dron.jsontool.user.controller.dto.UserDto;
import com.dron.jsontool.user.controller.facade.UserFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static lombok.AccessLevel.PRIVATE;

@Tag(name = "user")
@RestController
@RequestMapping("/api/v1")
@FieldDefaults(makeFinal = true, level = PRIVATE)
@RequiredArgsConstructor
public class UserController {

	UserFacade userFacade;

	@GetMapping("/private/users/me")
	public ResponseEntity<UserDto> findAuthUser() {
		UserDto userDto = userFacade.getAuthUser();
		return ResponseEntity.ok(userDto);
	}

	@PostMapping("/public/auth")
	public ResponseEntity<UserDto> auth(@RequestBody @Valid AuthDto authDto) {
		UserDto userDto = userFacade.auth(authDto);
		return ResponseEntity.ok(userDto);
	}

	@PostMapping("/public/users")
	public ResponseEntity<UserDto> register(@RequestBody @Valid UserDto user) {
		UserDto response = userFacade.save(user);
		return ResponseEntity.ok(response);
	}

}
