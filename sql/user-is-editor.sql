-- Gets a user's editor boolean
-- Position 1 -> Email of the user
SELECT editor_f
FROM users
WHERE email = ?;