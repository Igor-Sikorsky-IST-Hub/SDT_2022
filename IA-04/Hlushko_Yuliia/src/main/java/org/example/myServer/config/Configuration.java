package org.example.myServer.config;


public class Configuration {
    private int port;
    private String webroot;

    private String directory;

    private String statisticDirectoryPath;

    public String getStatisticDirectoryPath() {
        return statisticDirectoryPath;
    }

    public void setStatisticDirectoryPath(String statisticDirectoryPath) {
        this.statisticDirectoryPath = statisticDirectoryPath;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getWebroot() {
        return webroot;
    }

    public void setWebroot(String webroot) {
        this.webroot = webroot;
    }

}
