SELECT
    origin_airport,
    dest_airport,
    airline_code,
    COUNT(*) AS total_flights,
    ROUND(AVG(distance), 0) AS avg_distance,
    ROUND(AVG(dep_delay_minutes), 2) AS avg_dep_delay,
    SUM(CASE WHEN Cancelled THEN 1 ELSE 0 END) AS total_cancelled
FROM {{ ref('fact_flights') }}
GROUP BY 1, 2, 3