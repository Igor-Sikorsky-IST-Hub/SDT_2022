package org.example.client;

import org.example.client.config.ConfigurationManager;

public class Client {

    private static int PORT = (int) (9000 + Math.random() * 500);

    public static void main(String[] args) {
        ConfigurationManager.getInstance().loadConfigurationFile("p2pClient/src/main/resources/http.json");
        new ClientFrame(ConfigurationManager.getInstance().getCurrentConfiguration().getWebroot(), PORT);

    }


}
