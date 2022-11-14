package org.example.myServer.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.log4j.Logger;
import org.example.myServer.util.Json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {
    private static ConfigurationManager configurationManager;
    private static Configuration currentConfiguration;
    private static final Logger LOGGER = Logger.getLogger(ConfigurationManager.class);

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

}
