/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

-- ============================================================
-- This file defines the database destruction logic for
-- the XYZ service.
-- ============================================================
DROP DATABASE xyz; -- includes all schemas and tables

DROP ROLE app_readonly;
DROP ROLE auth_service;
DROP ROLE rider_service;
DROP ROLE driver_service;
DROP ROLE payment_service;
DROP ROLE admin_service;

DROP USER auth_user;
DROP USER rider_user;
DROP USER driver_user;
DROP USER payment_user;
DROP USER admin_user;
DROP USER reporting_user;
