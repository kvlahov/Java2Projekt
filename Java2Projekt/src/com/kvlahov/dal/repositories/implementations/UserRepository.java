/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.dal.repositories.implementations;

import com.kvlahov.dal.repositories.IUserRepository;
import com.kvlahov.model.User;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author evlakre
 */
public class UserRepository extends FileGenericRepository<User> implements IUserRepository{
    private static final String FILE = ".\\data\\users.ser";
    private static int idCounter = 1;
    public UserRepository() {
        super(FILE);
    }

    public UserRepository(String filename) {
        super(filename);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return getAll().stream()
                .filter(x -> x.getUsername().equals(username))
                .findFirst();                
    }
    
}
