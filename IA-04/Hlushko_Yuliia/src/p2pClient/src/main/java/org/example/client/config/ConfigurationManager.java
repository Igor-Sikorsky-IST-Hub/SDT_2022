package org.example.client.config;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.JsonNode;

import org.apache.log4j.Logger;
import org.example.client.util.Json;

import java.io.*;


public class ConfigurationManager {
    private static ConfigurationManager configurationManager;
    private static Configuration currentConfiguration;
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(ConfigurationManager.class));

    private ConfigurationManager() {
    }

    public static ConfigurationManager getInstance() {
        if (configurationManager == null)
            configurationManager = new ConfigurationManager();
        return configurationManager;
    }


    public void loadConfigurationFile(String filePath) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            LOGGER.error("Config file not found!!!");
            throw new RuntimeException(e);
        }
        StringBuffer sb = new StringBuffer();
        int i;
        while (true) {
            try {
                if (((i = fileReader.read()) == -1)) break;
            } catch (IOException e) {
                LOGGER.error("Can't read config");
                throw new RuntimeException(e);
            }
            sb.append((char) i);
        }
        JsonNode conf = null;
        try {
            conf = Json.parse(sb.toString());
        } catch (IOException e) {
            LOGGER.error("Can't parsed it");
            throw new RuntimeException(e);
        }
        try {
            currentConfiguration = Json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            LOGGER.error("Can't convert from json");
            throw new RuntimeException(e);
        }
    }


    public Configuration getCurrentConfiguration() {
        if (currentConfiguration == null) {
            throw new RuntimeException("Configuration not found");
        }
        return currentConfiguration;
    }

    public void saveCurrentConfiguration(String filePath) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filePath))) {
            out.write(Json.toJson(currentConfiguration).toString());
        } catch (Exception e) {
            LOGGER.error("File For Save Statistic Not Found" + e);
        }
    }

}
