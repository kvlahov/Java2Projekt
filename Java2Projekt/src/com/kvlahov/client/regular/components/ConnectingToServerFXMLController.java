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
import javafx.application.Platform;
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
    private String connectingMessage;

    @FXML
    public void handleTryAgainClick(ActionEvent event) {
        lblMessage.setText(connectingMessage);
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
            try {
                if (!service.connectToServer()) {
                    handleFailedServerConnection();
                } else {
                    handleSuccessfulServerConnection();
                }
            } catch (IOException | IllegalArgumentException ex) {
                handleFailedServerConnection();
                ex.printStackTrace();
            }
        });
        thread.start();
    }

    private void handleFailedServerConnection() {
        Platform.runLater(() -> {
            lblMessage.setText("Connecting to the server failed!");
            btnTryAgain.setVisible(true);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        service = new ReservationService();
        connectingMessage = lblMessage.getText();
        tryConnectToServer();
    }

    private void handleSuccessfulServerConnection() {
        Platform.runLater(() -> {
            lblMessage.setText("Successful connection!");
        });
    }

}
