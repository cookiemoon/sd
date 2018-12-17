CREATE TABLE users (
    email   VARCHAR(256) UNIQUE,
    pwd     VARCHAR(128) NOT NULL,
    editor  BOOL NOT NULL DEFAULT false,
    dropbox_token VARCHAR(256),
    PRIMARY KEY(email)
)