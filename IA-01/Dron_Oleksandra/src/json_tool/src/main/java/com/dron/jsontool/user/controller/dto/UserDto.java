package com.dron.jsontool.user.controller.dto;

import com.dron.jsontool.user.repository.entity.enumeration.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Builder
public class UserDto {

	@JsonProperty(access = READ_ONLY)
	UUID id;

	@Email
	@NotBlank
	String email;

	@Length(min = 8)
	@NotBlank
	String password;

	@NotBlank
	String firstname;

	@NotBlank
	String lastname;

	@JsonProperty(access = READ_ONLY)
	Role role;

}
