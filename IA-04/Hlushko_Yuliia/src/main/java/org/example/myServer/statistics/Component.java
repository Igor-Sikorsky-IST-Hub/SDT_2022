package org.example.myServer.statistics;

public class Component {
    private String name;
    private int count;

    public Component(String name) {
        this.name = name;
        this.count=1;
    }

    public Component() {
    }

    @Override
    public String toString() {
        return "Component{" +
                "name='" + name + '\'' +
                ", count=" + count +
                '}';
    }

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
    public Integer getStatistics(String url) {
        return this.count;
    }


    public void addStatistics(String url) {
        count ++;

    }
}

