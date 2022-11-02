CREATE TABLE barbershops
(
    id                   SERIAL       NOT NULL,
    name                 VARCHAR(255) NOT NULL,
    city                 VARCHAR(255) NOT NULL,
    amount_of_workplaces INTEGER      NOT NULL,

    PRIMARY KEY (id)
)