CREATE TABLE orders
(
    id            SERIAL       NOT NULL,
    start         TIMESTAMP    NOT NULL,
    finish        TIMESTAMP    NOT NULL,
    status        VARCHAR(255) NOT NULL,
    client_id     INTEGER      NOT NULL,
    barber_id     INTEGER      NOT NULL,
    barbershop_id INTEGER      NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT client_is_not_a_barber CHECK ( client_id != barber_id ),

    CONSTRAINT client_id_orders_fk
        FOREIGN KEY (client_id) REFERENCES profiles ON DELETE CASCADE,
    CONSTRAINT barber_id_orders_fk
        FOREIGN KEY (barber_id) REFERENCES profiles ON DELETE CASCADE,
    CONSTRAINT barbershop_id
        FOREIGN KEY (barbershop_id) REFERENCES barbershops ON DELETE CASCADE
)