package org.example.myServer.dynamicPage.states;


import org.example.myServer.dynamicPage.Page;

public class ServiceState extends DynamicPageState {

    public ServiceState(Page page) {
        super(page);

    }

    @Override
    public void doAction() {
        //якщо сторінка динамічна
        if (page.getPathToFile().split("\\.")[1].equals("jsp")) {
            //якщо це перше відвідування сторінки
            if (page.getPageData() == null) {
                super.page.changeState(new ReceivingDataState(super.page));
            } else {
                //якщо це оновлення сторінки
                super.page.changeState(new TranslatingState(super.page));
            }
        } else {
            //якщо сторінка статична
            super.page.changeState(new FinishState(super.page));
        }
    }
}

