package org.example.myServer.statistics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.log4j.Logger;

import org.example.myServer.config.ConfigurationManager;
import org.example.myServer.util.Json;

import java.io.*;



public class Statistic {

    private static final Logger LOGGER = Logger.getLogger(Statistic.class);
    private static Directory rootDirectory;

    private static Integer countOfRequestsToSaveData = ConfigurationManager
            .getInstance()
            .getCurrentConfiguration()
            .getCountOfRequestsToSaveData();

    private static int iterationCount=0;
    public Statistic() {

    }


    public synchronized void addData(String url) {
        rootDirectory.addStatistics("root" + url);

        iterationCount++;
        if(iterationCount>=countOfRequestsToSaveData){
            Statistic.saveStatistic(ConfigurationManager
                    .getInstance()
                    .getCurrentConfiguration()
                    .getStatisticDirectoryPath());
            iterationCount =0;
        }

    }

    public synchronized Integer getData(String url) {
       return rootDirectory.getStatistics("root" + url);

    }

    public static synchronized void loadStatistic(String filePath) {
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
            rootDirectory.setName("globalRoot");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized void saveStatistic(String filePath) {

        try (PrintWriter out = new PrintWriter(new FileWriter(filePath))) {
            out.write(String.valueOf(Json.toJson(rootDirectory)));
        } catch (Exception e) {
            LOGGER.error("File For Save Statistic Not Found" + e);
        }
    }

}
