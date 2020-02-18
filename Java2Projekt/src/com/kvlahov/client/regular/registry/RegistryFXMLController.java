/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.client.regular.registry;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.kvlahov.client.Main;
import com.kvlahov.model.Category;
import com.kvlahov.model.Receipt;
import com.kvlahov.model.ReceiptItem;
import com.kvlahov.model.RegistryUser;
import com.kvlahov.model.interfaces.IControllerWithModel;
import com.kvlahov.services.RegistryService;
import com.sun.glass.ui.Application;
import com.sun.glass.ui.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author evlakre
 */
public class RegistryFXMLController implements Initializable {

    private static final Logger LOG = Logger.getLogger(RegistryFXMLController.class.getName());

    @FXML
    private Pane root;
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
    private Label registryUserLabel;

    @FXML
    private JFXTabPane tabPaneCategories;
    @FXML
    private JFXButton btnCash;
    @FXML
    private Label total;

    private ObservableList<ReceiptItem> receiptItems;
    private RegistryService registryService;
    private RegistryUser user;

    @FXML
    public void handleRemoveClick(ActionEvent event) {
        if (receiptItems.size() <= 0) {
            return;
        }

        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            receiptItems.remove(selectedIndex);
        } else {
            receiptItems.remove(receiptItems.size() - 1);
        }
        tableView.requestFocus();
        tableView.getSelectionModel().select(receiptItems.size() - 1);
    }

    @FXML
    public void handleCashClick(ActionEvent event) {
        if(receiptItems == null || receiptItems.isEmpty()) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Printing receipt...");
        alert.setHeaderText(null);
        alert.show();
        try {
            Thread.sleep(1500);
            alert.setResult(ButtonType.OK);
        } catch (InterruptedException ex) {
            Logger.getLogger(RegistryFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        alert.close();

        saveReceipt();

        receiptItems.clear();
    }

    @FXML
    public void handleSwitchUserClick(ActionEvent event) {
        handleUserLogin();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handleUserLogin();
        registryService = new RegistryService();
        receiptItems = FXCollections.observableArrayList();

        initTableColumns();

        tableView.setItems(receiptItems);

        receiptItems.addListener((ListChangeListener.Change<? extends ReceiptItem> change) -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved()) {
                    double sum = tableView.getItems()
                            .stream()
                            .mapToDouble(i -> i.getTotalPrice())
                            .sum();
                    total.setText(String.valueOf(sum));

                }
                btnCash.setDisable(receiptItems.isEmpty());
            }
        });

        createCategories();
    }

    private void initTableColumns() {
        productNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduct().getName()));
        priceColumn.setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getProduct().getPrice()).asObject());
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        discountNameColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        priceColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
        amountColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
        discountNameColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
        totalColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
    }

    private void createCategories() {
        for (Category category : registryService.getCategories()) {
            FlowPane container = new FlowPane(Orientation.HORIZONTAL, 10, 10);
            registryService.getProductsForCategory(category)
                    .forEach(p -> {
                        JFXButton btn = new JFXButton(p.getName());
                        btn.setStyle("-fx-background-color: #F7AD35");
                        btn.setTextAlignment(TextAlignment.CENTER);
                        btn.setPrefSize(100, 100);
                        btn.wrapTextProperty().setValue(true);
                        btn.setOnAction(e -> {
//                            tableView.getItems().add(new ReceiptItem(p, 1, 0));
                            receiptItems.add(new ReceiptItem(p, 1, 0));
                        });
                        container.getChildren().add(btn);
                    });
            ScrollPane scrollPane = new ScrollPane(container);
            scrollPane.setFitToWidth(true);
            scrollPane.setPadding(new Insets(5, 5, 5, 5));
            Tab categoryTab = new Tab(category.getName(), scrollPane);

            categoryTab.setStyle("-fx-background-color: #C67B00");
            categoryTab.setStyle("-fx-border-color: black");
            categoryTab.setStyle("-fx-border-width: 1");
            tabPaneCategories.getTabs().add(categoryTab);
        }
    }

    private void saveReceipt() {
        if (receiptItems.isEmpty()) {
            return;
        }

        Map<ReceiptItem, Integer> collect = receiptItems.stream().collect(Collectors.groupingBy(i -> i, Collectors.summingInt(i -> i.getAmount())));

        collect.forEach((k, v) -> k.setAmount(v));

        List<ReceiptItem> items = collect.keySet().stream().map(k -> k).collect(Collectors.toList());
        Receipt receipt = new Receipt(LocalDate.now(), user);
        registryService.insertReceipt(receipt, items);
    }

    private IControllerWithModel showDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("RegistryUserLoginFXML.fxml"));
            Parent pane = loader.load();
            Scene scene = new Scene(pane);
            Stage dialog = new Stage();
            RegistryUserLoginFXMLController controller = (RegistryUserLoginFXMLController) loader.getController();

            dialog.initStyle(StageStyle.UTILITY);
//            scene.setFill(Color.TRANSPARENT);
            dialog.setScene(scene);
            dialog.initOwner(Main.getStage());
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.showAndWait();
            return controller;
        } catch (IOException ex) {
            Logger.getLogger(RegistryFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    private void handleUserLogin() {
        IControllerWithModel<RegistryUser> controller = showDialog();
        if (controller.getModel() == null) {
            Robot r = Application.GetApplication().createRobot();
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_H);

            r.keyRelease(KeyEvent.VK_CONTROL);
            r.keyRelease(KeyEvent.VK_H);
        } else {
            user = controller.getModel();
            registryUserLabel.setText(user.getUsername());
        }
    }

}
