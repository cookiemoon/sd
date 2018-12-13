-- Gets all the reviews associated with an album
-- Position 1 -> ID of the album
SELECT *
FROM review
WHERE album_id = ?;