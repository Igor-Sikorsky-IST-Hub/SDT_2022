package org.example.client.statistics;


import org.example.client.DAO.P2PServerDAO;

public class Statistic {

    public Statistic() {

    }
    public synchronized void addData(String url) {
        new P2PServerDAO().addStatistic(url);
    }

    public synchronized Integer getData(String url) {
        return new P2PServerDAO().getStatistic(url);

    }

}
