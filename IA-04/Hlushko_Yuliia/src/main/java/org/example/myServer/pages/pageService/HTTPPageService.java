package org.example.myServer.pages.pageService;


import org.example.myServer.config.Configuration;
import org.example.myServer.config.ConfigurationManager;

import java.util.HashMap;


public class HTTPPageService implements PageService {
    private Configuration configuration = ConfigurationManager.getInstance().getCurrentConfiguration();
    private String directory = configuration.getDirectory();
    private static HashMap<String, Page> pages = new HashMap<>();

    public HTTPPageService() {
    }

    @Override
    public synchronized Page getPage(String pathToPage) {
        return pages.containsKey(pathToPage) ? pages.get(pathToPage) : addNewPage(pathToPage);
    }

    private Page addNewPage(String pathToPage) {
        Page page=new Page();
        page.setPathToFile(directory + pathToPage);
        pages.put(pathToPage, page);
        return pages.get(pathToPage);
    }


}
