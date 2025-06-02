/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

-- ============================================================
-- This file defines the database initialization logic for
-- the Pointyware Authorization Service.
-- ============================================================

CREATE DATABASE pointyware;
\c pointyware;

-- Revoke public schema usage from public role
REVOKE CREATE ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON DATABASE pointyware FROM PUBLIC;

-- ============================================================
-- Authorization
-- ============================================================
CREATE SCHEMA auth;
CREATE TABLE auth.users (
    user_id SERIAL PRIMARY KEY,                        -- Internal primary key for FKs
    public_id UUID DEFAULT gen_random_uuid() NOT NULL, -- Public-facing ID
    email TEXT UNIQUE NOT NULL CHECK (length(email) <= 255),
    password_hash TEXT NOT NULL,
    user_role TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP WITH TIME ZONE,
    CONSTRAINT users_public_id_unique UNIQUE (public_id) -- Enforce uniqueness
);
-- Create an index on public_id for performance
CREATE INDEX idx_users_public_id ON auth.users(public_id);
-- Enable row-level security for the users table
ALTER TABLE auth.users ENABLE ROW LEVEL SECURITY;
CREATE POLICY user_isolation ON auth.users
    USING (user_id = current_setting('app.current_user_id')::INTEGER OR
           current_setting('app.user_role') = 'admin');

CREATE TABLE auth.sessions (
    session_id UUID PRIMARY KEY,
    user_id INTEGER REFERENCES auth.users(user_id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
    device_info JSONB
);

-- ============================================================
-- Define Roles
-- ============================================================
-- Base read-only role for the application
CREATE ROLE app_readonly;
ALTER ROLE app_readonly SET statement_timeout = '30s';
GRANT USAGE ON SCHEMA auth TO app_readonly;
GRANT SELECT ON ALL TABLES IN SCHEMA auth TO app_readonly;
ALTER DEFAULT PRIVILEGES IN SCHEMA auth
    GRANT SELECT ON TABLES TO app_readonly;

-- Base role for authentication service
CREATE ROLE auth_service;
ALTER ROLE auth_service SET statement_timeout = '10s';
GRANT USAGE ON SCHEMA auth TO auth_service;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA auth TO auth_service;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA auth TO auth_service;
ALTER DEFAULT PRIVILEGES IN SCHEMA auth
    GRANT ALL PRIVILEGES ON TABLES TO auth_service;
ALTER DEFAULT PRIVILEGES IN SCHEMA auth
    GRANT USAGE, SELECT ON SEQUENCES TO auth_service;

CREATE ROLE admin_service;
-- no statement timeout for admin service
GRANT USAGE ON SCHEMA auth TO admin_service;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA auth TO admin_service;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA auth TO admin_service;


-- ============================================================
-- Create Users
-- ============================================================
-- App user for authentication operations
CREATE USER auth_user WITH PASSWORD 'auth_password';
GRANT auth_service TO auth_user;
GRANT app_readonly TO auth_user;
ALTER ROLE auth_user CONNECTION LIMIT 10;

-- Admin user with expanded privileges
CREATE USER admin_user WITH PASSWORD 'admin_password';
GRANT admin_service TO admin_user;
GRANT app_readonly TO admin_user;
ALTER ROLE admin_user CONNECTION LIMIT 5;

-- Read-only user for reporting
CREATE USER reporting_user WITH PASSWORD 'reporting_password';
GRANT app_readonly TO reporting_user;
ALTER ROLE reporting_user CONNECTION LIMIT 3;

-- ============================================================
-- Logging
-- ============================================================
-- Create audit schema and table
CREATE SCHEMA audit;
CREATE TABLE audit.database_activity (
    activity_id SERIAL PRIMARY KEY,
    table_name TEXT NOT NULL,
    operation TEXT NOT NULL,
    record_id TEXT,
    old_data JSONB,
    new_data JSONB,
    changed_by TEXT DEFAULT current_user,
    changed_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Grant permissions on audit schema
GRANT USAGE ON SCHEMA audit TO app_readonly;
GRANT SELECT ON ALL TABLES IN SCHEMA audit TO app_readonly;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA audit TO admin_service;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA audit TO admin_service;

-- Example trigger function for audit logging
CREATE OR REPLACE FUNCTION audit.log_changes()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO audit.database_activity (table_name, operation, record_id, new_data)
        VALUES (TG_TABLE_SCHEMA || '.' || TG_TABLE_NAME, TG_OP, NEW.user_id::TEXT, row_to_json(NEW));
        RETURN NEW;
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO audit.database_activity (table_name, operation, record_id, old_data, new_data)
        VALUES (TG_TABLE_SCHEMA || '.' || TG_TABLE_NAME, TG_OP, NEW.user_id::TEXT, row_to_json(OLD), row_to_json(NEW));
        RETURN NEW;
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO audit.database_activity (table_name, operation, record_id, old_data)
        VALUES (TG_TABLE_SCHEMA || '.' || TG_TABLE_NAME, TG_OP, OLD.user_id::TEXT, row_to_json(OLD));
        RETURN OLD;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql SECURITY DEFINER;

-- Apply trigger to sensitive tables
CREATE TRIGGER users_audit_trigger
AFTER INSERT OR UPDATE OR DELETE ON auth.users
FOR EACH ROW EXECUTE FUNCTION audit.log_changes();
