/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.utilities;

import com.kvlahov.client.Main;
import com.kvlahov.client.login.LoginFXMLDocumentController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author evlakre
 */
public class UIHelper {

    public static void switchScene(Node node, String fxml) {
        try {
            Stage stage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(Main.class.getResource(fxml));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.setResizable(false);
            stage.centerOnScreen();
        } catch (IOException ex) {
            Logger.getLogger(LoginFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void switchComponent(Node root, Class resourceRootClass ,String componentFxml) {
        try {
            Pane parent = (Pane) root.getParent();
            parent.getChildren().clear();
            parent.getChildren().add(FXMLLoader.load(resourceRootClass.getResource("ConnectingToServerFXML.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(UIHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
