SELECT *
FROM album
WHERE id IN (SELECT album_id
             FROM album_genres
             WHERE genres_gname LIKE ?);