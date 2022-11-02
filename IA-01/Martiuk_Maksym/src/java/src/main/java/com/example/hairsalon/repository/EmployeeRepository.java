package com.example.hairsalon.repository;

import com.example.hairsalon.entity.Employee;
import com.example.hairsalon.entity.Role;

import java.util.List;

public interface EmployeeRepository {

    List<Employee> findByBarbershopId(Integer id);

    List<Employee> findByUserId(Integer id);

    List<Employee> findByBarbershopIdAndRole(Integer barbershopId, Role role);

    List<Employee> findAll();

    Employee save(Employee employee);

    void delete(Employee employee);

}
