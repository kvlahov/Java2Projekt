/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.client.login;

import com.jfoenix.controls.JFXButton;
import com.kvlahov.model.User;
import com.kvlahov.services.AccountService;
import com.kvlahov.utilities.UIHelper;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author lordo
 */
public class LoginFXMLDocumentController implements Initializable {

    @FXML
    private Label errorLabel;
    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private JFXButton btnLogin;

    private AccountService service;

    @FXML
    private void handleLoginClick(ActionEvent event) {
        String username = tfUsername.getText().trim();
        String password = pfPassword.getText().trim();

        //validate...
        Optional<User> user = service.loginUser(username, password);
        
        if(user.isPresent()){
            //switch to main screen
            switch(user.get().getRole()) { 
                case REGULAR:
                    UIHelper.switchScene((Node)event.getSource(), "regular/RegularFXMLDocument.fxml");
            }
            errorLabel.setVisible(false);
        } else {
            errorLabel.setVisible(true);
            tfUsername.requestFocus();
            clearForm();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        service = new AccountService();
        
        //login immediately
        tfUsername.setText("user");
        pfPassword.setText("password");
    }

    private void clearForm() {
        tfUsername.clear();
        pfPassword.clear();
    }

}
