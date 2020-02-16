/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.client.regular.reservations;

import com.kvlahov.model.enums.TableStateEnum;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Cursor;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author evlakre
 */
public class ReservationTableComponent extends ImageView {

    private final Map<TableStateEnum, Image> tableStateImageMap;
    private ObjectProperty<TableStateEnum> tableState;
    private int tableId;

    public ReservationTableComponent(int tableId, TableStateEnum tableState) {
        this.tableId = tableId;
        SimpleObjectProperty<TableStateEnum> test = new SimpleObjectProperty<>(tableState);
        SimpleObjectProperty<TableStateEnum> test2 = new SimpleObjectProperty<>();
        test2.set(tableState);
        this.tableState = new SimpleObjectProperty<>(tableState);
        this.tableStateImageMap = new HashMap<>();
        init();
    }

    public ReservationTableComponent(int tableId) {
        this(tableId, TableStateEnum.FREE);
    }

    private void init() {
        initTableStateMap();

        this.setImage(tableStateImageMap.get(tableState.get()));

        getTableState().addListener((observable, oldValue, newValue) -> {
            this.setImage(null);
            this.setImage(tableStateImageMap.get(newValue));
        });
        
        Cursor cursor = Cursor.DEFAULT;

        if (getTableState().get().equals(TableStateEnum.FREE)) {
            cursor = Cursor.HAND;
            hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setBrightness(0.4);
                    colorAdjust.setContrast(1);
                    setEffect(colorAdjust);
                } else {
                    setEffect(null);
                }
            });
        }

        setCursor(cursor);        
    }

    private void initTableStateMap() {
        Stream.of(TableStateEnum.values())
                .forEach(v -> {
                    Image img = new Image(getClass().getResourceAsStream("/assets/table_" + v.name().toLowerCase() + ".png"));
                    tableStateImageMap.put(v, img);
                });

    }

    public ObjectProperty<TableStateEnum> getTableState() {
        return tableState;
    }

    public void setTableState(TableStateEnum tableState) {
        this.tableState.set(tableState);
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + (int) (this.tableId ^ (this.tableId >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReservationTableComponent other = (ReservationTableComponent) obj;
        return this.tableId == other.tableId;
    }
    
    public void setOnMouseClickEvent(Consumer<MouseEvent> mouseClickConsumer) {
        tableState.addListener((observable, oldValue, newValue) -> {
            if(newValue.equals(TableStateEnum.FREE)) {
                setOnMouseClicked(event -> {
                    mouseClickConsumer.accept(event);
                });
                this.setCursor(Cursor.HAND);
            } else {
                setOnMouseClicked(null);
                this.setCursor(Cursor.DEFAULT);
                setEffect(null);
            }
        });
        
        if(tableState.get().equals(TableStateEnum.FREE)) {
            setOnMouseClicked(event -> {
                    mouseClickConsumer.accept(event);
                });
        }
    }

    public void setImageFromState(TableStateEnum tableState) {
        this.setImage(null);
        this.setImage(tableStateImageMap.get(tableState));
    }
}
