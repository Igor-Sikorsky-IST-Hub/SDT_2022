package org.example.client.client;

import org.apache.log4j.Logger;
import org.example.network.ConnectionListener;
import org.example.network.HTTPConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;

public class ClientFrame extends JFrame implements ActionListener {
    private static final int WIGHT = 800;
    private static final int HEIGHT = 400;
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
    private final Label labelValue2 = new Label("value 2");
    private final Label labelValue3 = new Label("value 3");
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
    private String ip;
    private int port;
    private ConnectionListener connectionListener;

    public ClientFrame(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.connectionListener = new ClientListener(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                new MyServerPortListener().startListen(port, connectionListener);
                clientFrameThread(ip, port);
            }
        }).start();
    }

    public void clientFrameThread(String ip, int port) {

        this.connection = tryGetConnectionWithServer(ip, port, connectionListener);
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if (new ValidationDialog(receiverPeerAddress, url, method,
                labelMethodError, labelUrlError, labelReceiverPeerError).notify("")) {
            int connectionPort = Integer.parseInt(receiverPeerAddress.getText());
            Map<String, String> params = new ParametersHandler(param1, param2, param3, value1, value2, value3).handle();
            String mail = new HTTPRequestMaker(method.getText(), url.getText(), params).make();

            this.connection.disconnect();
            this.connection = tryGetConnectionWithServer(ip, connectionPort, connectionListener);
            if(this.connection!=null)this.connection.sendString(mail);
        }

    }

    public synchronized void printInformationMessage(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                informationLabel.setText(message);
            }
        });
    }

    private HTTPConnection tryGetConnectionWithServer(String ip, int port, ConnectionListener connectionListener) {
        HTTPConnection httpConnection = null;
        try {
            httpConnection = new HTTPConnection(ip, port, connectionListener);
        } catch (IOException ex) {
            LOGGER.error("Server not run");
        }
        return httpConnection;
    }
}