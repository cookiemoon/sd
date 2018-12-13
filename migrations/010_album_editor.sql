CREATE TABLE album_editor (
    users_email   VARCHAR(256),
    album_id      SERIAL,
    PRIMARY KEY (users_email, album_id)
);

ALTER TABLE album_editor ADD CONSTRAINT album_editor_fk1 FOREIGN KEY (users_email) REFERENCES users(email);
ALTER TABLE album_editor ADD CONSTRAINT album_editor_fk2 FOREIGN KEY (album_id) REFERENCES album(id);