SELECT *
FROM music
WHERE id IN (SELECT music_id
             FROM album_music
             WHERE album_id IN (SELECT album_id
                                FROM artist_album
                                WHERE artist_id IN (SELECT id
                                                    FROM artist
                                                    WHERE aname LIKE ?)));