SELECT
    Year,
    Month,
    airline_code,
    COUNT(*) AS total_flights,
    SUM(CASE WHEN Cancelled THEN 1 ELSE 0 END) AS total_cancelled,
    ROUND(AVG(dep_delay_minutes), 2) AS avg_monthly_delay
FROM {{ ref('fact_flights') }}
GROUP BY 1, 2, 3