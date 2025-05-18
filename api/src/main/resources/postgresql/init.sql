-- ============================================================
-- This file defines the database initialization logic for
-- the XYZ service.
--
-- ============================================================

-- ============================================================
-- Create Database
-- ============================================================

-- ============================================================
-- Create Schema
-- ============================================================

-- ============================================================
-- Create Tables
-- ============================================================

-- ============================================================
-- Define Roles
-- ============================================================

-- ============================================================
-- Assign Permissions
-- ============================================================

-- ============================================================
-- Create Users
-- ============================================================

-- ============================================================
-- Assign Roles
-- ============================================================

-- ============================================================
-- Logging
-- ============================================================


CREATE DATABASE xyz;
GRANT CONNECT ON DATABASE xyz TO xyz_api;

CREATE TABLE xyz.users(
    id INT PRIMARY KEY,
    email TEXT NOT NULL,
    pass_hash TEXT NOT NULL,
    salt TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
GRANT ALL ON TABLE xyz.users TO xyz_api;

CREATE TABLE xyz.user_permissions(
    user_id INT REFERENCES users(id),
    permission TEXT NOT NULL
);
GRANT ALL ON TABLE xyz.user_permissions TO xyz_api;

CREATE TABLE xyz.authorizations(
    email TEXT NOT NULL,
    token TEXT NOT NULL
);
GRANT ALL ON TABLE xyz.authorizations TO xyz_api;
