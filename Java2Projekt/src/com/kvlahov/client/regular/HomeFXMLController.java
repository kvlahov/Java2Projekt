/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.client.regular;

import com.kvlahov.client.Main;
import com.kvlahov.utilities.UIHelper;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
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
public class HomeFXMLController implements Initializable {

    private static final Logger LOG = Logger.getLogger(HomeFXMLController.class.getName());

    @FXML
    private Node root;

    @FXML
    public void handleReservationsClick(ActionEvent event) {
        UIHelper.switchComponent((Pane) root.getParent(), this.getClass(), "reservations/ConnectingToServerFXML.fxml");
    }

    @FXML
    public void handleRegistryClick(ActionEvent event) {
        UIHelper.switchComponent((Pane) root.getParent(), this.getClass(), "registry/RegistryFXML.fxml");
    }

    @FXML
    public void handleInventoryClick(ActionEvent event) {
        LOG.info("Inventory clicked");
    }

    @FXML
    public void handleReceiptsClick(ActionEvent event) {
        UIHelper.switchComponent((Pane) root.getParent(), this.getClass(), "receipts/ReceiptsFXML.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        String css = Main.class.getResource("/com/kvlahov/resources/registryStyle.css").toExternalForm();
//        root.getScene().getStylesheets().add(css);
    }

}
