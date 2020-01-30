/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.model;

import java.util.Objects;

/**
 *
 * @author evlakre
 */
public class RegistryUser implements IEntity {
    private long id;
    private long pin;
    private String username;

    public RegistryUser() {
    }

    public RegistryUser(long pin, String username) {
        this.pin = pin;
        this.username = username;
    }
    
    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getPin() {
        return pin;
    }

    public void setPin(long pin) {
        this.pin = pin;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 53 * hash + (int) (this.pin ^ (this.pin >>> 32));
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
        final RegistryUser other = (RegistryUser) obj;
        return this.id == other.id || this.pin == other.pin;
    }
    
    
}
