package com.example.hairsalon.profilemanagement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileResponseDto {

    private Integer profileId;
    private String name;
    private String surname;
    private String email;
    private String avatar;

}
