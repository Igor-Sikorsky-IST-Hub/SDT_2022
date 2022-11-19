package org.example.myServer.pages.states;


import org.example.myServer.DAO.DAO;
import org.example.myServer.DAO.MyDAO;
import org.example.myServer.pages.pageService.RequestPage;


public class ReceivingDataState implements DynamicPageState {

    public ReceivingDataState() {

    }

    @Override
    public void doAction(RequestPage page) {

        DAO dao = new MyDAO();

        if (page.getStartPage().getPathToFile().split("\\.")[1].equals("jsp")) {
            page.getStartPage().setPageData(dao.getData(page.getStartPage().getPathToFile()));
            page.changeState(new TranslatingToSendingReadyPageState());
        } else {
            page.setReadyForSendingData(dao.getData(page.getStartPage().getPathToFile()));
            page.changeState(new FinishState());
        }
    }

}

