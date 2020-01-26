/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.client.regular.registry;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTabPane;
import com.kvlahov.model.Category;
import com.kvlahov.model.ReceiptItem;
import com.kvlahov.services.RegistryService;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author evlakre
 */
public class RegistryFXMLController implements Initializable {

    @FXML
    private TableView<ReceiptItem> tableView;
    @FXML
    private TableColumn<ReceiptItem, String> productNameColumn;
    @FXML
    private TableColumn<ReceiptItem, Double> priceColumn;
    @FXML
    private TableColumn<ReceiptItem, Integer> amountColumn;
    @FXML
    private TableColumn<ReceiptItem, Double> discountNameColumn;
    @FXML
    private TableColumn<ReceiptItem, Double> totalColumn;
    
    @FXML
    private JFXTabPane tabPaneCategories;

    private RegistryService registryService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        registryService = new RegistryService();
//        List<ReceiptItem> receiptItems = new ArrayList<>();
//        Random r = new Random();
//        registryService.getProducts().stream().limit(5).forEach(p -> {
//            receiptItems.add(new ReceiptItem(p, r.nextInt(5) + 1, 0));
//        });
        
        productNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduct().getName()));
        priceColumn.setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getProduct().getPrice()).asObject());
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        discountNameColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        
//        tableView.setItems(FXCollections.observableArrayList(receiptItems));
        
        createCategories();
    }

    private void createCategories() {
        for (Category category : registryService.getCategories()) {
//            HBox container = new HBox();
            FlowPane container = new FlowPane(Orientation.HORIZONTAL, 10, 10);
            registryService.getProductsForCategory(category)
                    .forEach(p -> {
                        JFXButton btn = new JFXButton(p.getName());
                        btn.setStyle("-fx-background-color: #F7AD35");
                        btn.setPrefSize(100, 100);
                        btn.wrapTextProperty().setValue(true);
                        btn.setOnAction(e -> {
                            tableView.getItems().add(new ReceiptItem(p, 1, 0));
                        });
                        container.getChildren().add(btn);
                    });
            
            Tab categoryTab = new Tab(category.getName(), container);
            categoryTab.setStyle("-fx-background-color: #C67B00");
            categoryTab.setStyle("-fx-border-color: black");
            categoryTab.setStyle("-fx-border-width: 1");
            tabPaneCategories.getTabs().add(categoryTab);
        }
    }

}
