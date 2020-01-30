/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.utilities;

import com.kvlahov.client.Main;
import com.kvlahov.client.login.LoginFXMLDocumentController;
import com.kvlahov.client.regular.receipts.ReceiptPreviewFXMLController;
import com.kvlahov.client.regular.receipts.ReceiptsFXMLController;
import com.kvlahov.model.Receipt;
import com.kvlahov.model.interfaces.IControllerWithModel;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
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

    public static void switchComponent(Pane root, Class resourceRootClass, String componentFxml) {
        try {
            root.getChildren().clear();
            Node node = (Node)FXMLLoader.load(resourceRootClass.getResource(componentFxml));
            fitToParent(node);
            root.getChildren().add(node);
        } catch (IOException ex) {
            Logger.getLogger(UIHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static<TModel> void switchComponentSetModel(Pane root, Class resourceRootClass, String componentFxml, TModel model) {
        try {
            root.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(resourceRootClass.getResource(componentFxml));
            Node node = (Node)loader.load();
            IControllerWithModel controller = (IControllerWithModel) loader.getController();
            controller.setModel(model);
            
            fitToParent(node);
            
            root.getChildren().add(node);
        } catch (IOException ex) {
            Logger.getLogger(UIHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public static void switchComponentSetModel(Pane root, Class resourceRootClass, String componentFxml, Receipt model) {
//        try {
//            root.getChildren().clear();
//            FXMLLoader loader = new FXMLLoader(resourceRootClass.getResource(componentFxml));
//            Node node = (Node)loader.load();
//            ReceiptPreviewFXMLController controller = (ReceiptPreviewFXMLController) loader.getController();
//            controller.setModel(model);
//            
//            fitToParent(node);
//            
//            root.getChildren().add(node);
//        } catch (IOException ex) {
//            Logger.getLogger(UIHelper.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    private static void fitToParent(Node node) {
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
    }
}
