package org.example.myServer.response;

import java.io.OutputStream;

public class ResponseBuilderImpl implements ResponseBuilder {

    Response response= new Response();

    @Override
    public  ResponseBuilder setStatusCode(int statusCode) {
        this.response.setStatusCode(statusCode);
        return this;
    }

    @Override
    public ResponseBuilder setStatusText(String statusText) {
        this.response.setStatusText(statusText);
        return this;
    }

    @Override
    public ResponseBuilder setFileBytes(byte[] fileBytes) {
        this.response.setFileBytes(fileBytes);
        return this;
    }


    @Override
    public ResponseBuilder setDate(String date) {
        this.response.setDate(date);
        return this;
    }

    @Override
    public ResponseBuilder setHTTPVersion(String HTTPVersion) {
        this.response.setHTTPVersion(HTTPVersion);
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
    public ResponseBuilder setOutputStream(OutputStream outputStream) {
        this.response.setOutputStream(outputStream);
        return this;
    }

    @Override
        public synchronized Response build() {
            return this.response;
        }

}
