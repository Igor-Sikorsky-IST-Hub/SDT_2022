ALTER SEQUENCE barbershops_id_seq RESTART WITH 1;
ALTER SEQUENCE barbershop_schedules_id_seq RESTART WITH 1;

INSERT INTO barbershops (name, city, amount_of_workplaces)
VALUES ('first', 'first_city', 4),
       ('second', 'second_city', 5),
       ('thirst', 'third_city', 7);

INSERT INTO barbershop_schedules(barbershop_id, day_of_week, opening, closing, active)
VALUES (1, 'MONDAY', time '08:00:00', time '17:00:00', true),
       (1, 'TUESDAY', time '08:00:00', time '17:00:00', true),
       (1, 'WEDNESDAY', time '08:00:00', time '17:00:00', true),
       (1, 'THURSDAY', time '08:00:00', time '17:00:00', true),
       (1, 'FRIDAY', time '08:00:00', time '17:00:00', true),
       (1, 'SATURDAY', time '08:00:00', time '17:00:00', true),
       (1, 'SUNDAY', time '08:00:00', time '17:00:00', true),

       (2, 'MONDAY', time '08:00:00', time '17:00:00', true),
       (2, 'TUESDAY', time '08:00:00', time '17:00:00', true),
       (2, 'WEDNESDAY', time '08:00:00', time '17:00:00', true),
       (2, 'THURSDAY', time '08:00:00', time '17:00:00', true),
       (2, 'FRIDAY', time '08:00:00', time '17:00:00', true),
       (2, 'SATURDAY', time '08:00:00', time '17:00:00', true),
       (2, 'SUNDAY', time '08:00:00', time '17:00:00', true),

       (3, 'MONDAY', time '08:00:00', time '17:00:00', true),
       (3, 'TUESDAY', time '08:00:00', time '17:00:00', true),
       (3, 'WEDNESDAY', time '08:00:00', time '17:00:00', true),
       (3, 'THURSDAY', time '08:00:00', time '17:00:00', true),
       (3, 'FRIDAY', time '08:00:00', time '17:00:00', true),
       (3, 'SATURDAY', time '08:00:00', time '17:00:00', true),
       (3, 'SUNDAY', time '08:00:00', time '17:00:00', true);