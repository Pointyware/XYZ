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
-- Rider
-- ============================================================
CREATE SCHEMA rider;
CREATE TABLE rider.profiles (
    profile_id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES auth.users(user_id),
    first_name TEXT NOT NULL CHECK (length(first_name) <= 100),
    middle_name TEXT CHECK (length(middle_name) <= 100),
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
GRANT USAGE ON SCHEMA rider, driver, payment, common TO app_readonly;
GRANT SELECT ON ALL TABLES IN SCHEMA rider, driver, payment, common TO app_readonly;
ALTER DEFAULT PRIVILEGES IN SCHEMA rider, driver, payment, common
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
GRANT USAGE ON SCHEMA rider, driver, payment, common TO admin_service;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA rider, driver, payment, common TO admin_service;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA rider, driver, payment, common TO admin_service;


-- ============================================================
-- Create Users
-- ============================================================

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
