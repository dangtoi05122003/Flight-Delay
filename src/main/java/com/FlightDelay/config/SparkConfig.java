package com.FlightDelay.config;

import org.apache.spark.sql.SparkSession;

public class SparkConfig {
    private SparkSession spark;
    public SparkConfig() {
        this.spark = SparkSession.builder()
            .master("local[*]")
            .config("spark.hadoop.fs.defaultFS", "hdfs://localhost:9000")
            .appName("FlightDelay").getOrCreate();
    }
    public SparkSession getSparkSession() {
        return this.spark;
    }
}
