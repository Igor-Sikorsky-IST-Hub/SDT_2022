package org.example.client.request.specificRequests;

import org.example.client.pages.states.ServiceState;
import org.example.client.request.Request;
import org.example.client.statistics.Statistic;
import org.example.client.config.ConfigurationManager;


public class Get extends Request {

    private Statistic statistic = new Statistic();

    public Get(String requestData) {
        super(requestData);
    }


    @Override
    public Request requestProcess() {
        statistic.addData(super.getPath());


        super.page.setParameters(super.headersParameters);
        super.page.addNewParameter("numberOfVisits", statistic.getData(super.getPath()).toString());
        super.page.changeState(new ServiceState());
        super.fileData = super.page.getReadyForSendingData();
        return this;

    }

}
