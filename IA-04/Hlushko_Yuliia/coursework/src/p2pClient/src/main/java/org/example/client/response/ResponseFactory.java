package org.example.client.response;

import org.example.client.request.Request;


public class ResponseFactory {

    private Response response;

    public ResponseFactory() {

    }

    public synchronized Response getResponse(Request request) {


        if (request.getFileData() != null && !request.getFileData().isEmpty() ){
            response = new ResponseBuilderImpl()
                    .setStatusCode(200)
                    .setStatusText("OK!")
                    .setContentLength(request.getFileData().length())
                    .setContentType("html")
                    .setFileData(request.getFileData()).build();

        } else {
            response = new ResponseBuilderImpl()
                    .setStatusCode(404)
                    .setStatusText("Page not found!")
                    .setContentLength(0)
                    .setContentType("html")
                    .setFileData("").build();
        }
        return this.response;
    }

    public synchronized Response build(String errorResponse) {
        response = new ResponseBuilderImpl()
                .setStatusCode(500)
                .setStatusText("Server problems")
                .setContentLength(0)
                .setContentType("html")
                .setFileData("").build();
        return this.response;
    }
}
