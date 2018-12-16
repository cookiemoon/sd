SELECT *
FROM artist
WHERE id = (SELECT artist_id
            FROM artist_album
            WHERE album_id = (SELECT album_id
                              FROM album_music
                              WHERE music_id=?));