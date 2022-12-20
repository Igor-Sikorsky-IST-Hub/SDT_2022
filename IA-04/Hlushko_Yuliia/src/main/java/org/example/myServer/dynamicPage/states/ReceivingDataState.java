package org.example.myServer.dynamicPage.states;


import org.example.myServer.DAO.DAO;
import org.example.myServer.DAO.MyDAO;
import org.example.myServer.dynamicPage.Page;



public class ReceivingDataState extends DynamicPageState{


    private String pageData;
    public ReceivingDataState(Page page) {
        super(page);

    }

    @Override
    public void doAction() {
        DAO dao = new MyDAO();
        super.page.setPageData(dao.getData(super.page.getPathToFile()));
       super.page.changeState(new TranslatingState(super.page));

    }


}
