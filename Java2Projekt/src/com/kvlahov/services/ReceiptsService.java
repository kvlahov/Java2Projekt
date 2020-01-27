/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.services;

import com.kvlahov.dal.repositories.IReceiptItemRepository;
import com.kvlahov.dal.repositories.IReceiptRepository;
import com.kvlahov.dal.repositories.implementations.ReceiptItemRepository;
import com.kvlahov.dal.repositories.implementations.ReceiptRepository;
import com.kvlahov.model.Receipt;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author evlakre
 */
public class ReceiptsService {
    private final IReceiptRepository receiptRepo = new ReceiptRepository();
    private final IReceiptItemRepository receiptItemRepo = new ReceiptItemRepository();
    
    public double getReceiptTotal(Receipt receipt) {
        return receiptItemRepo.getAll()
                .stream()
                .filter(ri -> ri.getReceipt().equals(receipt))
                .mapToDouble(ri -> ri.getTotalPrice())
                .sum();
    }
    
    public List<Receipt> getReceipts() {
        return receiptRepo.getAll().stream().collect(Collectors.toList());
    }
    
}
