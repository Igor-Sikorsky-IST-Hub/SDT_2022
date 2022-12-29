package org.example.client.pages.pageService;

import org.example.client.pages.GeneralInterfaceForTranslatedPages;

import java.io.File;

/*
До кожного файлу .html та .jsp "прив'язаний" один екземпляр класу Page ,тут зберігають дані, які
скоріше за все не будуть змінюватись часто,а тому їх можна обробити один раз,а не при кожному запиті на певну сторінку.
 */

public class Page {
    private String translatedPage; //Перекладена для компіляції сторінка, те,що буде записуватись в class.java
    private String pageData;//Прочитані дані з jsp сторінки
    private String pathToFile;//шлях до файлу з jsp сторінкою , з якої потрібно читати дані
    private String name;//назва файлів .class та .java класу
    private File sourceFile;
    private GeneralInterfaceForTranslatedPages classInstance; //клас з єдиним методом ,який потрібно викликати ,щоб в перекладену,
                                                               // відкомпільовану та завантажену сторінку додати параметри,а точніше
                                                                //замінити всі ключі параметрів на їхні значення



    public Page() {

    }

    public GeneralInterfaceForTranslatedPages getClassInstance() {
        return classInstance;
    }
    public void setClassInstance(GeneralInterfaceForTranslatedPages classInstance) {
        this.classInstance = classInstance;
    }

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



    @Override
    public String toString() {
        return "Page{" +
                "translatedPage='" + translatedPage + '\'' +
                ", pageData='" + pageData + '\'' +
                ", pathToFile='" + pathToFile + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
