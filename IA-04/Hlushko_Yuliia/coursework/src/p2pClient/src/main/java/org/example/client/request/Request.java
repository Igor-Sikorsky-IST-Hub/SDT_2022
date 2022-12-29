package org.example.client.request;


import org.example.client.pages.pageService.RequestPage;

import java.util.*;

public abstract class Request {

    protected String method;
    protected String path;
    protected Map<String, String> headersParameters = new HashMap<>();
    protected String HTTPVersion;

    protected Map<String, String> allOtherHeaders = new HashMap<>();

    protected Map<String, String> bodyParameters = new HashMap<>();
    protected String fileData;

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

                    if (!header.equals("Content-Disposition:"))
                        allOtherHeaders.put(header, value);


                    if (header.equals("Content-Disposition:")) {
                        String key = x.split("\s")[2].split("\"")[1];

                        String maybeParam = iterator.next();

                        String bodyParamValue = maybeParam != null ? maybeParam : null;
                        System.out.println("param " + bodyParamValue);
                        bodyParamValue.replace('\n', ' ');

                        this.bodyParameters.put(key, bodyParamValue);

                    }
                }
            }

        }

    }


    public RequestPage getPage() {
        return page;
    }

    public void setPage(RequestPage page) {
        this.page = page;
    }

    abstract public Request requestProcess();

    public String getFileData() {
        return fileData;
    }

    public String getPath() {
        return path;
    }


}
