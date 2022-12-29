package org.example.client.DAO;

public interface DAO {
    String getData(String urlToPage) throws ClassNotFoundException;
    void addStatistic (String urlToPage);
    int getStatistic (String urlToPage);

}
