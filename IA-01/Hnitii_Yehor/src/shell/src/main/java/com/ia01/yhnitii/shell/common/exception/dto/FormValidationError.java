package com.ia01.yhnitii.shell.common.exception.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PRIVATE;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
public class FormValidationError extends Error {

    String field;

}
