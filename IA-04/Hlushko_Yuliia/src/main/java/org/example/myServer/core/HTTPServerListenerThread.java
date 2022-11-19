package org.example.myServer.core;


import org.apache.log4j.Logger;
import java.io.IOException;
import java.net.ServerSocket;
public class HTTPServerListenerThread extends Thread {

    private static final Logger LOGGER = Logger.getLogger(HTTPServerListenerThread.class);
    private ServerSocket serverSocket;

    public HTTPServerListenerThread(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        try {
            while (!serverSocket.isClosed()& serverSocket.isBound()) {
                new HTTPConnection(serverSocket.accept());
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        } finally {
            try {
                serverSocket.close();
                LOGGER.error("Server Socket was closed!");
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }


}
