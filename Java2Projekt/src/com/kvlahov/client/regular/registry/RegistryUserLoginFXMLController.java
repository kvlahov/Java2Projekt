/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.client.regular.registry;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.kvlahov.model.RegistryUser;
import com.kvlahov.model.interfaces.IControllerWithModel;
import com.kvlahov.services.AccountService;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;

/**
 * FXML Controller class
 *
 * @author evlakre
 */
public class RegistryUserLoginFXMLController implements Initializable, IControllerWithModel<RegistryUser> {

    private RegistryUser model;
    private StringProperty userPin;

    @FXML
    private JFXPasswordField userPinField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userPin = new SimpleStringProperty("");
        userPinField.textProperty().bindBidirectional(userPin);
        Platform.runLater(() -> userPinField.requestFocus());

        TextFormatter<Long> formatter = new TextFormatter<>(
                new LongStringConverter(),
                null,
                c -> Pattern.matches("\\d*", c.getText()) ? c : null);
        
        userPinField.setTextFormatter(formatter);

    }

    @Override
    public void setModel(RegistryUser model) {
        this.model = model;
    }

    @Override
    public RegistryUser getModel() {
        return this.model;
    }

    public Scene getScene() {
        return userPinField.getScene();
    }

    @FXML
    public void handleNumberClick(ActionEvent event) {
        if (event.getSource() instanceof JFXButton) {
            JFXButton button = (JFXButton) event.getSource();
            String btnValue = button.getText();

            if (!btnValue.equals("Clear")) {
                userPin.setValue(userPin.get().concat(btnValue));
            } else {
                userPin.setValue(null);
            }
        }
    }

    @FXML
    public void handleLoginClick(ActionEvent event) {
        if(userPin.getValueSafe().isEmpty()) {
            return;
        }
        AccountService accountService = new AccountService();

        Optional<RegistryUser> registryUser = accountService.loginRegistryUser(Long.parseLong(userPin.get()));
        if(registryUser.isPresent()) {
            model = registryUser.get();
            closeWIndow();
        } else {
            model = null;
            userPin.setValue(null);
            userPinField.requestFocus();
        }
    }

    private void closeWIndow() {
        Stage stage = (Stage)userPinField.getScene().getWindow();
        stage.close();
    }

}
