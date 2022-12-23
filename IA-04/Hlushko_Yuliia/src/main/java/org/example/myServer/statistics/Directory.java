package org.example.myServer.statistics;

import java.io.Serializable;
import java.util.HashMap;


public class Directory implements Serializable {

    private String name;
    private int count;

    private HashMap<String, Directory> files;


    public Directory(String name, HashMap<String, Directory> files) {
        this.name = name;
        this.count = 1;
        this.files = files;
    }

    public Directory(String name) {
        this.name = name;
        this.count = 1;

    }

    public Directory() {}

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public int getCount() {
        return count;
    }


    public void setCount(int count) {
        this.count = count;
    }

    public HashMap<String, Directory> getFiles() {
        return files;
    }

    public int incrementCount() {
        count++;
        return count;
    }

    public void setFiles(HashMap<String, Directory> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "Directory{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", files=" + files +
                '}';
    }

}
