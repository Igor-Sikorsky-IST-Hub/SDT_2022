package org.example.myServer;

import org.example.myServer.config.Configuration;
import org.example.myServer.config.ConfigurationManager;
import org.example.myServer.core.HTTPServerListenerThread;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class HttpServer {
    private static HTTPServerListenerThread HTTPServerListenerThread = null;
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(HttpServer.class);

    public static void main(String[] args) {
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration configuration = ConfigurationManager.getInstance().getCurrentConfiguration();
        try {
            HTTPServerListenerThread = new HTTPServerListenerThread(configuration.getPort());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HTTPServerListenerThread.start();

        LOGGER.info("Server starting....");


    }


}
