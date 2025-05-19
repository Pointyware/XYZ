# Package org.pointyware.xyz.api.data
Each repository contains a collection of data-access objects (DAOs) that are
used to interact with the PostgreSQL database.
A repository corresponds to a schema in postgres, which is a logical 
grouping of tables.
A DAO corresponds to a table in the database.

## Database
The script postgresql/init.sql in the resources directory is used to initialize the database but
contains placeholder password values. To use proper passwords, use the following command to replace
the placeholders with password environment variables:
```shell
cat postgresql/init.sql | \
  sed "s/auth_password/$XYZ_DB_AUTH_PASSWORD/g" | \
  sed "s/rider_password/$XYZ_DB_RIDER_PASSWORD/g" | \
  sed "s/driver_password/$XYZ_DB_DRIVER_PASSWORD/g" | \
  sed "s/payment_password/$XYZ_DB_PAYMENT_PASSWORD/g" | \
  sed "s/admin_password/$XYZ_DB_ADMIN_PASSWORD/g" | \
  sed "s/reporting_password/$XYZ_DB_REPORTING_PASSWORD/g" > temp/init_injected.sql
```
