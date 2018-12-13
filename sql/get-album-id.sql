-- Gets all the info associated with an album, filtered by ID
-- Position 1 -> ID of the album
SELECT *
FROM album
WHERE id = ?;