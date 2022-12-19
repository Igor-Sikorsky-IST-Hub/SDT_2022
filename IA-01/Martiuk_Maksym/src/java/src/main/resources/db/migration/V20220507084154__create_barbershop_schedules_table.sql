CREATE TABLE barbershop_schedules
(
    id            SERIAL       NOT NULL,
    barbershop_id INTEGER      NOT NULL,
    day_of_week   VARCHAR(255) NOT NULL,
    opening       TIME         NOT NULL,
    closing       TIME         NOT NULL,
    active        BOOLEAN      NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT barbershop_id_barbershop_schedules_fk
        FOREIGN KEY (barbershop_id) REFERENCES barbershops
);

CREATE UNIQUE INDEX unique_active_schedule
    ON barbershop_schedules (barbershop_id, day_of_week, active)
    WHERE (active = true);