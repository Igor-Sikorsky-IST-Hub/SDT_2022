package org.example.client.request.specificRequests;




import org.example.client.request.Request;

public class UnknownType extends Request {


    public UnknownType(String requestData) {

        super(requestData);
    }

    @Override
    public Request requestProcess() {
        return this;
    }
}
