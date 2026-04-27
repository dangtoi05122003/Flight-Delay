WITH base AS (
    SELECT DISTINCT airline_code
    FROM {{ ref('fact_flights') }}
)
SELECT
    Code AS airline_code,
    Description AS airline_name
FROM {{ source('flight_delay', 'silver_airlines') }} s
INNER JOIN base f ON f.airline_code = s.Code