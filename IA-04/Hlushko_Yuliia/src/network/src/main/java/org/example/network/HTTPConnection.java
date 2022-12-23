package org.example.network;


import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class HTTPConnection {

    private Socket socket;
    private Thread thread;
    private InputStream in;
    private OutputStream out;


    private ConnectionListener connectionListener;
    private String data;


    public HTTPConnection(String ip, int port, ConnectionListener connectionListener) throws IOException {
        this(new Socket(ip, port), connectionListener);
    }

    public HTTPConnection(Socket socket, ConnectionListener connectionListener) throws IOException {

        this.socket = socket;
        this.connectionListener = connectionListener;
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                connectionListener.ready(HTTPConnection.this);

                while (!thread.isInterrupted()) {
                    data = reader(in);
                    if (data != null && !data.isBlank() && !data.isEmpty() && !thread.isInterrupted()) {

                        if (data.split("\s")[0].equals("GET") | data.split("\s")[0].equals("POST"))
                            connectionListener.getRequest(HTTPConnection.this, data);
                        else
                            connectionListener.sendMessage(HTTPConnection.this, data);

                    }
                }

                connectionListener.disconnect(HTTPConnection.this);

            }

        });
        thread.start();

    }


    public synchronized void sendString(String message) {
        try {
            out.write(message.getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (IOException e) {
            connectionListener.exception(HTTPConnection.this, e);
            disconnect();
        }
    }

    public void disconnect() {
        thread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
        }

    }

    @Override
    public String toString() {
        return "Connection address : " + socket.getInetAddress() + " port: " + socket.getPort();
    }

    private String reader(InputStream inputStream) {//для читання даних із вхідного потоку
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


}


