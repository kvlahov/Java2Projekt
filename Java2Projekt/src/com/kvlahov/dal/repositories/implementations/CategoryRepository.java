/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.dal.repositories.implementations;

import com.kvlahov.model.Category;

/**
 *
 * @author evlakre
 */
public class CategoryRepository extends FileGenericRepository<Category> {

    private static final String FILE = ".\\data\\categories.ser";

    public CategoryRepository() {
        super(FILE);
    }

    public CategoryRepository(String filename) {
        super(filename);
    }

}
