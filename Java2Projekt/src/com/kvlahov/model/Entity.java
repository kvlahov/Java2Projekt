/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.model;

import java.io.Serializable;

/**
 *
 * @author evlakre
 */
public class Entity implements IEntity, Serializable {
    
    private long id;

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Entity) {
            return ((Entity) obj).getId() == this.getId();
        }
        return false;
    }
    
    
}
