package org.example.myServer.pages.states;

import org.apache.log4j.Logger;
import org.example.myServer.pages.pageService.RequestPage;

public class FinishState implements DynamicPageState{

    private static final Logger LOGGER = Logger.getLogger(DynamicPageState.class);

    public FinishState() {

    }

    @Override
    public void doAction(RequestPage page) {
     LOGGER.info("Page is ready!!!!");

    }
}
