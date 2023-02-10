package com.ia01.yhnitii.shell.common.exception.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PROTECTED;

@Data
@SuperBuilder(toBuilder = true)
@RequiredArgsConstructor
@FieldDefaults(level = PROTECTED)
public class Error {

    String message;

}
