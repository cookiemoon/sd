CREATE TABLE album (
    id      SERIAL,
    title   VARCHAR(128),
    adesc   TEXT,
    label   VARCHAR(128),
    release_date DATE NOT NULL,
    PRIMARY KEY(id)
);