WITH base AS (
    SELECT
        CAST(Year AS INT64) AS Year,
        CAST(Month AS INT64) AS Month,
        CAST(Airline AS STRING) AS airline,
        CAST(Operating_Airline AS STRING) AS airline_code,
        CAST(Origin AS STRING) AS origin_airport,
        CAST(Dest AS STRING) AS dest_airport,
        CAST(Distance AS FLOAT64) AS distance,
        CAST(DepDelayMinutes AS FLOAT64) AS dep_delay_minutes,
        CAST(ArrDelayMinutes AS FLOAT64) AS arr_delay_minutes,
        CAST(Cancelled AS BOOL) AS Cancelled
    FROM {{ source('flight_delay', 'silver_combined_flights') }}
)

SELECT * FROM base