ALTER SEQUENCE orders_id_seq RESTART WITH 1;

INSERT INTO orders(start, finish, status, client_id, barber_id, barbershop_id)
VALUES (current_date + time '08:00:00', current_date + time '09:00:00', 'AWAITING', 1, 7, 1),
       (current_date + time '09:30:00', current_date + time '10:00:00', 'AWAITING', 2, 7, 1),
       (current_date + time '8:30:00', current_date + time '10:00:00', 'AWAITING', 3, 8, 1),
       (current_date + time '11:00:00', current_date + time '11:20:00', 'AWAITING', 9, 8, 1),
       (current_date + time '16:00:00', current_date + time '16:45:00', 'AWAITING', 13, 8, 1);