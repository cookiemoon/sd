DELETE FROM periods
WHERE artist_id = ? AND artist_id NOT IN (SELECT artist_id FROM artist_album);