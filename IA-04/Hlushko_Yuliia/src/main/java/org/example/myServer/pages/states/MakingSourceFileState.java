package org.example.myServer.pages.states;

import org.example.myServer.pages.pageService.RequestPage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class MakingSourceFileState implements DynamicPageState{
    private File root = new File("src/main/resources/compiledJSPPages"); // add to config
    private String fileName;
   private String path;
    private String newClassName;
    private String urlToJSPPage;
    public MakingSourceFileState() {


    }

    @Override
    public void doAction(RequestPage page) {

        this.urlToJSPPage=page.getStartPage().getPathToFile();
        this.fileName = urlToJSPPage.substring(urlToJSPPage.lastIndexOf('/') + 1, urlToJSPPage.indexOf("."));
        this.path = urlToJSPPage.substring(0, urlToJSPPage.lastIndexOf('/'));
        this.newClassName = path.replaceAll("/", "") + fileName.toUpperCase();
       page.getStartPage().setName(newClassName);

        File sourceFile = new File(root, "dynamicPage/" +this.newClassName + ".java");

        sourceFile.getParentFile().mkdirs();
        try {
            Files.write(sourceFile.toPath(), page.getStartPage().getTranslatedPage().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        page.getStartPage().setSourceFile(sourceFile);
       page.changeState( new CompilingState());


    }
}