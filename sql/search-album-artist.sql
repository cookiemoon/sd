SELECT *
FROM album
WHERE id IN (SELECT album_id
             FROM artist_album
             WHERE artist_id IN (SELECT id
                                 FROM artist
                                 WHERE aname LIKE ?));