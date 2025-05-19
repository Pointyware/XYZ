
-- searchNearCoordinate(:longitude, :latitude, :radius)
SELECT * FROM active_drivers
    WHERE ST_DWithin( ST_MakePoint(:longitude,:latitude)
                     ,location
                     ,:radius);
