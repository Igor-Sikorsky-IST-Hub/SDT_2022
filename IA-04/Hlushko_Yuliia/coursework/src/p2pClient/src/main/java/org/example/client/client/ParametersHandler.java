package org.example.client.client;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class ParametersHandler implements  TakerParametersFromForm {

    private  JTextField param1 ;
    private  JTextField param2 ;
    private  JTextField param3 ;
    private  JTextField value1 ;
    private  JTextField value2 ;
    private  JTextField value3 ;
    private Map<String, String> param ;

    public ParametersHandler(JTextField param1, JTextField param2, JTextField param3,
                             JTextField value1, JTextField value2, JTextField value3) {
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        param = new HashMap<>();
    }

    @Override
    public Map handle() {


        if (!param1.getText().isEmpty() & !param1.getText().isBlank()
                & value1.getText() != null & !value1.getText().isEmpty()) param.put(param1.getText(), value1.getText());
        if (!param2.getText().isEmpty() & !param2.getText().isBlank()
                & value2.getText() != null & !value2.getText().isEmpty()) param.put(param2.getText(), value2.getText());
        if (!param3.getText().isEmpty() & !param3.getText().isBlank()
                & value3.getText() != null & !value3.getText().isEmpty()) param.put(param3.getText(), value3.getText());
        return param;
    }
}
