package org.example.myServer;

import org.example.myServer.config.Configuration;
import org.example.myServer.config.ConfigurationManager;
import org.example.myServer.core.HTTPServerListenerThread;
import org.example.myServer.statistics.Statistic;
import java.io.IOException;


public class HttpServer {
    private static HTTPServerListenerThread HTTPServerListenerThread = null;
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(HttpServer.class);

    public static void main(String[] args) {
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration configuration = ConfigurationManager.getInstance().getCurrentConfiguration();
        Statistic.loadStatistic(configuration.getStatisticDirectoryPath());

        try {
            HTTPServerListenerThread = new HTTPServerListenerThread(configuration.getPort());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HTTPServerListenerThread.start();

        LOGGER.info("Server starting....");

    }
}
