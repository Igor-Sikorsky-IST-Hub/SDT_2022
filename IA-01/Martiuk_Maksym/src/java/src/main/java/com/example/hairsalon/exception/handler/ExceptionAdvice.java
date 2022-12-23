package com.example.hairsalon.exception.handler;

import com.example.hairsalon.exception.MailSenderException;
import com.example.hairsalon.exception.PermissionDeniedException;
import com.example.hairsalon.exception.ResourceNotFoundException;
import com.example.hairsalon.exception.ResourceWriteException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    private final MultipartProperties multipartProperties;

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundHandler(ResourceNotFoundException exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ResourceWriteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String writeOperationHandler(ResourceWriteException exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(MailSenderException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public String sendMailHandler(MailSenderException exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(SizeLimitExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String maxFileSizeHandler() {
        return String.format("Max file size: %d MB", multipartProperties.getMaxFileSize().toMegabytes());
    }

    @ResponseBody
    @ExceptionHandler(PermissionDeniedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String permissionDenied(PermissionDeniedException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public Set<String> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        Set<String> errors = new HashSet<>();

        if (exception.getBindingResult().hasErrors()) {
            exception.getBindingResult().getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        }

        return errors;
    }

}
