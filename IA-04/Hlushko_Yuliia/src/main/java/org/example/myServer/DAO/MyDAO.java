package org.example.myServer.DAO;

import org.apache.log4j.Logger;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class MyDAO implements DAO {

    private static final Logger LOGGER = Logger.getLogger(MyDAO.class);

    @Override
    public String getData(String urlToJSPPage) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(urlToJSPPage);
        } catch (FileNotFoundException e) {
            LOGGER.error("file not found!!!");
            return new String("");
        }
        StringBuffer sb = new StringBuffer();
        int i;
        while (true) {
            try {
                if (!((i = fileReader.read()) != -1)) break;
            } catch (IOException e) {
                LOGGER.error("Can't read file");
                throw new RuntimeException(e);
            }
            sb.append((char) i);
        }
        return sb.toString();
    }
}
