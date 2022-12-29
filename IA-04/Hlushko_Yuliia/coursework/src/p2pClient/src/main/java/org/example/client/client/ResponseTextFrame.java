package org.example.client.client;

import javax.swing.*;

public class ResponseTextFrame implements  SimpleTextFrame {

    private static final int WIGHT = 800;
    private static final int HEIGHT = 400;
     private final JTextArea responseText = new JTextArea();


    public ResponseTextFrame() {

    }

    @Override
    public synchronized void printText(String message) {
        printHTMLResponse(message);
    }

    //вивід відповіді в окреме вікно
    public synchronized void printHTMLResponse(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                JFrame frame = new JFrame();

                frame.setSize(WIGHT, HEIGHT);
                frame.setAlwaysOnTop(true);
                frame.setVisible(true);
                responseText.setEditable(false);
                responseText.setLineWrap(true);
                frame.add(responseText);
                responseText.setText(message);
            }
        });
    }
}
