-- Checks if any users exist
SELECT count(*)
FROM (SELECT * FROM users LIMIT 1) as t;