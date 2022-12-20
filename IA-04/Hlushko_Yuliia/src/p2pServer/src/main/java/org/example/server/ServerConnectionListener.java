package org.example.server;

import org.example.network.ConnectionListener;
import org.example.network.HTTPConnection;

import java.util.*;


public class ServerConnectionListener implements ConnectionListener {
    private static volatile Set<String> connections = Collections.synchronizedSet(new HashSet<>());

    public ServerConnectionListener() {
    }

    @Override
    public synchronized void ready(HTTPConnection httpConnection) {

    }

    @Override
    public synchronized void disconnect(HTTPConnection httpConnection) {

    }

    @Override
    public synchronized void sendMessage(HTTPConnection httpConnection, String message) {

    }

    @Override
    public synchronized void getRequest(HTTPConnection httpConnection, String message) {

        HashMap<String, String> param = new HashMap<>();
        Arrays.stream(message.split("\n")[0].split("\s")[1].split("\\?")[1].split("&")).forEach(x -> {
            param.put(x.split("=")[0], x.split("=")[1]);
        });

        if (param.containsKey("port") & param.get("action").equals("add")) {
            connections.add(param.get("port"));
            httpConnection.sendString(connections.toString());
        } else if (param.containsKey("port") & param.get("action").equals("delete"))
            connections.remove(param.get("port"));


    }

    @Override
    public synchronized void exception(HTTPConnection HTTPConnection, Exception exception) {
        HTTPConnection.sendString("exception " + exception);
    }

}
