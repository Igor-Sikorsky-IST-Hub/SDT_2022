package org.example.client.response;

public class ResponseBuilderImpl implements ResponseBuilder {

    Response response = new Response();

    public ResponseBuilderImpl() {
    }

    @Override
    public ResponseBuilder setStatusCode(int statusCode) {
        this.response.setStatusCode(statusCode);
        return this;
    }

    @Override
    public ResponseBuilder setStatusText(String statusText) {
        this.response.setStatusText(statusText);
        return this;
    }

    @Override
    public ResponseBuilder setFileData(String fileData) {
        this.response.setFileData(fileData);
        return this;
    }


    @Override
    public ResponseBuilder setContentType(String contentType) {
        this.response.setContentType(contentType);
        return this;
    }

    @Override
    public ResponseBuilder setContentLength(int contentLength) {
        this.response.setContentLength(contentLength);
        return this;
    }


    @Override
    public synchronized Response build() {
        return this.response;
    }


}
