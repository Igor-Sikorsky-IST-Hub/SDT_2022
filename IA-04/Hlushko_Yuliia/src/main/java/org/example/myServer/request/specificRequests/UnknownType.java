package org.example.myServer.request.specificRequests;



import org.example.myServer.core.ConnectionListener;
import org.example.myServer.core.HTTPConnection;
import org.example.myServer.request.Request;

public class UnknownType extends Request {


    public UnknownType(String requestData) {

        super(requestData);
    }

    @Override
    public Request requestProcess(HTTPConnection httpConnection) {
        return this;
    }
}
