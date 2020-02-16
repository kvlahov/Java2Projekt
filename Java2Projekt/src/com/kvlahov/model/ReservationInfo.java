/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.model;

import com.kvlahov.utilities.LocalDateTimeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author evlakre
 */
@XmlRootElement(name="reservationInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReservationInfo implements Serializable{

    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    private LocalDateTime reservationDateTime;
    private String name;
    private int tableId;

    public ReservationInfo() {
    }

    public ReservationInfo(LocalDateTime reservationDateTime, String name, int tableId) {
        this.reservationDateTime = reservationDateTime;
        this.name = name;
        this.tableId = tableId;
    }

    public LocalDateTime getReservationDateTime() {
        return reservationDateTime;
    }

    public void setReservationDateTime(LocalDateTime reservationDateTime) {
        this.reservationDateTime = reservationDateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.reservationDateTime);
        hash = 89 * hash + this.tableId;
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
        final ReservationInfo other = (ReservationInfo) obj;
        if (this.tableId != other.tableId) {
            return false;
        }
        return Objects.equals(this.reservationDateTime, other.reservationDateTime);
    }

}
