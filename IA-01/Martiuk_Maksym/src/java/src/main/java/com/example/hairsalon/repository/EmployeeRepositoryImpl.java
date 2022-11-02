package com.example.hairsalon.repository;

import com.example.hairsalon.entity.Employee;
import com.example.hairsalon.entity.Role;
import com.example.hairsalon.repository.rowmapper.EmployeeRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.sql.Types.INTEGER;
import static java.sql.Types.VARCHAR;

@Repository
@RequiredArgsConstructor
@Slf4j
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final EmployeeRowMapper employeeRowMapper;

    @Override
    public List<Employee> findByBarbershopId(Integer id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("barbershop_id", id, INTEGER);

        String query = "SELECT e.barbershop_id AS barbershop_id, e.employee_id AS employee_id, "
                + "e.role AS role "
                + "FROM employees e "
                + "WHERE barbershop_id = :barbershop_id";

        return jdbcTemplate.query(query, parameterSource, employeeRowMapper);
    }

    @Override
    public List<Employee> findByUserId(Integer id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("employee_id", id, INTEGER);

        String query = "SELECT e.barbershop_id AS barbershop_id, e.employee_id AS employee_id, "
                + "e.role AS role "
                + "FROM employees e "
                + "WHERE e.employee_id = :employee_id";

        return jdbcTemplate.query(query, parameterSource, employeeRowMapper);
    }

    @Override
    public List<Employee> findByBarbershopIdAndRole(Integer barbershopId, Role role) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("barbershop_id", barbershopId, INTEGER)
                .addValue("role", role.name(), VARCHAR);

        String query = "SELECT e.barbershop_id AS barbershop_id, e.employee_id AS employee_id, "
                + "e.role AS role "
                + "FROM employees e "
                + "WHERE e.barbershop_id = :barbershop_id AND e.role = :role";


        return jdbcTemplate.query(query, parameterSource, employeeRowMapper);
    }

    @Override
    public List<Employee> findAll() {
        String query = "SELECT e.barbershop_id AS barbershop_id, e.employee_id AS employee_id, "
                + "e.role AS role "
                + "FROM employees e";

        return jdbcTemplate.query(query, employeeRowMapper);
    }

    @Override
    public Employee save(Employee employee) {
        MapSqlParameterSource parameterSource = setupParamSourceForInsertEmployee(employee);

        String query = "INSERT INTO employees(barbershop_id, employee_id, role) "
                + "VALUES (:barbershop_id, :employee_id, :role)"
                + "ON CONFLICT DO NOTHING";

        jdbcTemplate.update(query, parameterSource);

        return employee;
    }

    @Override
    public void delete(Employee employee) {
        MapSqlParameterSource parameterSource = setupParamSourceForInsertEmployee(employee);

        String query = "DELETE FROM employees WHERE barbershop_id = :barbershop_id AND "
                + "employee_id = :employee_id AND role = :role";

        jdbcTemplate.update(query, parameterSource);
    }

    private MapSqlParameterSource setupParamSourceForInsertEmployee(Employee employee) {
        return new MapSqlParameterSource()
                .addValue("barbershop_id", employee.getBarbershopId(), INTEGER)
                .addValue("employee_id", employee.getEmployeeUserId(), INTEGER)
                .addValue("role", employee.getPosition().name(), VARCHAR);
    }

}
