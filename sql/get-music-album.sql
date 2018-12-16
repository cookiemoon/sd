SELECT *
FROM album
WHERE id = (SELECT album_id
            FROM album_music
            WHERE music_id = ?);