/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.client.regular.components;

import com.kvlahov.services.ReservationService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author evlakre
 */
public class ConnectingToServerFXMLController implements Initializable {
    
    @FXML
    private Label lblMessage;
    
    @FXML
    private Button btnTryAgain;
    
    private ReservationService service;
    
    @FXML
    public void handleTryAgainClick(ActionEvent event) {
        btnTryAgain.setVisible(false);
        
        try {
            if(!service.connectToServer()) {
                handleFailedServerConnection();
            }
        } catch (IOException ex) {
            handleFailedServerConnection();
        }
        
    }

    private void handleFailedServerConnection() {
        lblMessage.setText("Connecting to the server failed!");
        btnTryAgain.setVisible(true);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        service = new ReservationService();
    }    
    
}
