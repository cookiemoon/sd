#!/bin/bash

DB_NAME="dropmusic"

echo "Setting up new PSQL user account"
sudo -u postgres createuser $USER --superuser
echo "Creating database '$DB_NAME'"
createdb $DB_NAME
echo -n "Created succesfully"
echo "You can now log in to this database by typing: 'psql -d $DB_NAME'"
psql -d $DB_NAME