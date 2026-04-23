package com.FlightDelay.silver;

import java.util.List;
import java.util.Map;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import com.FlightDelay.config.LoaderYaml;
import com.FlightDelay.config.SparkConfig;

public class transform {
    private SparkConfig sparkconfig = new SparkConfig();
    private static Map<String, Object> config = LoaderYaml.loadConfig("Flight-Delay/config/silver.yml");
    public void run() {
        Map<String, Object> silverConfig = (Map<String, Object>) config.get("silver");
        Map<String, Object> gcpConfig = (Map<String, Object>) config.get("gcp");
        String gcsInput = (String) gcpConfig.get("gcs_input");
        String gcsOutput = (String) gcpConfig.get("gcs_output");
        List<Map<String, Object>> tables = (List<Map<String, Object>>) silverConfig.get("tables");
        for (Map<String, Object> tableEntry : tables) {
            String tableName = tableEntry.keySet().iterator().next();
            Map<String, Object> tableConfig = (Map<String, Object>) tableEntry.get(tableName);
            String inputPath = gcsInput + "/" + tableName;
            String outputPath = gcsOutput + "/" + tableName;
            Dataset<Row> df = sparkconfig.getSparkSession().read().parquet(inputPath);
            if (tableConfig.containsKey("filters")) {
                List<String> filters = (List<String>) tableConfig.get("filters");
                for (String condition : filters) {
                    df = df.filter(condition);
                }
            }
            if (tableConfig.containsKey("drop_nulls")) {
                List<String> dropNullCols = (List<String>) tableConfig.get("drop_nulls");
                String[] colsArr = dropNullCols.toArray(new String[0]);
                df = df.na().drop(colsArr);
            }
            if (tableConfig.containsKey("partition_by")) {
                List<String> partition = (List<String>) tableConfig.get("partition_by");
                df.write().mode("overwrite")
                    .partitionBy(partition.toArray(new String[0]))
                    .parquet(outputPath);
            }else {
                df.write().mode("overwrite").parquet(outputPath);
            }
        }
    }
    public static void main(String[] args) {
        transform app = new transform();
        app.run();
    }
}
