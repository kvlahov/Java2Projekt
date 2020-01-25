/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.client.regular.components;

import com.kvlahov.utilities.UIHelper;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author evlakre
 */
public class HomeFXMLController implements Initializable {
    @FXML
    private Node root;
            
    @FXML
    public void handleReservationsClick(ActionEvent event) {
//        ((Pane)root.getParent()).getChildren().clear();
        UIHelper.switchComponent(root, this.getClass(), "ConnectingToServerFXML.fxml");
    }
    
    @FXML
    public void handleRegistryClick(ActionEvent event) {

    }

    @FXML
    public void handleInventoryClick(ActionEvent event) {

    }

    @FXML
    public void handleReceiptsClick(ActionEvent event) {

    }
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
