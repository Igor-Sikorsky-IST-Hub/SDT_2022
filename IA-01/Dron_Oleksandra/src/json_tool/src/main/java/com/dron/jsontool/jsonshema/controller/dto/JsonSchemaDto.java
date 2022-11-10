package com.dron.jsontool.jsonshema.controller.dto;

import com.dron.jsontool.jsonhistory.controller.dto.JsonHistoryDto;
import com.dron.jsontool.user.controller.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Builder
public class JsonSchemaDto {

	@NotBlank
	String json;

	@NotBlank
	String name;

	@NotNull
	UserDto owner;

	JsonHistoryDto previousHistory;

	JsonHistoryDto currentHistory;

}
