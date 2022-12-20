package org.example.client.config;


public class Configuration {
    private int port;
    private String webroot;

    private String directory;

    private  int p2pSearcherServerPort;
    private String statisticDirectoryPath;
    private String pathToSourceFileSavingDirectory;

    public String getPathToSourceFileSavingDirectory() {
        return pathToSourceFileSavingDirectory;
    }

    public int getP2pSearcherServerPort() {
        return p2pSearcherServerPort;
    }

    public String getWebroot() {
        return webroot;
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
