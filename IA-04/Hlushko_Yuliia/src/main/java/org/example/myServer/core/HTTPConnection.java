package org.example.myServer.core;


import org.apache.log4j.Logger;
import org.example.myServer.request.Request;
import org.example.myServer.request.RequestFactoryImpl;
import org.example.myServer.request.specificRequests.Get;
import org.example.myServer.response.ResponseBuilderImpl;


import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;


public class HTTPConnection {
    private static final Logger LOGGER = Logger.getLogger(HTTPConnection.class);
    private Socket socket;
    private Thread thread;
    private ConnectionListener connectionListener;

    private final InputStream in;
    private OutputStream out;
    private Request request;


    public HTTPConnection(ConnectionListener connectionListener, Socket socket) throws IOException {
        this.connectionListener = connectionListener;
        this.socket = socket;

        in = socket.getInputStream();
        out = socket.getOutputStream();
        socket.setKeepAlive(true);

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                connectionListener.ready(HTTPConnection.this);

                while (!thread.isInterrupted()) {
                    String data = reader(in);
                    if (data != null && !data.isEmpty() && !data.isBlank()&&!thread.isInterrupted()) {
                        request = new RequestFactoryImpl(data)
                                .initialize();
                        getData(request.getBodyParameters());
                    }
                }connectionListener.disconnect(HTTPConnection.this);
            }
        });
        thread.start();

    }

    public void getData(Map<String,String> data) {
        LOGGER.info("Connection " + HTTPConnection.this.toString() + " get a data " + data.toString());
        if (request != null) {
            request.getPage().setParameters(data);
            request = request.requestProcess(HTTPConnection.this);
            if (request.getFileBytes() != null) {
                try {
                    new ResponseBuilderImpl()
                            .setOutputStream(out)
                            .setStatusText("OK!")
                            .setStatusCode(200)
                            .setFileBytes(request.getFileBytes())
                            .setContentLength(request.getFileBytes().length)
                            .build().send();
                } catch (IOException e) {
                    connectionListener.disconnect(HTTPConnection.this);
                }
            } else {
                connectionListener.disconnect(HTTPConnection.this);
                try {
                    new ResponseBuilderImpl()
                            .setOutputStream(out)
                            .setStatusText("Page not found!")
                            .setStatusCode(404)
                            .setContentLength(0)
                            .build().send();
                } catch (IOException e) {
                    connectionListener.disconnect(HTTPConnection.this);
                }
            }} else{
                LOGGER.info("Request is null on " + HTTPConnection.this.toString());
                connectionListener.disconnect(HTTPConnection.this);
                try {
                    new ResponseBuilderImpl()
                            .setOutputStream(out)
                            .setStatusText("Server problems")
                            .setStatusCode(501)
                            .setContentLength(0)
                            .build().send();
                } catch (IOException e) {
                    connectionListener.disconnect(HTTPConnection.this);
                }
            }


    }

    public void disconnect() {
        thread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
           connectionListener.exception(HTTPConnection.this,e);
        }

    }

    @Override
    public String toString() {
        return "Connection address : " + socket.getInetAddress() + " port: " + socket.getPort();
    }

    private String reader(InputStream inputStream) {
        String data = null;
        try {
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            data = new String(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    public ConnectionListener getConnectionListener() {
        return connectionListener;
    }

    public void setConnectionListener(ConnectionListener connectionListener) {
        this.connectionListener = connectionListener;
    }
}


