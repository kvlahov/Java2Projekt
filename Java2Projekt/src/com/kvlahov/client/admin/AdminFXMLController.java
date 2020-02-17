/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.client.admin;

import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.kvlahov.client.Main;
import com.kvlahov.utilities.UIHelper;
import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author evlakre
 */
public class AdminFXMLController implements Initializable {

    @FXML
    private Pane rootPane;

    @FXML
    public void handleUser(ActionEvent event) {
        UIHelper.switchComponentSlide(rootPane, Main.class, "admin/UsersFXML.fxml");
    }
    
    @FXML
    public void handleProductClick(ActionEvent event) {
        UIHelper.switchComponentSlide(rootPane, Main.class, "admin/ProductsFXML.fxml");
    }
    
    @FXML
    public void handleReceiptsClick(ActionEvent event) {
        UIHelper.switchComponentSlide(rootPane, Main.class, "regular/receipts/ReceiptsFXML.fxml");
    }
    
    @FXML
    public void handleLogoutClick(ActionEvent event) {
        UIHelper.switchScene(rootPane, "login/LoginFXMLDocument.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        

//        validator.setMessage("This field is required");
//        validator.setIcon(GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.WARNING).size("1em").style("error").build());
//        field.getValidators().add(validator);
//        field.focusedProperty().addListener((observable, oldValue, newValue) -> {
//            if(!newValue){
//                field.validate();
//            }
//        });
    }

}
