echo "use password 'celar-user'
	then run command
	CREATE DATABASE celardb;


"

psql -U celaruser postgres

psql -U celaruser -d celardb -a -f celardb.psql
