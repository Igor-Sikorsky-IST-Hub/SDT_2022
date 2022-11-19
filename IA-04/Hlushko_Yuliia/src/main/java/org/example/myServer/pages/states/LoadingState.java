package org.example.myServer.pages.states;

import org.example.myServer.pages.GeneralInterfaceForTranslatedPages;
import org.example.myServer.pages.pageService.RequestPage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class LoadingState implements DynamicPageState {

    private File root = new File("src/main/resources/compiledJSPPages");  // add to config


    public LoadingState() {

    }

    @Override
    public void doAction(RequestPage page) {

        try {
            URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[]{root.toURI().toURL()});
            Class<?> cls = Class.forName("dynamicPage." + page.getStartPage().getName(),
                    true, urlClassLoader);

            Class<? extends GeneralInterfaceForTranslatedPages> instbs =
                    cls.asSubclass(org.example.myServer.pages.GeneralInterfaceForTranslatedPages.class);
            GeneralInterfaceForTranslatedPages newInstance = instbs.newInstance();
            page.getStartPage().setClassInstance(newInstance);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        page.changeState(new InsertParametersState());
    }
}
