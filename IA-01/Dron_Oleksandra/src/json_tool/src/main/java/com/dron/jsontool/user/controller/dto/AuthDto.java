package com.dron.jsontool.user.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Builder
public class AuthDto {

	@Email
	@NotBlank
	String email;

	@Length(min = 8)
	@NotBlank
	String password;

}
