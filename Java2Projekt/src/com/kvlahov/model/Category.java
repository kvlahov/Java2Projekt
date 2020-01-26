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
public class Category implements IEntity {
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" + "id=" + id + ", name=" + name + '}';
    }
    
     @Override
    public boolean equals(Object obj) {
        if (obj instanceof Category) {
            Category other = ((Category) obj);
            return other.id == this.id
                    || other.name.equals(this.name);
        }
        return false;
    }

}
