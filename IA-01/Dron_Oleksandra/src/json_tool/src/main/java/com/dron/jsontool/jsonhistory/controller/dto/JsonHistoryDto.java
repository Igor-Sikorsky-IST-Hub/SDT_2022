package com.dron.jsontool.jsonhistory.controller.dto;

import com.dron.jsontool.jsonshema.controller.dto.JsonSchemaDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Builder
public class JsonHistoryDto {

	@NotNull
	JsonSchemaDto previous;

	@NotNull
	JsonSchemaDto current;

}
