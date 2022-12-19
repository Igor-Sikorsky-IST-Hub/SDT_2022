CREATE TABLE profile_activation_code
(
    profile_id      INTEGER NOT NULL UNIQUE,
    activation_code VARCHAR(255),

    CONSTRAINT profile_id_profile_activation_code_fk
        FOREIGN KEY (profile_id) REFERENCES profiles
)