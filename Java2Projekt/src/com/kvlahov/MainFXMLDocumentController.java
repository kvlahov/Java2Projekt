/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author evlakre
 */
public class MainFXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private TextField textField; 
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println(textField.getText());
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
