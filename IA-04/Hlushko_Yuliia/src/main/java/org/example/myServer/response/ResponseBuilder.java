package org.example.myServer.response;

import java.io.OutputStream;

public interface ResponseBuilder {

    public  ResponseBuilder setStatusCode(int statusCode) ;

     public ResponseBuilder setHTTPVersion(String HTTPVersion);

    public  ResponseBuilder setStatusText(String statusText) ;



    public  ResponseBuilder setDate(String date) ;


    public  ResponseBuilder setFileBytes(byte[] fileBytes ) ;

    public  ResponseBuilder setContentType(String contentType) ;

    public  ResponseBuilder setContentLength(int contentLength) ;


    public  ResponseBuilder setOutputStream(OutputStream outputStream) ;


    public  Response build();
}
