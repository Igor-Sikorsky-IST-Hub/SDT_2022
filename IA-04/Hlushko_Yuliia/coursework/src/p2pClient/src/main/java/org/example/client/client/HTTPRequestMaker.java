package org.example.client.client;

import java.util.Map;

public class HTTPRequestMaker implements RequestMaker {

    private String method;
    private String url;
    private Map<String, String> parameters;

    public HTTPRequestMaker(String method, String url, Map<String, String> parameters) {
        this.method = method.toUpperCase();
        this.url = url;
        this.parameters = parameters;
    }

    @Override
    public String make() {

        if (this.method.equals("GET")&&!parameters.isEmpty()) {
            this.url = this.url + "?";
            for (String item : this.parameters.keySet()) {
                if (this.parameters.get(item) != null && !item.isEmpty()) {
                    if (!(url.lastIndexOf("?") == url.length() - 1)) url = url + "&";
                    url = url + item + "=" + this.parameters.get(item);
                }
            }
        }

        String httpRequest = method + " " + url + " HTTP/1.1\n";
        httpRequest = httpRequest + "Content-Type: multipart/form-data;boundary=\"boundary\"; charset=utf-8\n";

        if (this.method.equals("GET")) {
            httpRequest = httpRequest + "Content-Length: 0 \n";
        }
        if (this.method.equals("POST")) {

            httpRequest = httpRequest + "Content-Length:" + String.valueOf(byteCount(this.parameters)) + "\n";


            if (this.parameters != null) {
                for (String item : this.parameters.keySet()) {
                    httpRequest = httpRequest + "Content-Disposition: form-data; name=\"" + item + "\"\n";
                    httpRequest = httpRequest + this.parameters.get(item) + "\n";

                }
            }

        }
        return httpRequest;
    }

    private long byteCount(Map<String, String> parameters) {
        long count = 0;
        if (parameters == null) return 0;
        for (String item : parameters.keySet()) {

            count += item.getBytes().length
                    + parameters.get(item).getBytes().length;

        }
        return count;
    }
}
