package com.dron.jsontool.config.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ErrorResponse {

	Long timestamp;

	Integer status;

	List<? extends Error> errors;

}
