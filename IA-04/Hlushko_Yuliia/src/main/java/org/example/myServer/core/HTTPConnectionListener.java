package org.example.myServer.core;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class HTTPConnectionListener implements ConnectionListener {

    private volatile List<HTTPConnection> connections = new CopyOnWriteArrayList<>();


    private static final Logger LOGGER = Logger.getLogger(HTTPConnectionListener.class);

    public HTTPConnectionListener() {
    }

    @Override
    public synchronized void ready(HTTPConnection httpConnection) {
        connections.add(httpConnection);
    }

    @Override
    public synchronized void sendData(HTTPConnection httpConnection, Map<String, String> data) {
        sendAllConnections(httpConnection, data);
    }

    @Override
    public synchronized void disconnect(HTTPConnection httpConnection) {
        connections.remove(httpConnection);
        httpConnection.disconnect();
    }

    @Override
    public synchronized void exception(HTTPConnection httpConnection, Exception exception) {

        LOGGER.error("Connection exception : " + exception + " from " + httpConnection.toString());

    }

    private void sendAllConnections( HTTPConnection httpConnection,Map<String, String> data) {
        LOGGER.info(connections.size() + "  connections size ");
        for (HTTPConnection connection : connections)
            if(!(connection==httpConnection))
            connection.getData(data);
    }

}
