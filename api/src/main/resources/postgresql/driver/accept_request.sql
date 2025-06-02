/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

-- ============================================================
-- Accept a Rider's Request
-- ============================================================

-- Driver accepting a ride request
BEGIN TRANSACTION ISOLATION LEVEL REPEATABLE READ;

-- Check if the request is still available
SELECT * FROM market.rider_bids
WHERE bid_id = $1 AND status = 'ACTIVE' FOR UPDATE;

-- If row was found, proceed with matching
UPDATE market.rider_bids
SET status = 'MATCHED'
WHERE bid_id = $1;

INSERT INTO market.matches (bid_id, ask_id, ride_id, matched_at, price_per_mile)
VALUES ($1, $2, $3, NOW(), $4);

-- Create the actual ride
INSERT INTO common.rides (request_id, driver_id, status, ...)
VALUES (...);

COMMIT;
