package org.example.myServer.dynamicPage.states;

import org.example.myServer.dynamicPage.Page;

public abstract class DynamicPageState {

    protected Page page;

    public DynamicPageState(Page page) {
        this.page = page;
    }

    public abstract void doAction();


}
