-- Grants editor privileges to a user
-- Position 1 -> Value to set
-- Position 2 -> Email of the user
UPDATE users
SET editor = ?
WHERE email = ?;