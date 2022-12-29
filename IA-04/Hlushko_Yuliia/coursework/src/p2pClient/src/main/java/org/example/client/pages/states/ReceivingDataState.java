package org.example.client.pages.states;


import org.example.client.DAO.DAO;
import org.example.client.DAO.P2PServerDAO;
import org.example.client.config.ConfigurationManager;
import org.example.client.pages.pageService.RequestPage;


public class ReceivingDataState implements DynamicPageState {

    String directory = ConfigurationManager.getInstance().getCurrentConfiguration().getDirectory();
    public ReceivingDataState() {

    }

    @Override
    public void doAction(RequestPage page) {

        DAO dao = new P2PServerDAO();
        String url = page.getStartPage().getPathToFile().substring(directory.length()-1);
        while (url.charAt(0)=='/'&&url.charAt(1)=='/') url=url.substring(1);
        if (page.getStartPage().getPathToFile().split("\\.")[1].equals("jsp")) {
            try {
                page.getStartPage().setPageData(dao.getData(url));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            page.changeState(new TranslatingToSendingReadyPageState());
        } else {
            try {
                page.setReadyForSendingData(dao.getData(url));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            page.changeState(new FinishState());
        }
    }

}

