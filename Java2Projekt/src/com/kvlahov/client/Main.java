/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.client;

import com.kvlahov.dal.repositories.IRepository;
import com.kvlahov.dal.repositories.IUserRepository;
import com.kvlahov.dal.repositories.implementations.CategoryRepository;
import com.kvlahov.dal.repositories.implementations.ProductRepository;
import com.kvlahov.dal.repositories.implementations.UserRepository;
import com.kvlahov.model.Category;
import com.kvlahov.model.Product;
import com.kvlahov.model.User;
import com.kvlahov.model.enums.UserRoleEnum;
import com.kvlahov.services.AccountService;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author evlakre
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login/LoginFXMLDocument.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }

}
