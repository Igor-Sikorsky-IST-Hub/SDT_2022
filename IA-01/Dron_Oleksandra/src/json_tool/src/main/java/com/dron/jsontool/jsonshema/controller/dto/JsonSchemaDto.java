package com.dron.jsontool.jsonshema.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Builder
public class JsonSchemaDto {

	@JsonProperty(access = READ_ONLY)
	UUID id;

	@NotBlank
	String json;

	@NotBlank
	String title;

	String description;

	UUID previousId;

	@Builder.Default
	LocalDateTime createdDate = LocalDateTime.now();

}
