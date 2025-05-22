-- ============================================================
-- This file defines the database initialization logic for
-- the XYZ service.
--
-- ============================================================

CREATE DATABASE xyz;
\c xyz

CREATE EXTENSION postgis;

-- Revoke public schema usage from public role
REVOKE CREATE ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON DATABASE xyz FROM PUBLIC;

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
-- Rider
-- ============================================================
CREATE SCHEMA rider;
CREATE TABLE rider.profiles (
    profile_id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES auth.users(user_id),
    first_name TEXT NOT NULL CHECK (length(first_name) <= 100),
    last_name TEXT NOT NULL CHECK (length(last_name) <= 100),
    phone_number TEXT CHECK (length(phone_number) <= 20),
    rating DECIMAL(3,2) DEFAULT 5.0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE rider.ride_requests (
    request_id SERIAL PRIMARY KEY,
    rider_id INTEGER REFERENCES rider.profiles(profile_id),
    pickup_location POINT NOT NULL,
    dropoff_location POINT NOT NULL,
    request_time TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    status TEXT DEFAULT 'PENDING' CHECK (length(email) <= 50),
    estimated_price DECIMAL(10,2)
);

-- ============================================================
-- Driver
-- ============================================================
CREATE SCHEMA driver;
CREATE TABLE driver.profiles (
    profile_id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES auth.users(user_id),
    first_name TEXT NOT NULL CHECK (length(email) <= 100),
    last_name TEXT NOT NULL CHECK (length(email) <= 100),
    phone_number TEXT CHECK (length(email) <= 20),
    license_number TEXT NOT NULL CHECK (length(email) <= 50),
    vehicle_info JSONB,
    rating DECIMAL(3,2) DEFAULT 5.0,
    is_active BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE driver.availability (
    availability_id SERIAL PRIMARY KEY,
    driver_id INTEGER REFERENCES driver.profiles(profile_id),
    status TEXT DEFAULT 'OFFLINE' CHECK (length(email) <= 50),
    current_location POINT,
    last_updated TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================
-- Market
-- ============================================================
CREATE SCHEMA market;
CREATE TABLE market.rider_bids (
    bid_id SERIAL PRIMARY KEY,
    rider_id INTEGER REFERENCES auth.users(user_id),
    bid_amount DECIMAL(10,2) NOT NULL,
    minimum_driver_rating DECIMAL(3,2),
    request_id INTEGER REFERENCES rider.ride_requests(request_id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP WITH TIME ZONE,
    status TEXT DEFAULT 'PENDING'  CHECK (length(email) <= 50) -- status (ACTIVE, MATCHED, EXPIRED, CANCELLED
);

CREATE TABLE market.driver_asks (
    ask_id SERIAL PRIMARY KEY,
    driver_id INTEGER REFERENCES auth.users(user_id),
    price_per_mile DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP WITH TIME ZONE,
    status TEXT DEFAULT 'ACTIVE' CHECK (length(email) <= 50) -- status (ACTIVE, MATCHED, EXPIRED, CANCELLED
);

CREATE TABLE market.matches (
    match_id SERIAL PRIMARY KEY,
    bid_id INTEGER REFERENCES market.rider_bids(bid_id),
    ask_id INTEGER REFERENCES market.driver_asks(ask_id),
    ride_id INTEGER REFERENCES common.rides(ride_id),
    matched_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    price_per_mile DECIMAL(10,2) NOT NULL
);

-- ============================================================
-- Payment
-- ============================================================
CREATE SCHEMA payment;
-- Payment schema tables
CREATE TABLE payment.methods (
    payment_id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES auth.users(user_id),
    payment_type TEXT NOT NULL CHECK (length(email) <= 50),
    provider TEXT NOT NULL CHECK (length(email) <= 100),
    account_reference TEXT NOT NULL CHECK (length(email) <= 255),
    is_default BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
ALTER TABLE payment.methods ENABLE ROW LEVEL SECURITY;
CREATE POLICY payment_method_isolation ON payment.methods
    USING (user_id = current_setting('app.current_user_id')::INTEGER OR
           current_setting('app.user_role') IN ('admin', 'payment_processor'));

CREATE TABLE payment.transactions (
    transaction_id SERIAL PRIMARY KEY,
    ride_id INTEGER,
    payment_id INTEGER REFERENCES payment.methods(payment_id),
    amount DECIMAL(10,2) NOT NULL,
    status TEXT DEFAULT 'PENDING' CHECK (length(email) <= 50),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP WITH TIME ZONE
);
ALTER TABLE payment.transactions ENABLE ROW LEVEL SECURITY;
CREATE POLICY payment_transaction_isolation ON payment.transactions
    USING (user_id = current_setting('app.current_user_id')::INTEGER OR
           current_setting('app.user_role') IN ('admin', 'payment_processor'));

-- ============================================================
-- region Common
-- ============================================================
CREATE SCHEMA common;
-- Common schema tables for shared data
CREATE TABLE common.rides (
    ride_id SERIAL PRIMARY KEY,
    request_id INTEGER REFERENCES rider.ride_requests(request_id),
    driver_id INTEGER REFERENCES driver.profiles(profile_id),
    start_time TIMESTAMP WITH TIME ZONE,
    end_time TIMESTAMP WITH TIME ZONE,
    pickup_location POINT,
    dropoff_location POINT,
    distance DECIMAL(10,2),
    price DECIMAL(10,2),
    status TEXT DEFAULT 'ASSIGNED' CHECK (length(email) <= 50),
    rider_rating INTEGER,
    driver_rating INTEGER,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================
-- Define Roles
-- ============================================================
-- Base read-only role for the application
CREATE ROLE app_readonly;
ALTER ROLE app_readonly SET statement_timeout = '30s';
GRANT USAGE ON SCHEMA auth, rider, driver, payment, common TO app_readonly;
GRANT SELECT ON ALL TABLES IN SCHEMA auth, rider, driver, payment, common TO app_readonly;
ALTER DEFAULT PRIVILEGES IN SCHEMA auth, rider, driver, payment, common
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

-- Roles for specific application functions
CREATE ROLE rider_service;
ALTER ROLE rider_service SET statement_timeout = '10s';
GRANT USAGE ON SCHEMA rider, common TO rider_service;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA rider TO rider_service;
GRANT SELECT, INSERT, UPDATE ON common.rides TO rider_service;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA rider, common TO rider_service;
ALTER DEFAULT PRIVILEGES IN SCHEMA rider
    GRANT ALL PRIVILEGES ON TABLES TO rider_service;
ALTER DEFAULT PRIVILEGES IN SCHEMA rider
    GRANT USAGE, SELECT ON SEQUENCES TO rider_service;

CREATE ROLE driver_service;
ALTER ROLE driver_service SET statement_timeout = '10s';
GRANT USAGE ON SCHEMA driver, common TO driver_service;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA driver TO driver_service;
GRANT SELECT, UPDATE ON common.rides TO driver_service;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA driver, common TO driver_service;
ALTER DEFAULT PRIVILEGES IN SCHEMA driver
    GRANT ALL PRIVILEGES ON TABLES TO driver_service;
ALTER DEFAULT PRIVILEGES IN SCHEMA driver
    GRANT USAGE, SELECT ON SEQUENCES TO driver_service;

CREATE ROLE payment_service;
ALTER ROLE payment_service SET statement_timeout = '15s';
GRANT USAGE ON SCHEMA payment, common TO payment_service;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA payment TO payment_service;
GRANT SELECT ON common.rides TO payment_service;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA payment TO payment_service;
ALTER DEFAULT PRIVILEGES IN SCHEMA payment
    GRANT ALL PRIVILEGES ON TABLES TO payment_service;
ALTER DEFAULT PRIVILEGES IN SCHEMA payment
    GRANT USAGE, SELECT ON SEQUENCES TO payment_service;

CREATE ROLE admin_service;
-- no statement timeout for admin service
GRANT USAGE ON SCHEMA auth, rider, driver, payment, common TO admin_service;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA auth, rider, driver, payment, common TO admin_service;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA auth, rider, driver, payment, common TO admin_service;


-- ============================================================
-- Create Users
-- ============================================================
-- App user for authentication operations
CREATE USER auth_user WITH PASSWORD 'auth_password';
GRANT auth_service TO auth_user;
GRANT app_readonly TO auth_user;
ALTER ROLE auth_user CONNECTION LIMIT 10;

-- App user for rider operations
CREATE USER rider_user WITH PASSWORD 'rider_password';
GRANT rider_service TO rider_user;
GRANT app_readonly TO rider_user;
ALTER ROLE rider_user CONNECTION LIMIT 50;

-- App user for driver operations
CREATE USER driver_user WITH PASSWORD 'driver_password';
GRANT driver_service TO driver_user;
GRANT app_readonly TO driver_user;
ALTER ROLE driver_user CONNECTION LIMIT 50;

-- App user for payment operations
CREATE USER payment_user WITH PASSWORD 'payment_password';
GRANT payment_service TO payment_user;
GRANT app_readonly TO payment_user;
ALTER ROLE payment_user CONNECTION LIMIT 10;

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
