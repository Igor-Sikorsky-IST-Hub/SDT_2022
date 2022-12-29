package org.example.client.client;

import org.apache.log4j.Logger;
import org.example.network.ConnectionListener;
import org.example.network.HTTPConnection;

import java.io.IOException;
import java.net.ServerSocket;

public class MyServerPortListener implements  PortListener{

    private static final Logger LOGGER = Logger.getLogger(MyServerPortListener.class);

    public MyServerPortListener() {
    }

    @Override
    public void startListen(int port, ConnectionListener connectionListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (ServerSocket serverSocket = new ServerSocket(port)) {
                    while (true) {
                        try {
                            new HTTPConnection(serverSocket.accept(), connectionListener);
                        } catch (IOException e) {
                            LOGGER.info("Connection problems!");
                        }
                    }
                } catch (IOException e) {
                    LOGGER.info("socket problems!");
                }
            }
        }).start();
    }
}
