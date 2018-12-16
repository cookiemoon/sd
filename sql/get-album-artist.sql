SELECT *
FROM artist
WHERE id = (SELECT artist_id
            FROM artist_album
            WHERE album_id = ?);