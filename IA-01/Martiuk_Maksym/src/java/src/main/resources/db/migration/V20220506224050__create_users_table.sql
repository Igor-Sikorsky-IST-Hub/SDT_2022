CREATE TABLE users
(
    id       SERIAL       NOT NULL,
    active   BOOLEAN      NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,

    PRIMARY KEY (id)
);
