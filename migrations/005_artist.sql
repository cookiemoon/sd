CREATE TABLE artist(
    id  SERIAL,
    aname VARCHAR(128) NOT NULL,
    adesc VARCHAR(8192) NOT NULL,
    PRIMARY KEY (id)
);