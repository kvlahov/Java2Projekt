/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.client;

import com.kvlahov.dal.repositories.implementations.CategoryRepository;
import com.kvlahov.dal.repositories.implementations.ProductRepository;
import com.kvlahov.dal.repositories.implementations.RegistryUserRepository;
import com.kvlahov.dal.repositories.implementations.UserRepository;
import com.kvlahov.model.Category;
import com.kvlahov.model.Product;
import com.kvlahov.model.RegistryUser;
import com.kvlahov.model.ReservationInfo;
import com.kvlahov.model.User;
import com.kvlahov.model.enums.UserRoleEnum;
import com.kvlahov.services.XmlService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author evlakre
 */
public class Main extends Application {

    private static Stage stage;

    public static Window getStage() {
        return stage;
    }

    private static void createStartData() {
//        createCategoriesAndProducts();
//        createRegistryUsers();
//        createUsers();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Main.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("login/LoginFXMLDocument.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        createStartData();
//        testData();

        launch(args);

    }

    private static void createCategoriesAndProducts() {
        CategoryRepository categoryRepository = new CategoryRepository();
        ProductRepository productRepository = new ProductRepository();

        categoryRepository.deleteAll();
        productRepository.deleteAll();
        Category warmDrinks = new Category("Warm drinks");
        Category softDrinks = new Category("Soft drinks");
        Category liquor = new Category("Liqour");
        Category beer = new Category("Beer");

        List<Category> categories = new ArrayList<>();
        categories.add(warmDrinks);
        categories.add(softDrinks);
        categories.add(liquor);
        categories.add(beer);

        categoryRepository.addRange(categories);

        List<Product> products = new ArrayList<>();
        products.add(new Product("Espresso", 7, warmDrinks));
        products.add(new Product("Coffee with milk", 9, warmDrinks));
        products.add(new Product("Caffe latte", 10, warmDrinks));
        products.add(new Product("Cappuccino", 9, warmDrinks));
        products.add(new Product("Tea", 10, warmDrinks));

        products.add(new Product("Coca-cola", 15, softDrinks));
        products.add(new Product("Fanta", 15, softDrinks));
        products.add(new Product("Sprite", 15, softDrinks));
        products.add(new Product("Ice tea", 15, softDrinks));

        products.add(new Product("Pelinkovac", 10, liquor));
        products.add(new Product("Medica", 10, liquor));
        products.add(new Product("Travarica", 10, liquor));
        products.add(new Product("Jack Daniels", 10, liquor));
        products.add(new Product("Jameson", 10, liquor));
        products.add(new Product("Vodka", 10, liquor));
        products.add(new Product("Gin", 10, liquor));

        products.add(new Product("Ožujsko", 15, beer));
        products.add(new Product("Karlovačko", 15, beer));
        products.add(new Product("Osiječko", 15, beer));
        products.add(new Product("Zlatni Medvjed", 15, beer));
        products.add(new Product("Grička Vještica", 15, beer));
        products.add(new Product("Paulaner", 15, beer));

        productRepository.addRange(products);
    }

    private static void createRegistryUsers() {
        RegistryUserRepository registryUserRepo = new RegistryUserRepository();

        registryUserRepo.add(new RegistryUser(123, "Pero"));
        registryUserRepo.add(new RegistryUser(456, "Marko"));
        registryUserRepo.add(new RegistryUser(789, "Mirko"));
    }

    private static void createUsers() {
        UserRepository ur = new UserRepository();
        ur.deleteAll();
        ur.add(new User("user", "password"));
        ur.add(new User("admin", "password", UserRoleEnum.ADMIN));
    }

    private static void testData() {
        RegistryUserRepository registryUserRepo = new RegistryUserRepository();
        CategoryRepository categoryRepository = new CategoryRepository();
        ProductRepository productRepository = new ProductRepository();

        registryUserRepo.getAll().forEach(c -> System.out.println(c));
    }
}
