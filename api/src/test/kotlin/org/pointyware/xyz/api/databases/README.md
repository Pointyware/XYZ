# Package org.pointyware.xyz.api.data
This test package includes database integration tests which must be run against a running
postgres database. To start the database, 

```shell
initdb -D /usr/local/pgsql/data
postgres -D /usr/local/pgsql/data
  > logfile 2>&1 &
```

```shell
pg_ctl start -l logfile -D /usr/local/pgsql/data
```

The argument -D can be replaced by setting the environment variable PGDATA.
