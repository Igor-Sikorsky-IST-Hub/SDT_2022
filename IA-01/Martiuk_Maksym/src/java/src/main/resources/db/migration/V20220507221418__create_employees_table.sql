CREATE TABLE employees
(
    barbershop_id INTEGER      NOT NULL,
    employee_id   INTEGER      NOT NULL,
    role          VARCHAR(255) NOT NULL,

    CONSTRAINT barbershop_id_employees_fk
        FOREIGN KEY (barbershop_id) REFERENCES barbershops,

    CONSTRAINT employee_id_employees_fk
        FOREIGN KEY (employee_id) REFERENCES users,

    CONSTRAINT unique_record_employees
        UNIQUE (barbershop_id, employee_id, role)
);
