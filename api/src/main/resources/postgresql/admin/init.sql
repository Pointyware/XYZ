-- Initializes entire database from scratch
CREATE USER xyz_api;

CREATE DATABASE xyz;

GRANT CONNECT ON DATABASE xyz TO xyz_api;
