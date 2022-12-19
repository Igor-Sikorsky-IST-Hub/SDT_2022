CREATE TABLE clients
(
    barbershop_id INTEGER NOT NULL,
    client_id     INTEGER NOT NULL,

    CONSTRAINT barbershop_id_clients_fk
        FOREIGN KEY (barbershop_id) REFERENCES barbershops,

    CONSTRAINT client_id_clients_fk
        FOREIGN KEY (client_id) REFERENCES profiles
)