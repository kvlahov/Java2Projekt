/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.utilities;

import java.util.function.BiConsumer;
import java.util.function.Predicate;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;

/**
 *
 * @author evlakre
 */
public class MyCellEditHandler<S, T> implements EventHandler<TableColumn.CellEditEvent<S, T>> {

    private Predicate<T> validationPredicate;
    private String alertMessage;
    private BiConsumer<S, T> validationSuccessConsumer;

    public MyCellEditHandler() {
    }

    public MyCellEditHandler(Predicate<T> predicate, String alertMessage, BiConsumer<S, T> consumer) {
        this.validationPredicate = predicate;
        this.alertMessage = alertMessage;
        this.validationSuccessConsumer = consumer;
    }

    public Predicate<T> getValidationPredicate() {
        return validationPredicate;
    }

    public void setValidationPredicate(Predicate<T> validationPredicate) {
        this.validationPredicate = validationPredicate;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public BiConsumer<S, T> getValidationSuccessConsumer() {
        return validationSuccessConsumer;
    }

    public void setValidationSuccessConsumer(BiConsumer<S, T> validationSuccessConsumer) {
        this.validationSuccessConsumer = validationSuccessConsumer;
    }

    @Override
    public void handle(TableColumn.CellEditEvent<S, T> event) {
        T newValue = event.getNewValue();

        if (newValue == null || newValue.equals(event.getOldValue())) {
            event.getTableView().refresh();
        }

        if (validationPredicate.test(newValue)) {
            S rowValue = event.getRowValue();
            validationSuccessConsumer.accept(rowValue, newValue);
        } else {
            UIHelper.showInfoAlert(alertMessage);
            event.getTableView().refresh();
        }

    }

}
