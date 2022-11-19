package org.example.myServer.config;


public class Configuration {
    private int port;
    private String webroot;

    private String directory;

    private String statisticDirectoryPath;
    private int countOfRequestsToSaveData;

    public int getCountOfRequestsToSaveData() {
        return countOfRequestsToSaveData;
    }


    public String getStatisticDirectoryPath() {
        return statisticDirectoryPath;
    }


    public String getDirectory() {
        return directory;
    }


    public int getPort() {
        return port;
    }


}
