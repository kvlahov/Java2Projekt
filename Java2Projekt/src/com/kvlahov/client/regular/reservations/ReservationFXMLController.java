/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.client.regular.reservations;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTimePicker;
import com.kvlahov.model.ReservationInfo;
import com.kvlahov.model.enums.TableStateEnum;
import com.kvlahov.model.interfaces.IControllerWithModel;
import com.kvlahov.services.ReservationService;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author evlakre
 */
public class ReservationFXMLController implements Initializable, IControllerWithModel<ReservationService> {

    @FXML
    private Pane pane;

    @FXML
    private Label lblConnectedClients;

    @FXML
    private JFXButton btnReserve;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private TextField tfName;
    @FXML
    private JFXTimePicker tpTime;

    @FXML
    private Pane reservationForm;

    private ReservationService reservationService;
    private ObservableList<ReservationTableComponent> tables;
    private ReservationTableComponent selectedTable;
    private ReservationInfo reservation;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tables = FXCollections.observableArrayList();

        for (int i = 1; i <= 10; i++) {
            ReservationTableComponent table = initTableComponent(i);

            tables.add(table);
        }
        pane.getChildren().addAll(tables);
        reservation = new ReservationInfo();

    }

    private ReservationTableComponent initTableComponent(int i) {
        ReservationTableComponent table = new ReservationTableComponent(i);
        table.getTableState().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case FREE:
                    reservationService.freeTable(table.getTableId());
                    break;
                case RESERVED:
                    reservationService.reserveTable(reservation);
                    break;
                case UNAVAILABLE:
                    reservationService.lockTable(table.getTableId());
                    break;
            }

        });

        table.setOnMouseClickEvent(e -> {
            if (selectedTable != null && selectedTable.getTableState().get() != TableStateEnum.RESERVED) {
                selectedTable.setTableState(TableStateEnum.FREE);
            }
            reservationService.lockTable(table.getTableId());

            reservationForm.setVisible(true);
            selectedTable = table;
            table.setTableState(TableStateEnum.UNAVAILABLE);
        });
        table.setPreserveRatio(true);
        table.setFitWidth(150);
        table.setFitHeight(150);
        return table;
    }

    @FXML
    public void handleReserveClick(ActionEvent e) {
        if (selectedTable == null) {
            return;
        }
        
        reservation = new ReservationInfo();
        reservation.setTableId(selectedTable.getTableId());
        reservation.setName(tfName.getText());
        LocalDateTime dt = LocalDateTime.of(LocalDate.now(), tpTime.getValue());
        reservation.setReservationDateTime(dt);
        
        
        selectedTable.setTableState(TableStateEnum.RESERVED);
        reservationForm.setVisible(false);
    }

    @FXML
    public void handleCancelClick(ActionEvent e) {
        selectedTable.setTableState(TableStateEnum.FREE);

        selectedTable = null;
        reservationForm.setVisible(false);
    }

    @Override
    public void setModel(ReservationService model) {
        this.reservationService = model;

        reservationService.setTableFreedConsumer(processTableEvent(TableStateEnum.FREE));
        reservationService.setTableReservedConsumer(processTableEvent(TableStateEnum.RESERVED));
        reservationService.setTableLockedConsumer(processTableEvent(TableStateEnum.UNAVAILABLE));
        reservationService.setTableUnlockedConsumer(processTableEvent(TableStateEnum.FREE));

        reservationService.setNoClientsChangedConsumer(no -> {
            Platform.runLater(() -> lblConnectedClients.setText("" + no));
        });

        try {
            reservationService.registerClient();

        } catch (RemoteException ex) {
            Logger.getLogger(ReservationFXMLController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ReservationService getModel() {
        return this.reservationService;
    }

    private Consumer<Integer> processTableEvent(TableStateEnum state) {
        return tableId -> {
            Platform.runLater(() -> {
                Optional<ReservationTableComponent> targetTable = tables.stream()
                        .filter(t -> t.getTableId() == tableId)
                        .findFirst();

                if (targetTable.isPresent()) {
                    targetTable.get().setTableState(state);
                }
            });
        };
    }

}
