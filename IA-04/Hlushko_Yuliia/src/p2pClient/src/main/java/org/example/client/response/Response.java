package org.example.client.response;

public class Response {


    protected String HTTPVersion;
    protected int statusCode;
    protected String statusText;
    protected String date;

    protected String contentType;
    protected int contentLength;


    protected String fileData;


    protected Response() {

    }


    public void setHTTPVersion(String HTTPVersion) {
        this.HTTPVersion = HTTPVersion;
    }


    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }


    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }


    public void setDate(String date) {
        this.date = date;
    }


    public void setFileData(String fileData) {
        this.fileData = fileData;
    }


    public void setContentType(String contentType) {
        this.contentType = contentType;
    }


    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }


    @Override
    public String toString() {
        return "HTTP/1.1 " + statusCode + " " + statusText + "\n" +
                "Content-Type: " + this.contentType + "\n" +
                "Content-Length: " + this.contentLength + "\n\n" +
                fileData;
    }


}
