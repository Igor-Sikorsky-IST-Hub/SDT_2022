package org.example.myServer.dynamicPage.states;

import org.example.myServer.dynamicPage.GeneralInterfaceForTranslatedPages;
import org.example.myServer.dynamicPage.Page;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class LoadingState extends DynamicPageState {

    private File root = new File("src/main/resources/compiledJSPPages"); //


    public LoadingState(Page page) {
        super(page);

    }

    @Override
    public void doAction() {

        try {
            URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[]{root.toURI().toURL()});
            Class<?> cls = Class.forName("dynamicPage." + super.page.getName(), true, urlClassLoader);

            Class<? extends GeneralInterfaceForTranslatedPages> instbs = cls.asSubclass(org.example.myServer.dynamicPage.GeneralInterfaceForTranslatedPages.class);
            GeneralInterfaceForTranslatedPages newInstance = instbs.newInstance();
            newInstance.toDo();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        super.page.changeState(new FinishState(page));
    }
}
