/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.client.regular.receipts;

import com.kvlahov.model.Receipt;
import com.kvlahov.model.ReceiptItem;
import com.kvlahov.model.interfaces.IControllerWithModel;
import com.kvlahov.services.ReceiptsService;
import com.kvlahov.utilities.DateUtils;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author evlakre
 */
public class ReceiptPreviewFXMLController implements Initializable, IControllerWithModel<Receipt> {

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
    private Label dateLabel;
    @FXML
    private Label userLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label totalLabel;
    
    
    private ReceiptsService receiptService;
    private ObjectProperty<Receipt> receiptModel;
    private ObservableList<ReceiptItem> receiptItems;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        receiptService = new ReceiptsService();
        receiptModel = new SimpleObjectProperty<>();
        receiptItems = FXCollections.observableArrayList();
        
        tableView.setItems(receiptItems);
        
        receiptModel.addListener((observable, oldValue, newValue) -> {
            receiptItems.addAll(receiptService.getReceiptItemsForReceipt(newValue));
            
            dateLabel.setText(DateUtils.getFormattedDate(newValue.getDateCreated()));
            userLabel.setText(newValue.getUserName());
            idLabel.setText(String.valueOf(newValue.getId()));
            totalLabel.setText(String.valueOf(receiptService.getReceiptTotal(newValue)));
            
        });
        initTableColumns();
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
    @Override
    public void setModel(Receipt receipt) {
        receiptModel.set(receipt);
    }

    @Override
    public Receipt getModel() {
        return receiptModel.get();
    }
}
