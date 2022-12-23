package org.example.myServer.core;

import java.util.Map;

public interface ConnectionListener {
    void ready(HTTPConnection httpConnection);
    void sendData(HTTPConnection httpConnection, Map<String,String> data);
    void disconnect(HTTPConnection httpConnection);
    void exception(HTTPConnection httpConnection,Exception exception);
}
