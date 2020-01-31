/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.utilities;

import java.util.function.Consumer;
import java.util.function.Function;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 *
 * @author evlakre
 */
public class ActionButtonTableCell<S> extends TableCell<S, Button> {

    private final Button actionButton;

    public ActionButtonTableCell(String label, Consumer<S> consumer) {
        this.getStyleClass().add("action-button-table-cell");

        this.actionButton = new Button(label);
        this.actionButton.setOnAction(e -> consumer.accept(getCurrentItem()));
        this.actionButton.setMaxWidth(Double.MAX_VALUE);
    }

    public S getCurrentItem() {
        return (S) getTableView().getItems().get(getIndex());
    }
    
    public void setButtonStyleClass(String styleClass) {
        this.actionButton.getStyleClass().add(styleClass);
    }

    public static <S> Callback<TableColumn<S, Button>, TableCell<S, Button>> forTableColumn(String label, Consumer<S> consumer) {
        return param -> new ActionButtonTableCell<>(label, consumer);
    }

    @Override
    public void updateItem(Button item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(actionButton);
        }
    }
}