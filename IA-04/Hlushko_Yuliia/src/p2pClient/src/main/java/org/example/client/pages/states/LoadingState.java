package org.example.client.pages.states;

import org.example.client.config.ConfigurationManager;
import org.example.client.pages.GeneralInterfaceForTranslatedPages;
import org.example.client.pages.pageService.RequestPage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class LoadingState implements DynamicPageState {

    private File root;


    public LoadingState() {

    }

    @Override
    public void doAction(RequestPage page) {
        this.root = new File(ConfigurationManager.getInstance().getCurrentConfiguration().getPathToSourceFileSavingDirectory());
        try {
            URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[]{root.toURI().toURL()});
            Class<?> cls = Class.forName("dynamicPage." + page.getStartPage().getName(),
                    true, urlClassLoader);

            Class<? extends GeneralInterfaceForTranslatedPages> instbs =
                    cls.asSubclass(GeneralInterfaceForTranslatedPages.class);
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
