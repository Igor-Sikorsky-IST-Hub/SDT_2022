package org.example.client.pages.pageService;

import org.example.client.pages.states.DynamicPageState;


import java.util.Map;
/*
Екземпляри класу RequestPage прив'язані до Request , тут зберігаються дані, що будуть часто змінюватись,
ймовірно , для більшості запитів будуть унікальні
*/
public class RequestPage {
    private  String readyForSendingData;
    private Map<String, String> parameters;//Параметри, які повинні бути вставлені в певні місця <ket,value>
    private DynamicPageState state;//поточний стан перетворення сторінки з jsp до готового стану
    private Page startPage;

    public RequestPage(String path) {
        startPage=new HTTPPageService().getPage(path);

    }

    public Page getStartPage() {
        return startPage;
    }

    public void changeState(DynamicPageState state) {
        this.state = state;
        state.doAction(this);
    }

    public void addNewParameter(String key,String value){
        parameters.put(key,value);
    }
    public String getReadyForSendingData() {
        return readyForSendingData;
    }


    public void setReadyForSendingData(String readyForSendingData) {
        this.readyForSendingData = readyForSendingData;
    }


    public Map<String, String> getParameters() {
        return parameters;
    }


    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "RequestPage{" +
                "readyForSendingData='" + readyForSendingData + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
