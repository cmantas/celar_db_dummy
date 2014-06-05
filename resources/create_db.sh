echo "run commands:
	DROP DATABASE celardb;
	CREATE DATABASE celardb;
	\q
"

psql -U celaruser postgres
echo "use password celar-user
"
psql -U celaruser -d celardb -a -f celardb.psql
