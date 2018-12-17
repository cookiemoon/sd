-- Gets all the genres associated with a certain album
-- Position 1 -> ID of the album
SELECT genres_gname
FROM album_genres
WHERE album_id = ?;