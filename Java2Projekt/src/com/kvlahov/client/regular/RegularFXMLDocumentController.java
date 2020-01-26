/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.client.regular;

import com.kvlahov.utilities.UIHelper;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author evlakre
 */
public class RegularFXMLDocumentController implements Initializable {
    
    @FXML
    private Pane container;

    @FXML
    public void handleLogoutClick(ActionEvent event) {
        UIHelper.switchScene((Node) event.getSource(), "login/LoginFXMLDocument.fxml");
    }
    
    @FXML 
    public void handleHomeClick(ActionEvent event) {
        UIHelper.switchComponent(container, this.getClass(), "HomeFXML.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UIHelper.switchComponent(container, this.getClass(), "HomeFXML.fxml");
    }

}
