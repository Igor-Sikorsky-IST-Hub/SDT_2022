package org.example.myServer.dynamicPage;

import org.example.myServer.dynamicPage.states.DynamicPageState;

import java.io.File;
import java.util.Map;

public class Page {
    private String translatedPage;
    private String pageData;
    private Map<String,String> parameters;
    private DynamicPageState state;
    private String pathToFile;
    private String name;
    private File sourceFile;

    public File getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public String getPageData() {
        return pageData;
    }

    public String getTranslatedPage() {
        return translatedPage;
    }

    public void setTranslatedPage(String translatedPage) {
        this.translatedPage = translatedPage;
    }

    public void setPageData(String pageData) {
        this.pageData = pageData;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }



    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }


    public DynamicPageState getState() {
        return state;
    }

    public void setState(DynamicPageState state) {
        this.state = state;
    }


    public Page() {
    }
    public void changeState(DynamicPageState state){
        this.state=state;
        state.doAction();
    }

    @Override
    public String toString() {
        return "Page{" +
                "translatedPage='" + translatedPage + '\'' +
                ", pageData='" + pageData + '\'' +
                ", parameters=" + parameters +
                ", state=" + state +
                ", pathToFile='" + pathToFile + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
