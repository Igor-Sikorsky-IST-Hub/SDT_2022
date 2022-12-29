package org.example.client.client;

import org.example.client.request.RequestFactoryImpl;
import org.example.client.response.Response;
import org.example.client.response.ResponseFactory;
import org.example.network.ConnectionListener;
import org.example.network.HTTPConnection;

public class ClientListener implements ConnectionListener {

private ClientFrame clientFrame;

    public ClientListener(ClientFrame clientFrame) {
        this.clientFrame = clientFrame;
    }

    @Override
    public synchronized void sendMessage(HTTPConnection httpConnection, String message) {
        new ResponseTextFrame().printHTMLResponse(message);
    }

    @Override
    public synchronized void getRequest(HTTPConnection httpConnection, String message) {

        Response response = new ResponseFactory().getResponse(new RequestFactoryImpl(message).initialize().requestProcess());
        httpConnection.sendString(response.toString());
    }


    @Override
    public synchronized void exception(HTTPConnection httpConnection, Exception exception) {
        clientFrame.printInformationMessage("Servers problem!");
    }

    @Override
    public synchronized void ready(HTTPConnection httpConnection) {
        clientFrame.printInformationMessage("Connection is ready");
    }

    @Override
    public synchronized void disconnect(HTTPConnection httpConnection) {

        clientFrame.printInformationMessage("Connection is disconnected");

    }
}
