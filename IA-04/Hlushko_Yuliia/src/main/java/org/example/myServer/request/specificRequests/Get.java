package org.example.myServer.request.specificRequests;
import org.example.myServer.core.HTTPConnection;
import org.example.myServer.pages.states.ServiceState;
import org.example.myServer.request.Request;
import org.example.myServer.statistics.Statistic;

import java.nio.charset.StandardCharsets;


public class Get extends Request {

    private Statistic statistic = new Statistic();
    public Get(String requestData) {
        super(requestData);
    }

    public Get(Request request) {
        super(request);
        super.setMethod("GET");

    }

    @Override
    public Request requestProcess(HTTPConnection httpConnection) {

        statistic.addData(super.getPath());

        super.page.getStartPage().getConnectionListener().ready(httpConnection);

        super.page.addNewParameter("numberOfVisits",statistic.getData(super.getPath()).toString());
        super.page.changeState(new ServiceState());
        super.fileBytes = super.page.getReadyForSendingData().getBytes(StandardCharsets.UTF_8);


        return this;

    }

}
