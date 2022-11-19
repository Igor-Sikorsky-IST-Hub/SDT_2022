package org.example.myServer.request.specificRequests;


import org.example.myServer.core.HTTPConnection;
import org.example.myServer.request.Request;

public class Post extends Request {

    public Post(String requestData) {

        super(requestData);
    }

    @Override
    public Request requestProcess(HTTPConnection httpConnection) {

        //відправка всім peer`ам(крім себе), що мають активне підключення до сторінки, даних
        super.page.getStartPage().getConnectionListener().sendData(httpConnection,super.bodyParameters);

        //відправлення даних собі
        super.page.setParameters(super.bodyParameters);
        return new Get(this).requestProcess(httpConnection);

    }
}
