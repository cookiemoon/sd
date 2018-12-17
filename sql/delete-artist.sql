DELETE FROM artist
WHERE id = ? AND id NOT IN (SELECT artist_id FROM artist_album);