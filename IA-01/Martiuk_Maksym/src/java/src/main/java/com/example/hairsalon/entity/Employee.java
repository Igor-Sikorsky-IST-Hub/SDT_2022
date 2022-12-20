package com.example.hairsalon.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    private Integer barbershopId;
    private Integer employeeUserId;
    private Role position;

}
