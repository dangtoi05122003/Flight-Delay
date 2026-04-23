package com.FlightDelay.bronze;
import java.util.List;
import java.util.Map;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import com.FlightDelay.config.LoaderYaml;
import com.FlightDelay.config.SparkConfig;

public class ingestion {
    private SparkConfig sparkConfig = new SparkConfig();
    private static Map<String, Object> config = LoaderYaml.loadConfig("Flight-Delay/config/bronze.yml");
    public void run() {
        String inputPath = (String) config.get("local_path");
        String gcsPath = (String) config.get("gcs_path");
        List<Map<String, Object>> tables = (List<Map<String, Object>>) config.get("tables");
        for(Map<String, Object> table: tables) {
            String name = (String) table.get("name");
            String pattern = (String) table.get("pattern");
            List<String> partition = (List<String>) table.get("partition_by");
            Dataset<Row> df = sparkConfig.getSparkSession().read().option("header", true).csv(inputPath + "/" + pattern);
            if(!partition.isEmpty()) {
                df.write().mode("overwrite")
                    .partitionBy(partition.toArray(new String[0]))
                    .parquet(gcsPath + "/" + name);
            }
            else {
                df.write().mode("overwrite").parquet(gcsPath + "/" + name);
            }
        }
    }
    public static void main(String[] args) {
        ingestion app = new ingestion();
        app.run();
    }
}
