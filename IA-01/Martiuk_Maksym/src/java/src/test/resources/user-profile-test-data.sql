ALTER SEQUENCE profiles_id_seq RESTART WITH 1;
ALTER SEQUENCE users_id_seq RESTART WITH 10;

INSERT INTO profiles(name, surname, email)
VALUES ('maks', 'maksov', 'maks@mail.com'),
       ('vlad', 'vladov', 'vlad@mail.com'),
       ('danil', 'danilov', 'danil@mail.com'),
       ('sasha', 'sashov', 'sasha@mail.com'),
       ('egor', 'egorov', 'egor@mail.com'),
       ('asus', 'asusov', 'asus@mail.com'),
       ('taras', 'tarasov', 'taras@mail.com'),
       ('bob', 'bobov', 'bob@mail.com'),
       ('lewis', 'mills', 'lewis@mail.com'),
       ('reed', 'stark', 'reed@mail.com'),
       ('devon', 'smillie', 'devon@mail.com'),
       ('felix', 'felixov', 'felix@mail.com'),
       ('sean', 'ricany', 'sean@mail.com'),
       ('alex', 'alexov', 'alex@mail.com');

INSERT INTO users(active, username, password)
VALUES (true, 'mmaks', '1'),  -- CLIENT, ADMIN
       (true, 'vvlad', '1'),  -- CLIENT
       (true, 'ddanil', '1'), -- CLIENT
       (true, 'ssasha', '1'), -- CLIENT, BARBERSHOP_ADMIN(barbershop_id = 1)
       (true, 'eegor', '1'),  -- CLIENT, BARBERSHOP_ADMIN(barbershop_id = 2)
       (true, 'aasus', '1'),  -- CLIENT, BARBERSHOP_ADMIN(barbershop_id = 3)
       (true, 'ttaras', '1'), -- CLIENT, BARBER
       (true, 'bbob', '1'),   -- CLIENT, BARBER
       (true, 'llewis', '1'), -- CLIENT, BARBER
       (true, 'rreed', '1'),  -- CLIENT, BARBER
       (true, 'ddevon', '1'); -- CLIENT, BARBER

-- felix, sean, alex have only profiles

INSERT INTO user_roles(user_id, role)
VALUES (10, 'CLIENT'),
       (10, 'ADMIN'),
       (11, 'CLIENT'),
       (12, 'CLIENT'),
       (13, 'CLIENT'),
       (13, 'BARBERSHOP_ADMIN'),
       (14, 'CLIENT'),
       (14, 'BARBERSHOP_ADMIN'),
       (15, 'CLIENT'),
       (15, 'BARBERSHOP_ADMIN'),
       (16, 'CLIENT'),
       (16, 'BARBER'),
       (17, 'CLIENT'),
       (17, 'BARBER'),
       (18, 'CLIENT'),
       (18, 'BARBER'),
       (19, 'CLIENT'),
       (19, 'BARBER'),
       (20, 'CLIENT'),
       (20, 'BARBER');

INSERT INTO user_profile(user_id, profile_id)
VALUES (10, 1),
       (11, 2),
       (12, 3),
       (13, 4),
       (14, 5),
       (15, 6),
       (16, 7),
       (17, 8),
       (18, 9),
       (19, 10),
       (20, 11);
