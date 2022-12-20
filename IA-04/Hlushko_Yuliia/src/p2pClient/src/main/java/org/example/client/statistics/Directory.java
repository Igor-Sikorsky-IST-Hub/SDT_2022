package org.example.client.statistics;

import java.io.Serializable;
import java.util.HashMap;


public class Directory extends Component implements Serializable {



    private HashMap<String, Component> files = new HashMap<>();

    public Directory(String url) {
        super(url.split("/")[0]);
        if(url.split("/").length>1)
        files.put(url.split("/")[1],
                new CompositePageFactoryImpl().getElement(url.substring(url.indexOf("/") + 1)));
    }

    public Directory() {
        super();
    }
    public Directory(String name, HashMap<String,Component> files) {
       super(name);
        this.files = files;
    }

   @Override
   public Integer getStatistics(String url) {
        if(url.split("/").length>1){
        if (url.split("/").length>1&files.containsKey(url.split("/")[1])) {
            return files.get(url.split("/")[1]).getStatistics(url.substring(url.indexOf("/") + 1));
        }else return 0;
        }else return 0;
    }


    @Override
    public void addStatistics(String url) {
        super.setCount(super.getCount()+1);
        if (files.containsKey(url.split("/")[1])) {
            files.get(url.split("/")[1]).addStatistics(url.substring(url.indexOf("/") + 1));
        } else files.put(url.split("/")[1],
                new CompositePageFactoryImpl().getElement(url.substring(url.indexOf("/") + 1)));
    }


    @Override
    public String toString() {
        return "Directory{" +
                "name='" + super.getName() + '\'' +
                ", count=" + super.getCount() +
                ", files=" + files +
                '}';
    }

    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        super.setName( name);
    }

    public int getCount() {
        return super.getCount();
    }

    public void setCount(int count) {
        super.setCount(count);
    }

    public HashMap<String, Component> getFiles() {
        return files;
    }

    public void setFiles(HashMap<String, Component> files) {
        this.files = files;
    }
}
