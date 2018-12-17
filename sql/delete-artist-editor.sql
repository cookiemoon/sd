DELETE FROM artist_editor
WHERE artist_id = ? AND artist_id NOT IN (SELECT artist_id FROM artist_album);