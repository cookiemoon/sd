-- Gets a user
-- Position 1 -> Email of the user
SELECT *
FROM users
WHERE email = ?
LIMIT 1;