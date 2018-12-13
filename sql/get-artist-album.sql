-- Gets all of a musical group's albums
-- Position 1 -> ID of the musical group
SELECT album_id
FROM artist_album
WHERE artist_id = ?;