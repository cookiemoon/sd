-- Gets all of a musical group's relevant information, filtered by ID
-- Position 1 -> ID of the group
SELECT *
FROM artist
WHERE id = ?
LIMIT 1;