package com.dron.jsontool.user.controller;


import com.dron.jsontool.common.security.service.SecurityService;
import com.dron.jsontool.user.controller.dto.UserDto;
import com.dron.jsontool.user.controller.facade.UserFacade;
import com.dron.jsontool.user.repository.entity.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Tag(name = "user")
@Validated
@Controller
@RequestMapping("/api/v1")
@FieldDefaults(makeFinal = true, level = PRIVATE)
@RequiredArgsConstructor
public class UserController {

	UserFacade userFacade;
	SecurityService securityService;

	@GetMapping("/private/users/me")
	public ResponseEntity<UserDto> findAuthUser() {
		UserDto userDto = userFacade.getAuthUser();
		return ResponseEntity.ok(userDto);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/private/users/emails/{email}")
	public ResponseEntity<UserDto> findByEmail(@PathVariable String email) {
		User authUser = securityService.getAuthUser();
		UserDto userDto = userFacade.findByEmail(email);
		return ResponseEntity.ok(userDto);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/private/users")
	public ResponseEntity<List<UserDto>> findAll() {
		List<UserDto> userDtos = userFacade.findAll();

		return ResponseEntity.ok(userDtos);
	}

	@PostMapping("/public/users")
	public ResponseEntity<UserDto> register(@Valid UserDto user) {
		UserDto response = userFacade.save(user);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/public/pages/registration")
	public String registrationPage(Model model) {
		User authUser = securityService.getAuthUser();
		model.addAttribute("user", new UserDto());
		return "registration";
	}

}
