/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.dal.repositories.implementations;

import com.kvlahov.dal.repositories.IReceiptRepository;
import com.kvlahov.model.Receipt;

/**
 *
 * @author evlakre
 */
public class ReceiptRepository extends FileGenericRepository<Receipt> implements IReceiptRepository{
    private static final String FILE = ".\\data\\receipts.ser";
    
    public ReceiptRepository() {
        super(FILE);
    }
    
    public ReceiptRepository(String filename) {
        super(filename);
    }
    
}
