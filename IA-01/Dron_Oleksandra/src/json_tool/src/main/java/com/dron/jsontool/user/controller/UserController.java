package com.dron.jsontool.user.controller;


import com.dron.jsontool.user.controller.dto.UserDto;
import com.dron.jsontool.user.controller.facade.UserFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Tag(name = "users")
@Validated
@RestController
@RequestMapping("/api/v1/public/users")
@FieldDefaults(makeFinal = true, level = PRIVATE)
@RequiredArgsConstructor
public class UserController {

	UserFacade userFacade;

	@GetMapping("/{id}")
	public ResponseEntity<UserDto> findById(@PathVariable UUID id) {
		UserDto userDto = userFacade.findById(id);
		return ResponseEntity.ok(userDto);
	}

	@GetMapping("/emails/{email}")
	public ResponseEntity<UserDto> findByEmail(@PathVariable String email) {
		UserDto userDto = userFacade.findByEmail(email);
		return ResponseEntity.ok(userDto);
	}

	@PostMapping
	public ResponseEntity<UserDto> save(@RequestBody @Valid UserDto userDto) {
		UserDto response = userFacade.save(userDto);
		return ResponseEntity.ok(response);
	}

}
