package org.example.myServer.request.specificRequests;


import org.example.myServer.DAO.MyDAO;
import org.example.myServer.config.Configuration;
import org.example.myServer.config.ConfigurationManager;


import org.example.myServer.core.HTTPConnection;
import org.example.myServer.dynamicPage.states.ServiceState;
import org.example.myServer.request.Request;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;


public class Get extends Request {


    private Configuration configuration = ConfigurationManager.getInstance().getCurrentConfiguration();
    private String directory = configuration.getDirectory();


    public Get(String requestData) {
        super(requestData);
    }

    public Get(Request request) {
        super(request);
        super.setMethod("GET");

    }

    @Override
    public Request requestProcess(HTTPConnection httpConnection) {

        //визначається шлях, де лежить файл
        super.page.setPathToFile(directory + super.getPath());
        //процес обробки динамічних/статичних сторінок
        super.page.changeState(new ServiceState(page));

        //шлях, де лежить перекомпільована з динамічної сторінки статична з потрібними даними та параметрами
        Path filePath = Path.of(directory, super.getPath().split("\\.")[0] + ".html");

        //дані зі статичної сторінки,які будуть повернені в відповіді
        super.fileBytes = new MyDAO().getData(filePath.toString()).getBytes(StandardCharsets.UTF_8);

        return this;

    }

}
