SELECT *
FROM music
WHERE id IN (SELECT music_id
             FROM album_music
             WHERE album_id IN (SELECT id
                                FROM album
                                WHERE title LIKE ?));