package com.dron.jsontool.user.controller.dto;

import com.dron.jsontool.jsonshema.controller.dto.JsonSchemaDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static lombok.AccessLevel.PRIVATE;

@Getter
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

	List<JsonSchemaDto> jsonSchemas;

}
