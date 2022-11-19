package org.example.myServer.statistics;

public class CompositePageFactoryImpl implements CompositePageFactory {
    public CompositePageFactoryImpl() {
    }

    @Override
    public Component getElement(String url) {
        if (url.split("/").length > 1)
            return new Directory(url.split("/")[0]);
        else {
            if (url.contains(".")) return new Page(url);
            else return new Directory(url);
        }

    }
}
