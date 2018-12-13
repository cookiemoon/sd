CREATE TABLE music_genres (
    music_id    VARCHAR(128),
    album_id    SERIAL,
    PRIMARY KEY(album_id, music_id)
);

ALTER TABLE music_genres ADD CONSTRAINT music_genres_fk1 FOREIGN KEY (album_id) REFERENCES album(id);
ALTER TABLE music_genres ADD CONSTRAINT music_genres_fk2 FOREIGN KEY (music_id) REFERENCES music(id);