SELECT *
FROM artist
WHERE id IN (SELECT artist_id
             FROM artist_album
             WHERE album_id IN (SELECT id
                                FROM album
                                WHERE title LIKE ?));