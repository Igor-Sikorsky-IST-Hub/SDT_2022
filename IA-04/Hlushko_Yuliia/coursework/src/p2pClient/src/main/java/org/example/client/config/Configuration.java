package org.example.client.config;


public class Configuration {
    private String webroot;
    private String directory;
    private String databaseProperties;
    private String pathToSourceFileSavingDirectory;

    public String getPathToSourceFileSavingDirectory() {
        return pathToSourceFileSavingDirectory;
    }
    public String getWebroot() {
        return webroot;
    }

    public String getDirectory() {
        return directory;
    }

    public String getDatabaseProperties() {
        return databaseProperties;
    }

}
