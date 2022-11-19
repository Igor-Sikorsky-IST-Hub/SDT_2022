package org.example.myServer.pages.states;


import org.example.myServer.pages.pageService.RequestPage;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;


public class CompilingState implements DynamicPageState {

    public CompilingState() {

    }

    @Override
    public void doAction(RequestPage page) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, page.getStartPage().getSourceFile().getPath());
        page.changeState(new LoadingState());
    }
}
