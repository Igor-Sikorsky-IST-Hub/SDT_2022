package com.example.hairsalon.email.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OutOfBusinessMailNotificationDto {

    private String cause;
    private LocalDateTime start;
    private LocalDateTime finish;
    private String barbershopName;
    private List<String> emails;

}
