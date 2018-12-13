CREATE TABLE artist_album (
    artist_id SERIAL,
    album_id SERIAL,
    PRIMARY KEY(album_id, artist_id)
);

ALTER TABLE artist_album ADD CONSTRAINT artist_album_fk1 FOREIGN KEY (album_id) REFERENCES album(id);
ALTER TABLE artist_album ADD CONSTRAINT artist_album_fk2 FOREIGN KEY (artist_id) REFERENCES artist(id);