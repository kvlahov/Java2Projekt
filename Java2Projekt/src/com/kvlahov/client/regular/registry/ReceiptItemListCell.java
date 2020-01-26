/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.client.regular.registry;

import com.kvlahov.model.ReceiptItem;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

/**
 *
 * @author evlakre
 */
public class ReceiptItemListCell extends ListCell<ReceiptItem>{
    @FXML
    private HBox container;
    @FXML
    private Label productName; 
    @FXML
    private Label amount; 
    @FXML
    private Label price; 
    @FXML
    private Label discount;
    @FXML
    private Label total; 

    public ReceiptItemListCell() {
        loadFXML();
    }

    
    @Override
    protected void updateItem(ReceiptItem item, boolean empty) {
        super.updateItem(item, empty);
        
        if(empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            productName.setText(item.getProduct().getName());
            price.setText(String.valueOf(item.getProduct().getPrice()));
            amount.setText(String.valueOf(item.getAmount()));
            discount.setText(String.valueOf(item.getDiscount()));
            
            total.setText(String.valueOf(item.getTotalPrice()));
            
            setText(null);
            setGraphic(container);
        }
    }

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReceiptItemListCellFXML.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ReceiptItemListCell.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
}
