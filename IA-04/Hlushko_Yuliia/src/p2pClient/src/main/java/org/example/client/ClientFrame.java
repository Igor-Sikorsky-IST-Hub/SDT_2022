package org.example.client;

import org.apache.commons.lang3.StringUtils;

import org.apache.log4j.Logger;
import org.example.client.config.ConfigurationManager;
import org.example.client.request.RequestFactoryImpl;
import org.example.client.response.Response;
import org.example.client.response.ResponseFactory;
import org.example.network.ConnectionListener;
import org.example.network.HTTPConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;

import java.util.HashMap;
import java.util.Map;

public class ClientFrame extends JFrame implements ActionListener, ConnectionListener {

    private static final int WIGHT = 800;
    private static final int HEIGHT = 400;
    private final JTextArea log = new JTextArea();
    private final Label thisPeerAddress = new Label();
    private final JTextField receiverPeerAddress = new JTextField(20);
    private final JTextField url = new JTextField(20);
    private final JTextField method = new JTextField(20);
    private final JTextField param1 = new JTextField(20);
    private final JTextField param2 = new JTextField(20);
    private final JTextField param3 = new JTextField(20);
    private final JTextField value1 = new JTextField(20);
    private final JTextField value2 = new JTextField(20);
    private final JTextField value3 = new JTextField(20);

    private final Label labelParam1 = new Label("parameter 1");
    private final Label labelParam2 = new Label("parameter 2");
    private final Label labelParam3 = new Label("parameter 3");
    private final Label labelValue1 = new Label("value 1");
    private final Label labelValue2 = new Label("value 1");
    private final Label labelValue3 = new Label("value 1");
    private final Label labelMethod = new Label("Method");
    private final Label labelUrl = new Label("Url");
    private final Label labelReceiverPeer = new Label("Receiver");
    private final Label informationLabel = new Label();

    private final Label labelMethodError = new Label();
    private final Label labelUrlError = new Label();
    private final Label labelReceiverPeerError = new Label();
    private final Button button = new Button("Ask page");
    private static final Logger LOGGER = Logger.getLogger(ClientFrame.class);
    private HTTPConnection connection;

    private Map<String, String> bodyParameters;
    private Map<String, String> headerParameters;

    private String ip;
    private int port;

    private int p2pSearcherServerPort = ConfigurationManager.getInstance().getCurrentConfiguration().getP2pSearcherServerPort();

    //запуск 2 тредів : з одного можна підключитись до якогось іншого peer, а інший відсліжує порт даного peer'а
    public ClientFrame(String ip, int port) {
        this.ip = ip;
        this.port = port;

        new Thread(new Runnable() {
            @Override
            public void run() {
                startListenPort(port);
                clientFrameThread(ip, port);

            }
        }).start();

    }

    //запуст треда, що прослуховує порт
    private void startListenPort(int port) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (ServerSocket serverSocket = new ServerSocket(port)) {
                    while (true) {
                        try {
                            new HTTPConnection(serverSocket.accept(), ClientFrame.this);
                        } catch (IOException e) {
                            LOGGER.info("Connection problems!");
                        }
                    }
                } catch (IOException e) {
                    LOGGER.info("socket problems!");
                }
            }
        }).start();
    }

    // метод потрібен для зв'язку із search server , викликається,щоб повідомити йому, що тред уже не активний
// і його потрібно видалити зі списку активних тредів
    private void sendToP2PSearcherServerMessageAboutDeleting() {
        headerParameters = new HashMap<>();
        headerParameters.put("port", String.valueOf(this.port));
        headerParameters.put("action", "delete");
        String mail = httpMaker("GET", "ddddd", bodyParameters, headerParameters);
        try {
            connection.disconnect();
            this.connection = new HTTPConnection(ip, p2pSearcherServerPort, this);
        } catch (IOException ex) {
            LOGGER.error("Server not run");
        }
        connection.sendString(mail);
    }

    //багато рядочків графічного дизайну
    public void clientFrameThread(String ip, int port) {

        try {
            this.connection = new HTTPConnection(ip, port, ClientFrame.this);
        } catch (IOException e) {
            LOGGER.error("Server not run");
        }
//------------------------------------------------------------------------------------------
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIGHT, HEIGHT);
        setAlwaysOnTop(true);
        setVisible(true);


        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);
        GridBagConstraints constraints = new GridBagConstraints();
        thisPeerAddress.setText("Port " + port);
//----------------------------------------------------------
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(labelMethod, constraints);


        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 0;
        add(method, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 3;
        constraints.gridy = 0;
        add(labelUrl, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 4;
        constraints.gridy = 0;
        add(url, constraints);
        //----------------------------------------------------------


        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(labelMethodError, constraints);


        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 4;
        constraints.gridy = 1;
        add(labelUrlError, constraints);


//====================================================================


        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(labelReceiverPeer, constraints);


        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 2;
        add(receiverPeerAddress, constraints);
        //====================================================================


        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 3;
        add(labelReceiverPeerError, constraints);

//========================================================================================
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 4;
        add(labelParam1, constraints);


        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 4;
        add(param1, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 3;
        constraints.gridy = 4;
        add(labelValue1, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 4;
        constraints.gridy = 4;
        add(value1, constraints);

        //========================================================================================
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 5;
        add(labelParam2, constraints);


        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 5;
        add(param2, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 3;
        constraints.gridy = 5;
        add(labelValue2, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 4;
        constraints.gridy = 5;
        add(value2, constraints);

        //========================================================================================
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 6;
        add(labelParam3, constraints);


        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 6;
        add(param3, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 3;
        constraints.gridy = 6;
        add(labelValue3, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 4;
        constraints.gridy = 6;
        add(value3, constraints);
//-------------------------------------------------------------------------------------
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 4;
        constraints.gridy = 9;
        add(informationLabel, constraints);

//========================================================================================
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 2;
        constraints.gridy = 8;
        add(thisPeerAddress, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 4;
        constraints.gridy = 8;
        add(button, constraints);
        button.addActionListener(this);
        //-------------------------------------
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                sendToP2PSearcherServerMessageAboutDeleting();
                e.getWindow().dispose();
            }
        });


    }

    //дії при натисканні кнопки

    @Override
    public void actionPerformed(ActionEvent e) {

        if (isValidForm()) {

            headerParameters = new HashMap<>();
            bodyParameters = new HashMap<>();
            String mail;
            parametersHandler();
            int connectionPort;

//якщо в рядочку для адреси peer було введено Server , то формується спеціальний запит на сервер
            //інакше запит на вказаного peer
            if (!receiverPeerAddress.getText().toUpperCase().equals("SERVER")) {
                mail = httpMaker(method.getText().toUpperCase(), url.getText(), bodyParameters, headerParameters);
                connectionPort = Integer.parseInt(receiverPeerAddress.getText());
            } else {
                headerParameters = new HashMap<>();
                headerParameters.put("port", String.valueOf(this.port));
                headerParameters.put("action", "add");
                mail = httpMaker("GET", url.getText(), bodyParameters, headerParameters);
                connectionPort = ConfigurationManager.getInstance().getCurrentConfiguration().getP2pSearcherServerPort();
            }

            try {
                this.connection.disconnect();
                this.connection = new HTTPConnection(ip, connectionPort, this);
            } catch (IOException ex) {
                LOGGER.error("Server not run");
            }
            connection.sendString(mail);
        }
    }

    //невелика валідація форм
    private boolean isValidForm() {
        labelMethodError.setText("");
        labelUrlError.setText("");
        labelReceiverPeerError.setText("");

        if (method.getText().isEmpty() | method.getText().isBlank()) {
            labelMethodError.setText("Empty field");
            return false;
        } else if (!method.getText().toUpperCase().equals("GET") & !method.getText().toUpperCase().equals("POST")) {
            labelMethodError.setText("Incorrect method");
            return false;
        } else if (url.getText().isEmpty() | url.getText().isBlank()) {
            labelUrlError.setText("Empty field");
            return false;
        } else if (!url.getText().contains(".")) {
            labelUrlError.setText("Wrong url format");
            return false;
        } else if (receiverPeerAddress.getText().isEmpty() | receiverPeerAddress.getText().isBlank()) {
            labelReceiverPeerError.setText("Empty field");
            return false;
        } else if (!StringUtils.isNumeric(receiverPeerAddress.getText()) & (!receiverPeerAddress.getText().toUpperCase().equals("SERVER"))) {
            labelReceiverPeerError.setText("NaN!!!");
            return false;
        } else return true;

    }


    private String httpMaker(String method, String url, Map<String, String> bodyParam, Map<String, String> headerParam) {


        if (!headerParam.isEmpty()) {
            url = url + "?";
            for (String item : headerParam.keySet()) {
                if (headerParam.get(item) != null && !item.isEmpty()) {
                    if (!(url.lastIndexOf("?") == url.length() - 1)) url = url + "&";
                    url = url + item + "=" + headerParam.get(item);
                }
            }

        }
        String httpRequest = method + " " + url + " HTTP/1.1\n";
        httpRequest = httpRequest + "Content-Type: multipart/form-data;boundary=\"boundary\"; charset=utf-8\n";
        httpRequest = httpRequest + "Content-Length:" + String.valueOf(byteCount(bodyParam)) + "\n";

if(bodyParam!=null){
        for (String item : bodyParam.keySet()) {
            httpRequest = httpRequest + "Content-Disposition: form-data; name=\"" + item + "\"\n";
            httpRequest = httpRequest + bodyParam.get(item) + "\n";

        }}
        return httpRequest;
    }

    private long byteCount( Map<String, String> parameters) {
        long count = 0;
        if(parameters==null) return 0;
        for (String item : parameters.keySet()) {

            count += item.getBytes().length
                    + parameters.get(item).getBytes().length;

        }
        return count;
    }

    private void parametersHandler() {
        Map<String, String> param = new HashMap<>();


        if (!param1.getText().isEmpty() & !param1.getText().isBlank()
                & value1.getText() != null & !value1.getText().isEmpty()) param.put(param1.getText(), value1.getText());
        if (!param2.getText().isEmpty() & !param2.getText().isBlank()
                & value2.getText() != null & !value2.getText().isEmpty()) param.put(param2.getText(), value2.getText());
        if (!param3.getText().isEmpty() & !param3.getText().isBlank()
                & value3.getText() != null & !value3.getText().isEmpty()) param.put(param3.getText(), value3.getText());

        if (method.getText().toUpperCase().equals("GET")) headerParameters = param;
        if (method.getText().toUpperCase().equals("POST")) bodyParameters = param;

    }

    //вивід стрічки в графічне представлення
    private synchronized void printInformationMessage(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                informationLabel.setText(message);
            }
        });
    }

    //вивід відповіді в окреме вікно
    private synchronized void printHTMLResponse(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                JFrame frame = new JFrame();

                frame.setSize(WIGHT, HEIGHT);
                frame.setAlwaysOnTop(true);
                frame.setVisible(true);
                log.setEditable(false);
                log.setLineWrap(true);
                frame.add(log);
                log.setText(message);
            }
        });
    }


    @Override
    public synchronized void sendMessage(HTTPConnection httpConnection, String message) {
        printHTMLResponse(message);
    }

    @Override
    public synchronized void getRequest(HTTPConnection httpConnection, String message) {

        Response response = new ResponseFactory().getResponse(new RequestFactoryImpl(message).initialize().requestProcess());
        httpConnection.sendString(response.toString());
    }


    @Override
    public synchronized void exception(HTTPConnection httpConnection, Exception exception) {
        printInformationMessage("Servers problem!");
    }

    @Override
    public synchronized void ready(HTTPConnection httpConnection) {
        printInformationMessage("Connection is ready");
    }

    @Override
    public synchronized void disconnect(HTTPConnection httpConnection) {

        printInformationMessage("Connection is disconnected");

    }


}
