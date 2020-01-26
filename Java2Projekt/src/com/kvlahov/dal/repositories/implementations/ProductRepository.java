/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.dal.repositories.implementations;

import com.kvlahov.dal.repositories.IProductRepository;
import com.kvlahov.model.Product;

/**
 *
 * @author evlakre
 */
public class ProductRepository extends FileGenericRepository<Product> implements IProductRepository{
    private static final String FILE = ".\\data\\products.ser";
    
    public ProductRepository() {
        super(FILE);
    }
    public ProductRepository(String filename) {
        super(filename);
    }    
    
}
