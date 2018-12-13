-- Gets a user's review for a certain album
-- Position 1 -> Email of the user
-- Position 2 -> ID of the album
SELECT *
FROM review
WHERE album_id = ? AND users_email = ?;