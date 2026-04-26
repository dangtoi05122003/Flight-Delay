SELECT DISTINCT airport_code
FROM (
    SELECT Origin AS airport_code
    FROM {{ source('flight_delay', 'silver_combined_flights') }}
    UNION DISTINCT
    SELECT Dest AS airport_code
    FROM {{ source('flight_delay', 'silver_combined_flights') }}
) t