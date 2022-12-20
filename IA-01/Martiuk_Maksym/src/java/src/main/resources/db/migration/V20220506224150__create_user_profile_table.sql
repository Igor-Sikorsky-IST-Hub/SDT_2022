CREATE TABLE user_profile
(
    user_id    INTEGER NOT NULL UNIQUE,
    profile_id INTEGER NOT NULL UNIQUE,

    CONSTRAINT user_id_user_profile_fk
        FOREIGN KEY (user_id) REFERENCES users ON DELETE CASCADE,

    CONSTRAINT profile_id_user_profile_fk
        FOREIGN KEY (profile_id) REFERENCES profiles ON DELETE CASCADE
)