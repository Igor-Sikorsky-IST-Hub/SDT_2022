package org.example.myServer.dynamicPage.states;

import org.example.myServer.dynamicPage.Page;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;

public class CompilingState extends DynamicPageState{
    private File sourceFile;
    public CompilingState(Page page) {
        super(page);
        this.sourceFile=page.getSourceFile();
    }

    @Override
    public void doAction() {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, sourceFile.getPath());
        super.page.changeState(new LoadingState(page));
    }
}
