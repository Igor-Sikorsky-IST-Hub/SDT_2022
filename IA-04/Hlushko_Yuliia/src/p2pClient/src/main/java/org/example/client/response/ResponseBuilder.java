package org.example.client.response;


public interface ResponseBuilder {

    public ResponseBuilder setStatusCode(int statusCode);


    public ResponseBuilder setStatusText(String statusText);


    public ResponseBuilder setFileData(String fileData);

    public ResponseBuilder setContentType(String contentType);

    public ResponseBuilder setContentLength(int contentLength);


    public Response build();
}
