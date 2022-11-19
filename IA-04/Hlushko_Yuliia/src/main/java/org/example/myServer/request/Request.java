package org.example.myServer.request;


import org.example.myServer.core.HTTPConnection;
import org.example.myServer.pages.pageService.RequestPage;

import java.util.*;

public abstract class Request {

    protected String method;
    protected String path;
    protected Map<String, String> headersParameters = new HashMap<>();
    protected String HTTPVersion;

    protected Map<String, String> allOtherHeaders = new HashMap<>();

    protected Map<String, String> bodyParameters = new HashMap<>();
    protected byte[] fileBytes;

    private String requestData;
    protected RequestPage page;


    public Request(String requestData) {
        this.requestData = requestData;

        //розбиття даних, що прийшли в запиті

        this.method = this.requestData.split("\n")[0].split("\s")[0];

        if (requestData.split("").length > 2) {
            if (this.requestData.split(" ")[1].contains("?")) {
                this.path = this.requestData.split("\n")[0].split("\s")[1].split("\\?")[0];

                String dataString = this.requestData.split("\n")[0].split("\s")[1].split("\\?")[1];
                Arrays.stream(dataString.split("&")).forEach(x -> {
                    this.headersParameters.put(x.split("=")[0], x.split("=")[1]);
                });
            } else {
                this.path = this.requestData.split("\n")[0].split("\s")[1];
            }

            this.HTTPVersion = this.requestData.split("\n")[0].split("\s")[2];
            this.page = new RequestPage(path);

            List<String> param = Arrays.stream(this.requestData.split("\n")).toList();
            Iterator<String> iterator = param.iterator();
            iterator.next();
            while (iterator.hasNext()) {
                String x = iterator.next();
                if (x.isBlank()) continue;
                if (x.split("\s").length > 1) {
                    String header = x.split("\s")[0];
                    String value = x.split("\s")[1];

                    allOtherHeaders.put(header, value);
                } else {

                    if (!x.isBlank()) {
                        Arrays.stream(x.split("&")).forEach(y -> {
                            if(y.split("=").length>1)
                            this.bodyParameters.put(y.split("=")[0], y.split("=")[1]);
                            else this.bodyParameters.put(y.split("=")[0], null);
                        });
                    }
                }

//                if (header.equals("Content-Disposition:")) {
//                    String key = x.split("\s")[2].split("\"")[1];
//
//                    String bodyParamValue = iterator.next() != null ? iterator.next() : null;
//                    bodyParamValue.replace('\n', ' ');
//
//                    this.bodyParameters.put(key, bodyParamValue);
//
//                }
            }

        }
    }

    public Request(Request request) {
        this.method = request.getMethod();
        this.path = request.getPath();
        this.headersParameters = request.getHeadersParameters();
        this.HTTPVersion = request.getHTTPVersion();

        this.allOtherHeaders = request.getAllOtherHeaders();

        this.bodyParameters = request.getBodyParameters();
        this.page = request.getPage();
    }

    public RequestPage getPage() {
        return page;
    }

    public void setPage(RequestPage page) {
        this.page = page;
    }


    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getBodyParameters() {
        return bodyParameters;
    }

    public void setBodyParameters(Map<String, String> bodyParameters) {
        this.bodyParameters = bodyParameters;
    }

    abstract public Request requestProcess(HTTPConnection httpConnection);

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getHTTPVersion() {
        return HTTPVersion;
    }

    public Map<String, String> getAllOtherHeaders() {
        return allOtherHeaders;
    }

    public void setAllOtherHeaders(Map<String, String> allOtherHeaders) {
        this.allOtherHeaders = allOtherHeaders;
    }

    public Map<String, String> getHeadersParameters() {
        return headersParameters;
    }

    public void setHeadersParameters(Map<String, String> headersParameters) {
        this.headersParameters = headersParameters;
    }

}
