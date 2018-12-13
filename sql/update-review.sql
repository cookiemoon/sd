UPDATE review
SET review = ?, score = ?
WHERE users_email = ? AND album_id = ?;