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
import com.kvlahov.model.ReceiptItem;
import java.time.LocalDate;
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
    
    public List<ReceiptItem> getReceiptItemsForReceipt(Receipt receipt) {
        return receiptItemRepo
                .getAll()
                .stream()
                .filter(ri -> ri.getReceipt().equals(receipt))
                .collect(Collectors.toList());
    }
    
    public Receipt cancelReceipt(Receipt r) {
        
        Receipt canceledReceipt = new Receipt();
        canceledReceipt.setCreatedByUser(r.getCreatedByUser());
        canceledReceipt.setDateCreated(LocalDate.now());
        
        receiptRepo.add(canceledReceipt);
        List<ReceiptItem> receiptItems = getReceiptItemsForReceipt(r);
        
        receiptItems.forEach(ri -> {
            ri.setId(0);
            ri.setReceipt(canceledReceipt);
            ri.setAmount(ri.getAmount() * -1);
        });
        
        receiptItemRepo.addRange(receiptItems);
        
        return canceledReceipt;        
    }
    
}
