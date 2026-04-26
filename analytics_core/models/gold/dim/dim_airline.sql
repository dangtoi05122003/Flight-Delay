SELECT DISTINCT
    Code AS airline_code,
    Description AS airline_name
FROM {{ source('flight_delay', 'silver_airlines') }}