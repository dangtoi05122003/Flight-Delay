package com.FlightDelay.config;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
public class LoaderYaml {
    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    public static Map<String, Object> loadConfig(String filepath) {
        try(InputStream is = new BufferedInputStream(new FileInputStream(filepath))) {
            return mapper.readValue(is, new TypeReference<Map<String, Object>>() {});
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
