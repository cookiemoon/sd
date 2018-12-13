#!/bin/bash

SCRIPTS_DIR=./migrations
DB_NAME="dropmusic"


for file in $SCRIPTS_DIR/*.sql
    do psql -d $DB_NAME -f $file
done