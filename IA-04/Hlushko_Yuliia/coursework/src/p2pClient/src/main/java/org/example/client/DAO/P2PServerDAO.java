package org.example.client.DAO;

import org.apache.log4j.Logger;
import org.example.client.config.Configuration;
import org.example.client.config.ConfigurationManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class P2PServerDAO implements DAO {
    private static final Logger LOGGER = Logger.getLogger(P2PServerDAO.class);
    private Configuration configuration = ConfigurationManager.getInstance().getCurrentConfiguration();
    private String url;
    private String username;
    private String password;
    private Connection connection;
    private PreparedStatement myStmt;
    private ResultSet myRs;

    public P2PServerDAO() {
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get(configuration.getDatabaseProperties()))) {
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.url = props.getProperty("url");
        this.username = props.getProperty("username");
        this.password = props.getProperty("password");
    }

    @Override
    public String getData(String urlToPage) {
System.out.println(urlToPage);
        String pageData = null;
        String query
                = "Select * from PAGES_DATA where URL = ?";
        try {
            this.connection = DriverManager.getConnection(
                    this.url, this.username, this.password);
            this.myStmt = connection.prepareStatement(query);
            this.myStmt.setString(1, urlToPage);
            this.myRs = myStmt.executeQuery();
            if (myRs.next()) {
                pageData = myRs.getString("TEMPLATE_DATA");
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pageData;
    }


    @Override
    public void addStatistic(String urlToPage) {
        String query
                = "UPDATE PAGES_DATA SET number_of_views = number_of_views+1 WHERE URL = ?";

        try {
            this.connection = DriverManager.getConnection(
                    this.url, this.username, this.password);
            this.myStmt = this.connection.prepareStatement(query);
            this.myStmt.setString(1, urlToPage);
            myStmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getStatistic(String urlToPage) {
        int count = 0;
        String query
                = "Select * from PAGES_DATA where URL = ?";

        try {
            this.connection = DriverManager.getConnection(
                    this.url, this.username, this.password);
            this.myStmt = connection.prepareStatement(query);
            this.myStmt.setString(1, urlToPage);
            this.myRs = myStmt.executeQuery();
            if (myRs.next()) {
                count = Integer.parseInt(myRs.getString("number_of_views"));
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
}
