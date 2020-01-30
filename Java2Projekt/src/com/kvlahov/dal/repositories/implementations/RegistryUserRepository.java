/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.dal.repositories.implementations;

import com.kvlahov.dal.repositories.IRepository;
import com.kvlahov.model.RegistryUser;

/**
 *
 * @author evlakre
 */
public class RegistryUserRepository extends FileGenericRepository<RegistryUser>{
    private static final String FILE = ".\\data\\registry_users.ser";
    
    public RegistryUserRepository() {
        super(FILE);
    }
    
    public RegistryUserRepository(String filename) {
        super(filename);
    }
    
}
