SELECT *
FROM users
WHERE email = ? AND dropbox_token IS NOT NULL;