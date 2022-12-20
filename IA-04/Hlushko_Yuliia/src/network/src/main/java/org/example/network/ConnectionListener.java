package org.example.network;

public interface ConnectionListener {


    void ready(HTTPConnection httpConnection);

    void disconnect(HTTPConnection httpConnection);

    void sendMessage(HTTPConnection httpConnection, String message);

    void getRequest(HTTPConnection httpConnection, String message);


    void exception(HTTPConnection HTTPConnection, Exception exception);
}
