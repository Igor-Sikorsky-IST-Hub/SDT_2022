package org.example.client.client;

import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;

public class ValidationDialog implements ClientValidationMediator {

    private  JTextField receiverPeerAddress ;
    private  JTextField url ;
    private  JTextField method ;

    private  Label labelMethodError ;
    private  Label labelUrlError ;
    private  Label labelReceiverPeerError;

    public ValidationDialog(JTextField receiverPeerAddress, JTextField url, JTextField method,
                            Label labelMethodError, Label labelUrlError, Label labelReceiverPeerError) {
        this.receiverPeerAddress = receiverPeerAddress;
        this.url = url;
        this.method = method;
        this.labelMethodError = labelMethodError;
        this.labelUrlError = labelUrlError;
        this.labelReceiverPeerError = labelReceiverPeerError;
    }

    @Override
    public boolean notify( String event) {

        this.labelMethodError.setText("");
        this.labelUrlError.setText("");
        this.labelReceiverPeerError.setText("");

        if (this.method.getText().isEmpty() | this.method.getText().isBlank()) {
            this.labelMethodError.setText("Empty field");
        } else if (!this.method.getText().toUpperCase().equals("GET") & !this.method.getText().toUpperCase().equals("POST")) {
            this.labelMethodError.setText("Incorrect method");}
        else this.labelMethodError.setText("");

        if (this.url.getText().isEmpty() | this.url.getText().isBlank()) {
            this.labelUrlError.setText("Empty field");
        } else if (!this.url.getText().contains(".")) {
            this.labelUrlError.setText("Wrong url format");}else
            this.labelUrlError.setText("");

         if (this.receiverPeerAddress.getText().isEmpty() | this.receiverPeerAddress.getText().isBlank()) {
            this.labelReceiverPeerError.setText("Empty field");
        } else if (!StringUtils.isNumeric(this.receiverPeerAddress.getText())) {
            this.labelReceiverPeerError.setText("NaN!!!");}else
             this.labelReceiverPeerError.setText("");

         if(this.labelMethodError.getText().equals("")&& this.labelUrlError.getText().equals("")&&
                 this.labelReceiverPeerError.getText().equals("")) return true;
        return false;
    }
}
