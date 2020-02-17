/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.client.regular.receipts;

import com.jfoenix.controls.JFXButton;
import com.kvlahov.model.Receipt;
import com.kvlahov.services.ReceiptsService;
import com.kvlahov.utilities.UIHelper;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author evlakre
 */
public class ReceiptsFXMLController implements Initializable {

    @FXML
    private TableView<Receipt> tableView;
    @FXML
    private TableColumn<Receipt, LocalDate> dateColumn;
    @FXML
    private TableColumn<Receipt, Double> totalColumn;
    @FXML
    private TableColumn<Receipt, String> createdByColumn;
    @FXML
    private AnchorPane receiptPane;
    @FXML
    private JFXButton btnCancelReceipt;

    private ObservableList<Receipt> receipts;
    private ReceiptsService receiptsService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        receipts = FXCollections.observableArrayList();
        receiptsService = new ReceiptsService();
        setupColumns();

        fillReceipts();
        tableView.setItems(receipts);
        tableView.getSelectionModel().selectedIndexProperty()
                .addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        btnCancelReceipt.setVisible(true);
                        Receipt r = tableView.getSelectionModel().getSelectedItem();
                        UIHelper.switchComponentSetModel(receiptPane, ReceiptsFXMLController.class, "ReceiptPreviewFXML.fxml", r);
                    } else {
                        btnCancelReceipt.setVisible(false);
                        receiptPane.getChildren().clear();
                    }
                });
    }

    private void setupColumns() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
        totalColumn.setCellValueFactory(data
                -> new SimpleDoubleProperty(
                        receiptsService.getReceiptTotal(data.getValue())
                ).asObject()
        );

        createdByColumn.setCellValueFactory(data
                -> new SimpleStringProperty(
                        data.getValue().getCreatedByUser() == null ? "No user" : data.getValue().getCreatedByUser().getUsername()
                ));
    }

    private void fillReceipts() {
        receipts.addAll(receiptsService.getReceipts());
    }
    
    @FXML
    public void handeCancelReceiptClick(ActionEvent e) {
        Receipt selectedReceipt = tableView.getSelectionModel().getSelectedItem();
        if(selectedReceipt != null) {
            new Thread(() -> {
                Receipt canceledReceipt = receiptsService.cancelReceipt(selectedReceipt);
                receipts.add(canceledReceipt);
            }).start();
        }
    }

}
