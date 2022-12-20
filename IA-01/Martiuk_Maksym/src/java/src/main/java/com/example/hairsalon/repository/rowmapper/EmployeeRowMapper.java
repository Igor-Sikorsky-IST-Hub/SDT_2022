package com.example.hairsalon.repository.rowmapper;

import com.example.hairsalon.entity.Employee;
import com.example.hairsalon.entity.Role;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EmployeeRowMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer barbershopId = rs.getObject("barbershop_id", Integer.class);
        Integer employeeId = rs.getObject("employee_id", Integer.class);
        Role role = Role.valueOf(rs.getString("role"));

        return Employee.builder()
                .barbershopId(barbershopId)
                .employeeUserId(employeeId)
                .position(role)
                .build();
    }

}
