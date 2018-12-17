CREATE TABLE music_genres (
    music_id    SERIAL,
    genres_gname    VARCHAR(128),
    PRIMARY KEY(genres_gname, music_id)
);

ALTER TABLE music_genres ADD CONSTRAINT music_genres_fk1 FOREIGN KEY (genres_gname) REFERENCES genres(gname);
ALTER TABLE music_genres ADD CONSTRAINT music_genres_fk2 FOREIGN KEY (music_id) REFERENCES music(id);