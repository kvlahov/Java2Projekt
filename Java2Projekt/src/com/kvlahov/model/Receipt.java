/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.model;

import java.time.LocalDate;

/**
 *
 * @author evlakre
 */
public class Receipt implements IEntity {
    private static final long serialVersionUID = 1L;

    private long id;
    private LocalDate dateCreated;
    private User createdByUser;

    public Receipt() {
    }

    public Receipt(LocalDate dateCreated, User createdByUser) {
        this.dateCreated = dateCreated;
        this.createdByUser = createdByUser;
    }    
    
    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(User createdByUser) {
        this.createdByUser = createdByUser;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Receipt other = (Receipt) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    

    
}
