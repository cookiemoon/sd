SELECT *
FROM artist
WHERE id IN (SELECT artist_id
             FROM artist_album
             WHERE album_id IN (SELECT album_id
                                FROM album_music
                                WHERE music_id IN (SELECT id
                                                   FROM music
                                                   WHERE title LIKE ?)));