CREATE TABLE periods (
    artist_id SERIAL,
    start_date DATE,
    end_date DATE,
    PRIMARY KEY(start_date)
);

ALTER TABLE periods ADD CONSTRAINT artist_id_fk1 FOREIGN KEY (artist_id) REFERENCES artist(id);