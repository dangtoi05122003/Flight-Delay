SELECT
    airline_code,
    COUNT(*) AS total_flights,
    SUM(CASE WHEN Cancelled THEN 1 ELSE 0 END) AS total_cancelled,
    SUM(CASE WHEN dep_delay_minutes <= 15 THEN 1 ELSE 0 END) AS on_time_flights,
    SUM(CASE WHEN dep_delay_minutes > 60 THEN 1 ELSE 0 END) AS severe_delay_flights,
    ROUND(AVG(dep_delay_minutes), 2) AS avg_delay_minutes
FROM {{ ref('fact_flights') }}
GROUP BY 1