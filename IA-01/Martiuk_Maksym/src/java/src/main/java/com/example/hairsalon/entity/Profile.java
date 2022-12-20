package com.example.hairsalon.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile {

    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String avatar;
    private Integer userId;

}
