/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.utilities;

import com.kvlahov.client.Main;
import com.kvlahov.client.login.LoginFXMLDocumentController;
import com.kvlahov.model.interfaces.IControllerWithModel;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.LongStringConverter;

/**
 *
 * @author evlakre
 */
public class UIHelper {

    private static final int SLIDE_ANIMATION_DURATION = 750;

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
        root.getChildren().clear();
        Node node = loadNode(resourceRootClass, componentFxml);
        root.getChildren().add(node);

    }

    private static Node loadNode(Class resourceRootClass, String componentFxml) {
        try {
            Node node = (Node) FXMLLoader.load(resourceRootClass.getResource(componentFxml));
            fitToParent(node);
            return node;
        } catch (IOException ex) {
            Logger.getLogger(UIHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void switchComponentSlide(Pane root, Class resourceRootClass, String componentFxml) {
        Node node = loadNode(resourceRootClass, componentFxml);

        node.translateXProperty().set(-root.getWidth());
        root.getChildren().add(node);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(node.translateXProperty(), 0, Interpolator.EASE_OUT);
        KeyFrame kf = new KeyFrame(Duration.millis(SLIDE_ANIMATION_DURATION), kv);

        timeline.getKeyFrames().add(kf);
        timeline.play();
        timeline.setOnFinished((event) -> {
            root.getChildren().retainAll(node);
        });
    }

    public static <TModel> void switchComponentSetModel(Pane root, Class resourceRootClass, String componentFxml, TModel model) {
        try {
            root.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(resourceRootClass.getResource(componentFxml));
            Node node = (Node) loader.load();
            IControllerWithModel controller = (IControllerWithModel) loader.getController();
            controller.setModel(model);

            fitToParent(node);

            root.getChildren().add(node);
        } catch (IOException ex) {
            Logger.getLogger(UIHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void fitToParent(Node node) {
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
    }
    
    public static TextFormatter<Long> getLongTextFormatter() {
        return new TextFormatter<>(
                new LongStringConverter(),
                null,
                c -> Pattern.matches("\\d*", c.getText()) ? c : null);
    }
}
