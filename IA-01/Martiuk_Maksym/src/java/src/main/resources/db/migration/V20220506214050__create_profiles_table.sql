CREATE TABLE profiles
(
    id      SERIAL       NOT NULL,
    name    VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    email   VARCHAR(255) NOT NULL UNIQUE,
    avatar  VARCHAR(255),

    PRIMARY KEY (id)
)