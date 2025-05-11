CREATE EXTENSION postgis;

CREATE TABLE active_drivers (
    id INT PRIMARY KEY REFERENCES users(id),
    location POINT NOT NULL
);

-- searchNearCoordinate:
SELECT * FROM active_drivers
    WHERE ST_DWithin( ST_MakePoint(:longitude,:latitude)
                     ,location
                     ,:radius);
