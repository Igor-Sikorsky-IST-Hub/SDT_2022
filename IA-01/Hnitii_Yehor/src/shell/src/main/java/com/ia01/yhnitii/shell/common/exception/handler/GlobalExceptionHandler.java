package com.ia01.yhnitii.shell.common.exception.handler;


import com.ia01.yhnitii.shell.common.exception.BusinessException;
import com.ia01.yhnitii.shell.common.exception.dto.Error;
import com.ia01.yhnitii.shell.common.exception.dto.ErrorResponse;
import com.ia01.yhnitii.shell.common.exception.dto.FormValidationError;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@ControllerAdvice
@FieldDefaults(level = PRIVATE)
public class GlobalExceptionHandler {

	@ExceptionHandler(BindException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handle(BindException exception) {
		return new ErrorResponse(
				System.currentTimeMillis(),
				HttpStatus.BAD_REQUEST.value(),
				exception.getBindingResult().getFieldErrors()
						.stream()
						.map(x -> FormValidationError.builder()
								.field(x.getField())
								.message(x.getDefaultMessage())
								.build()).collect(Collectors.toList())
		);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handle(MethodArgumentNotValidException exception) {
		return new ErrorResponse(
				System.currentTimeMillis(),
				HttpStatus.BAD_REQUEST.value(),
				exception.getBindingResult().getFieldErrors()
						.stream()
						.map(x -> FormValidationError.builder()
								.field(x.getField())
								.message(x.getDefaultMessage())
								.build()).collect(Collectors.toList())
		);
	}


	@ResponseBody
	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleBusinessException(BusinessException ex) {
		return new ErrorResponse(
				System.currentTimeMillis(),
				HttpStatus.BAD_REQUEST.value(),
				List.of(Error.builder().message(ex.getMessage()).build())
		);
	}

}
