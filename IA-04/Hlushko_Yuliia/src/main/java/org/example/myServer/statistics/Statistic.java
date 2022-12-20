package org.example.myServer.statistics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.log4j.Logger;
import org.example.myServer.config.ConfigurationManager;
import org.example.myServer.util.Json;

import java.io.*;
import java.util.HashMap;


public class Statistic {

    private static final Logger LOGGER = Logger.getLogger(Statistic.class);
    private static Directory rootDirectory;
    private String currentDirectoryPath;
    private Directory currentDirectory;

    public Statistic() {
        currentDirectoryPath = ConfigurationManager
                .getInstance()
                .getCurrentConfiguration()
                .getDirectory();
        currentDirectory = rootDirectory;
    }


    public synchronized void addData(String url) {
        String[] path = url.split("/");

        int iter = 1;
        while (iter < path.length) {

            if (currentDirectory.getFiles().containsKey(currentDirectoryPath)) {
                currentDirectory.getFiles().get(currentDirectoryPath).incrementCount();
            } else {
                currentDirectory.getFiles().put(currentDirectoryPath, new Directory(currentDirectoryPath, new HashMap<>()));
            }
            currentDirectory = currentDirectory.getFiles().get(currentDirectoryPath);
            currentDirectoryPath = path[iter];
            iter++;
        }
        currentDirectoryPath = path[path.length - 1];
        if (currentDirectory.getFiles().containsKey(currentDirectoryPath)) {
            currentDirectory.getFiles().get(currentDirectoryPath).incrementCount();
        } else {
            currentDirectory.getFiles().put(currentDirectoryPath, new Directory(currentDirectoryPath));
        }

    }

    public static void loadStatistic(String filePath) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            LOGGER.error("File For Load Statistic Not Found" + e);
        }
        StringBuffer sb = new StringBuffer();
        int i;
        while (true) {
            try {
                if (!((i = fileReader.read()) != -1)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sb.append((char) i);
        }
        JsonNode conf = null;
        try {
            conf = Json.parse(sb.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            rootDirectory = Json.fromJson(conf, Directory.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveStatistic(String filePath) {

        try (PrintWriter out = new PrintWriter(new FileWriter(filePath))) {
            out.write(String.valueOf(Json.toJson(rootDirectory)));
        } catch (Exception e) {
            LOGGER.error("File For Save Statistic Not Found" + e);
        }
    }

}
