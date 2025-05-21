
-- searchNearCoordinate(:longitude, :latitude, :radius)
SELECT * FROM active_drivers
    WHERE ST_DWithin( ST_MakePoint($1,$2)
                     ,location
                     ,$3);
