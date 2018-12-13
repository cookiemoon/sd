CREATE TABLE music (
	id	     SERIAL,
	title	 VARCHAR(128) NOT NULL,
	duration INTEGER NOT NULL,
	lyrics	 TEXT,
	PRIMARY KEY(id)
);

ALTER TABLE music ADD CONSTRAINT constraint_0 CHECK (duration >= 0);