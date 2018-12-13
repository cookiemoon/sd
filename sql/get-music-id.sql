-- Gets all of a music's relevant information, filtered by ID
-- Position 1 -> ID of the music
SELECT *
FROM music
WHERE id = ?;