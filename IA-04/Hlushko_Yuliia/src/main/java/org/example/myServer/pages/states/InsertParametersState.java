package org.example.myServer.pages.states;


import org.example.myServer.pages.pageService.RequestPage;

import java.util.HashMap;

public class InsertParametersState implements DynamicPageState{

    public InsertParametersState() {

    }

    @Override
    public void doAction(RequestPage page) {
        String data =page.getStartPage().getClassInstance().toDo((HashMap<String, String>) page.getParameters());
        page.setReadyForSendingData(data);
        page.changeState(new FinishState());
    }
}
