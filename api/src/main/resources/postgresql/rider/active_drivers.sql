
/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

-- searchNearCoordinate(:longitude, :latitude, :radius)
SELECT * FROM active_drivers
    WHERE ST_DWithin( ST_MakePoint($1,$2)
                     ,location
                     ,$3);
