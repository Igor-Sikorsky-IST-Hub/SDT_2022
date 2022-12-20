CREATE TABLE user_roles
(
    user_id INTEGER      NOT NULL,
    role    VARCHAR(255) NOT NULL,

    CONSTRAINT user_id_in_user_roles_fk
        FOREIGN KEY (user_id) REFERENCES users ON DELETE CASCADE
);
