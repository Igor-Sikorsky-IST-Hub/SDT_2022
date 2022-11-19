package org.example.myServer.pages.states;



import org.example.myServer.pages.pageService.RequestPage;

public class ServiceState implements DynamicPageState {

    public ServiceState() {
        super();
    }

    @Override
    public void doAction(RequestPage page) {

        //якщо сторінка динамічна
        if (page.getStartPage().getPathToFile().split("\\.")[1].equals("jsp")) {

            //якщо це перше відвідування сторінки
            if (page.getStartPage().getTranslatedPage() == null) {
                page.changeState(new ReceivingDataState());
            } else {
                //якщо це оновлення сторінки
                page.changeState(new InsertParametersState());
            }
        } else {
            if (page.getStartPage().getPageData() == null) {
                //якщо сторінка статична
                page.changeState(new ReceivingDataState());
            }else { page.changeState(new FinishState());}
        }
    }
}

