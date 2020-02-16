/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.client.regular.reservations;

import com.jfoenix.controls.JFXSpinner;
import com.kvlahov.services.ReservationService;
import com.kvlahov.utilities.UIHelper;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

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
    
    @FXML
    private JFXSpinner spinner;

    @FXML
    private Pane root;
    
    private ReservationService service;
    private String connectingMessage;

    @FXML
    public void handleTryAgainClick(ActionEvent event) {
        lblMessage.setText(connectingMessage);
        spinner.setVisible(true);
        btnTryAgain.setVisible(false);

        tryConnectToServer();

    }

    private void tryConnectToServer() {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ConnectingToServerFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (!service.connectToServer()) {
                handleFailedServerConnection();
            } else {
                handleSuccessfulServerConnection();
            }

        });
        thread.start();
    }

    private void handleFailedServerConnection() {
        Platform.runLater(() -> {
            lblMessage.setText("Connecting to the server failed!");
            spinner.setVisible(false);
            btnTryAgain.setVisible(true);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        service = new ReservationService(false);
        connectingMessage = lblMessage.getText();
        tryConnectToServer();
    }

    private void handleSuccessfulServerConnection() {
        Platform.runLater(() -> {
            lblMessage.setText("Successful connection!");
            spinner.setVisible(false);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(ConnectingToServerFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            UIHelper.switchComponentSetModel((Pane)root.getParent(), getClass(), "ReservationFXML.fxml", service);
        });
    }

}
