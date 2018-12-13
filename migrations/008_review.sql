CREATE TABLE review (
	score	    BIGINT,
	review	    TEXT,
	users_email  VARCHAR(128) NOT NULL,
	album_id	SERIAL NOT NULL
);

ALTER TABLE review ADD CONSTRAINT constraint_0 CHECK (score >= 0 AND score <= 10);
ALTER TABLE review ADD CONSTRAINT review_fk1 FOREIGN KEY (users_email) REFERENCES users(email);
ALTER TABLE review ADD CONSTRAINT review_fk2 FOREIGN KEY (album_id) REFERENCES album(id);