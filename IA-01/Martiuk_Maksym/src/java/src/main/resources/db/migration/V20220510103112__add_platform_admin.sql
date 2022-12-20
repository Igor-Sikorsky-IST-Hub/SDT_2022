CREATE EXTENSION IF NOT EXISTS pgcrypto;

INSERT INTO users(active, username, password)
VALUES (true, 'maks', crypt('1', gen_salt('bf', 12)));

INSERT INTO user_roles(user_id, role)
VALUES (1, 'ADMIN');