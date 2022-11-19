package org.example.myServer.statistics;

import java.io.Serializable;

public class Page extends Component implements Serializable {



    public Page(String name) {
        super(name);
    }

    public Page() {
    }

    @Override
    public Integer getStatistics(String url) {
        return super.getStatistics(url);
    }


    public void addStatistics(String url) {
        super.addStatistics(url);

    }

    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        super.setName(name);
    }

    public int getCount() {
        return super.getCount();
    }

    public void setCount(int count) {
        super.setCount( count);
    }
}
