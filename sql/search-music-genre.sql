SELECT *
FROM music
WHERE id IN (SELECT music_id
             FROM music_genres
             WHERE genres_gname LIKE ?);