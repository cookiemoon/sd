CREATE TABLE artist_editor (
    users_email   VARCHAR(256),
    artist_id      SERIAL,
    PRIMARY KEY (users_email, artist_id)
);

ALTER TABLE artist_editor ADD CONSTRAINT artist_editor_fk1 FOREIGN KEY (users_email) REFERENCES users(email);
ALTER TABLE artist_editor ADD CONSTRAINT artist_editor_fk2 FOREIGN KEY (artist_id) REFERENCES artist(id);