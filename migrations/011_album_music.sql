CREATE TABLE album_music (
    album_id SERIAL,
    music_id SERIAL,
    PRIMARY KEY(album_id, music_id)
);

ALTER TABLE album_music ADD CONSTRAINT album_music_fk1 FOREIGN KEY (album_id) REFERENCES album(id);
ALTER TABLE album_music ADD CONSTRAINT album_music_fk2 FOREIGN KEY (music_id) REFERENCES music(id);