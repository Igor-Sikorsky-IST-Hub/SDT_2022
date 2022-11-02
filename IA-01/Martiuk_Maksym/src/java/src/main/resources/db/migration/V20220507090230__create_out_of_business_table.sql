CREATE TABLE out_of_business
(
    id            SERIAL        NOT NULL,
    cause         VARCHAR(1024) NOT NULL,
    start         TIMESTAMP     NOT NULL,
    finish        TIMESTAMP,
    barbershop_id INTEGER       NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT barbershop_id_out_of_business_fk
        FOREIGN KEY (barbershop_id) REFERENCES barbershops ON DELETE CASCADE
)