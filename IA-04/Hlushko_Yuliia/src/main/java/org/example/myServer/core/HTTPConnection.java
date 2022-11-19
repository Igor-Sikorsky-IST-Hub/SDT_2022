package org.example.myServer.core;


import org.apache.log4j.Logger;
import org.example.myServer.request.Request;
import org.example.myServer.request.RequestFactoryImpl;
import org.example.myServer.response.ResponseBuilderImpl;

import java.io.*;
import java.net.Socket;
import java.util.Map;


public class HTTPConnection {
    private static final Logger LOGGER = Logger.getLogger(HTTPConnection.class);
    private Socket socket;
    private Thread thread;
    private final InputStream in;
    private OutputStream out;
    private Request request;


    public HTTPConnection(Socket socket) throws IOException {
        this.socket = socket;

        in = socket.getInputStream(); //вхідний поток даних з сокету
        out = socket.getOutputStream();//вихідний поток даних з сокету
        socket.setKeepAlive(true);

        thread = new Thread(new Runnable() { //кожне з'єднання в своєму потоці виконання
            @Override
            public void run() {
                while (!thread.isInterrupted()) { //поки потік не перерваний( поки є з'єднання з потоком)
                    String data = reader(in);    //перехоплюємо те,що було надіслано із клієнта
                    if (data != null && !data.isEmpty() && !data.isBlank() && !thread.isInterrupted()) {

                        request = new RequestFactoryImpl(data) // створюємо новий реквест із вхідних даних
                                .initialize();
                        getData(request.getBodyParameters());//надсилаєм дані на свій peer-клієнт

                    }
                }
                request.getPage()
                        .getStartPage()
                        .getConnectionListener()
                        .disconnect(HTTPConnection.this);//якщо з'єднання перервалось,робиться disconnect

            }
        });
        thread.start();

    }


     /*getData - це метод,який показує,як буде клієнтом прийняті дані .Тобто
     якщо якесь з'єднання розіслало дані,то кожне з'єднання,яке їх отримує,має якось обробити*/

    public void getData(Map<String, String> data) {
        LOGGER.info("Connection " + HTTPConnection.this.toString() + " get a data " + data.toString());
        if (request != null) {
            request.getPage().setParameters(data);
            request = request.requestProcess(HTTPConnection.this);
            if (request.getFileBytes() != null & request.getFileBytes().length > 0) generatedSuccessfulResponse();
            else generatedPageNotFoundResponse();
        } else
            generatedServerProblemResponse();
    }

    private void generatedSuccessfulResponse() {
        try {
            new ResponseBuilderImpl()
                    .setOutputStream(out)
                    .setStatusText("OK!")
                    .setStatusCode(200)
                    .setFileBytes(request.getFileBytes())
                    .setContentLength(request.getFileBytes().length)
                    .build().send();
        } catch (IOException e) {
            request.getPage().getStartPage().getConnectionListener().disconnect(HTTPConnection.this);
        }
    }

    private void generatedPageNotFoundResponse() {
        request.getPage().getStartPage().getConnectionListener().disconnect(HTTPConnection.this);
        try {
            new ResponseBuilderImpl()
                    .setOutputStream(out)
                    .setStatusText("Page not found!")
                    .setStatusCode(404)
                    .setContentLength(0)
                    .build().send();
        } catch (IOException e) {
            request.getPage().getStartPage().getConnectionListener().disconnect(HTTPConnection.this);
        }
    }

    private void generatedServerProblemResponse() {

        LOGGER.info("Request is null on " + HTTPConnection.this.toString());
        request.getPage().getStartPage().getConnectionListener().disconnect(HTTPConnection.this);
        try {
            new ResponseBuilderImpl()
                    .setOutputStream(out)
                    .setStatusText("Server problems")
                    .setStatusCode(501)
                    .setContentLength(0)
                    .build().send();
        } catch (IOException e) {
            request.getPage().getStartPage().getConnectionListener().disconnect(HTTPConnection.this);
        }

    }

    public void disconnect() {
        thread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            request.getPage().getStartPage()
                    .getConnectionListener()
                    .exception(HTTPConnection.this, e);
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

    public Request getRequest() {
        return request;
    }

    public Thread getThread() {
        return thread;
    }


}


