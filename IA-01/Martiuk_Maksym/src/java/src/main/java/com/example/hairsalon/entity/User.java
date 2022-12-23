package com.example.hairsalon.entity;

import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class  User {

    private Integer id;
    private Integer profileId;
    private boolean active;
    private String username;
    private String password;
    private Set<Role> roles;

}
