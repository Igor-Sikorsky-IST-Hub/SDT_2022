package org.example.myServer.dynamicPage.states;

import org.example.myServer.dynamicPage.Page;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class MakingSourceFileState extends DynamicPageState{
    private File root = new File("src/main/resources/compiledJSPPages"); // add to config
    private String fileName;
   private String path;
    private String newClassName;
    private String urlToJSPPage;
    public MakingSourceFileState(Page page) {
        super(page);
        this.urlToJSPPage=page.getPathToFile();
        this.fileName = urlToJSPPage.substring(urlToJSPPage.lastIndexOf('/') + 1, urlToJSPPage.indexOf("."));
        this.path = urlToJSPPage.substring(0, urlToJSPPage.lastIndexOf('/'));
        this.newClassName = path.replaceAll("/", "") + fileName.toUpperCase();
        super.page.setName(newClassName);
    }

    @Override
    public void doAction() {
        File sourceFile = new File(root, "dynamicPage/" +this.newClassName + ".java");

        sourceFile.getParentFile().mkdirs();
        try {
            Files.write(sourceFile.toPath(), super.page.getTranslatedPage().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        page.setSourceFile(sourceFile);
        super.page.changeState( new CompilingState(page));


    }
}