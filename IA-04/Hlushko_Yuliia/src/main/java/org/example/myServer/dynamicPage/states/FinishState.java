package org.example.myServer.dynamicPage.states;

import org.apache.log4j.Logger;
import org.example.myServer.config.Configuration;
import org.example.myServer.config.ConfigurationManager;
import org.example.myServer.dynamicPage.Page;

public class FinishState extends DynamicPageState{

    private static final Logger LOGGER = Logger.getLogger(DynamicPageState.class);

    public FinishState(Page page) {
        super(page);
    }

    @Override
    public void doAction() {
     LOGGER.info("Page is ready!!!!");

    }
}
