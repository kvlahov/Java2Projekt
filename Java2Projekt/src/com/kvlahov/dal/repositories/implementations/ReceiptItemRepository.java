/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.dal.repositories.implementations;

import com.kvlahov.dal.repositories.IReceiptItemRepository;
import com.kvlahov.model.ReceiptItem;

/**
 *
 * @author evlakre
 */
public class ReceiptItemRepository extends FileGenericRepository<ReceiptItem> implements IReceiptItemRepository {

    private static final String FILE = ".\\data\\receiptitems.ser";

    public ReceiptItemRepository() {
        super(FILE);
    }

    public ReceiptItemRepository(String filename) {
        super(filename);
    }

}
