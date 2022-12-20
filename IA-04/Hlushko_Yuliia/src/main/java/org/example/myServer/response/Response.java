package org.example.myServer.response;


import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Map;


public class Response {
    private static final Logger LOGGER = Logger.getLogger(Response.class);

    protected String HTTPVersion;
    protected int statusCode;
    protected String statusText;
    protected String date;

    protected String contentType;
    protected int contentLength;
    protected OutputStream outputStream;


    protected byte[] fileBytes;

    protected Response() {
    }

    public void send() throws IOException {
        var printStream = new PrintStream(this.outputStream);
        printStream.printf("HTTP/1.1 %s%n", statusCode, statusText);
        printStream.printf("Date: " + this.date + "%n");
        printStream.printf("Connection: keep-alive");
        printStream.printf("Content-Type: " + this.contentType + "%n");
        printStream.printf("Content-Length: %s%n%n", ((long) this.contentLength));
        if (fileBytes != null) this.outputStream.write(fileBytes);

    }


    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public String getHTTPVersion() {
        return HTTPVersion;
    }

    public void setHTTPVersion(String HTTPVersion) {
        this.HTTPVersion = HTTPVersion;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }


}
