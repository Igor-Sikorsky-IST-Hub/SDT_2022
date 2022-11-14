package org.example.myServer.core;


import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;


public class HTTPServerListenerThread extends Thread {

    private static final Logger LOGGER = Logger.getLogger(HTTPServerListenerThread.class);
    private int port;
    private ServerSocket serverSocket;
    private HTTPConnectionListener listener = new HTTPConnectionListener();

    public HTTPServerListenerThread(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(this.port);
    }

    @Override
    public void run() {

        try {
            while (true) {
                new HTTPConnection(listener, serverSocket.accept());
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        } finally {
            try {
                serverSocket.close();
                LOGGER.error("Server Socket was closed!!!!!!!!!!!!!!!!!!!!!");
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }


}
