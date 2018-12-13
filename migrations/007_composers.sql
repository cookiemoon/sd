CREATE TABLE composers (
    artist_id SERIAL,
    music_id SERIAL,
    PRIMARY KEY (artist_id, music_id)
);

ALTER TABLE composers ADD CONSTRAINT composers_fk1 FOREIGN KEY (artist_id) REFERENCES artist(id);
ALTER TABLE composers ADD CONSTRAINT composers_fk2 FOREIGN KEY (music_id) REFERENCES music(id);
