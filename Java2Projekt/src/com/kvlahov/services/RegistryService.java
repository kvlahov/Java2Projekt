/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.services;

import com.kvlahov.dal.repositories.IProductRepository;
import com.kvlahov.dal.repositories.IReceiptItemRepository;
import com.kvlahov.dal.repositories.IReceiptRepository;
import com.kvlahov.dal.repositories.IRepository;
import com.kvlahov.dal.repositories.implementations.CategoryRepository;
import com.kvlahov.dal.repositories.implementations.FileGenericRepository;
import com.kvlahov.dal.repositories.implementations.ProductRepository;
import com.kvlahov.dal.repositories.implementations.ReceiptItemRepository;
import com.kvlahov.dal.repositories.implementations.ReceiptRepository;
import com.kvlahov.model.Category;
import com.kvlahov.model.Product;
import com.kvlahov.model.Receipt;
import com.kvlahov.model.ReceiptItem;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author evlakre
 */
public class RegistryService {
    private final IReceiptItemRepository receiptItemRepo = new ReceiptItemRepository();
    private final IReceiptRepository receiptRepo = new ReceiptRepository();
    private final IProductRepository productRepo = new ProductRepository();
    private final IRepository<Category> categoryRepo = new CategoryRepository();
    
    public List<Product> getProducts() {
        return productRepo.getAll().stream().collect(Collectors.toList());
    }
    
    public List<Product> getProductsForCategory(Category category) {
        return productRepo.getAll()
                .stream()
                .filter(p -> p.getCategory().getId() == category.getId())
                .collect(Collectors.toList());
    }
    
    public List<Category> getCategories() {
        return categoryRepo.getAll().stream().collect(Collectors.toList());
    }
    
    public void insertReceipt(Receipt receipt, List<ReceiptItem> items ) {
        receiptRepo.add(receipt);
        
        items.forEach(item -> {
            item.setReceipt(receipt);
        });
        
        receiptItemRepo.addRange(items);
    }
    
    public void insertProduct(Product p) {
        productRepo.add(p);
    }
    public boolean updateProduct(Product p) {
        return productRepo.update(p.getId(), p) == 1;
    }
    public void deleteProduct(Product p) {
        productRepo.delete(p.getId());
    }
    
    public void insertCategory(Category c) {
        categoryRepo.add(c);
    }
    public boolean updateCategory(Category c) {
        boolean success = categoryRepo.update(c.getId(), c) == 1;
        if(success) {
            getProductsForCategory(c).forEach(p -> {
                p.setCategory(c);
                productRepo.update(p.getId(), p);
            });
        }
        return success;
    }
    public void deleteCategory(Category c) {
        getProductsForCategory(c).forEach(p -> productRepo.delete(p.getId()));
        categoryRepo.delete(c.getId());
    }
    
}
