CREATE TABLE users (
    email   VARCHAR(256) UNIQUE,
    pwd     VARCHAR(128) NOT NULL,
    editor  BOOL NOT NULL DEFAULT false,
    PRIMARY KEY(email)
)