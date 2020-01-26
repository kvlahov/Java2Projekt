/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.model;

/**
 *
 * @author evlakre
 */
public class Product implements IEntity {

    private long id;
    private String name;
    private double price;
    private Category category;

    public Product() {
    }

    public Product(String name, double price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", price=" + price + ", category=" + category + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Product) {
            Product other = ((Product) obj);
            return other.id == this.id
                    || other.name.equals(this.name);
        }
        return false;
    }

}
