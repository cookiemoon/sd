CREATE TABLE album_genres (
    genres_gname VARCHAR(128),
    album_id    SERIAL,
    PRIMARY KEY(album_id, genres_gname)
);

ALTER TABLE album_genres ADD CONSTRAINT album_genres_fk1 FOREIGN KEY (album_id) REFERENCES album(id);
ALTER TABLE album_genres ADD CONSTRAINT album_genres_fk2 FOREIGN KEY (genres_gname) REFERENCES genres(gname);