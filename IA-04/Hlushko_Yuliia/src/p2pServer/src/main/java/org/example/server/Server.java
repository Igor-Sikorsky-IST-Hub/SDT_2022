package org.example.server;


import org.apache.log4j.Logger;
import org.example.network.ConnectionListener;
import org.example.network.HTTPConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server  {

    private static final Logger LOGGER = Logger.getLogger(Server.class);
    private static final int PORT = 9000;
    private static ConnectionListener serverConnectionListener = new ServerConnectionListener();

    public static void main(String[] args) {
        new Server();
    }

    private Server() {

        LOGGER.info("Server is running....");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                try {
                    new HTTPConnection(serverSocket.accept(), serverConnectionListener);
                } catch (IOException e) {
                    LOGGER.info("Connection problems!");
                }
            }
        } catch (IOException e) {
            LOGGER.info("socket problems!");
        }

    }

}
