-- Gets all the music belonging to an album
-- Position 1 -> ID of the album
SELECT music_id
FROM album_music
WHERE album_id = ?;