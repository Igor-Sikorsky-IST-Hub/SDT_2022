package com.ia01.yhnitii.shell.history.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ia01.yhnitii.shell.history.repository.entity.enumeration.Action;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
@SuperBuilder(toBuilder = true)
public class HistoryDto {

	@JsonProperty(access = READ_ONLY)
	UUID id;

	@JsonProperty(access = READ_ONLY)
	String history;

	@JsonProperty(access = READ_ONLY)
	Action action;

	@JsonProperty(access = READ_ONLY)
	LocalDateTime executed;

}