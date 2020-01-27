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
public class ReceiptItem implements IEntity{
    private static final long serialVersionUID = 1L;

    private long id;
    private Receipt receipt;
    private Product product;
    private int amount;
    private double discount;

    public ReceiptItem(Product product, int amount, double discount) {
        this.product = product;
        this.amount = amount;
        this.discount = discount;
        this.receipt = new Receipt();
    }
    
    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
    
    public double getTotalPrice() {
        if(product != null) {
            return product.getPrice() * amount * (1 - discount);
        }
        
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ReceiptItem) {
            ReceiptItem other = (ReceiptItem)obj;
            return this.getId() == other.id || (this.getProduct().equals(other.product) && this.getReceipt().getId() == other.getReceipt().getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 97 * hash + Objects.hashCode(this.product);
        return hash;
    }
    
    
    
}
