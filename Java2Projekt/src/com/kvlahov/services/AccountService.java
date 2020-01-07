/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.services;

import com.kvlahov.dal.repositories.IUserRepository;
import com.kvlahov.model.User;
import java.util.Optional;

/**
 *
 * @author lordo
 */
public class AccountService {
    
    private IUserRepository userRepository;

    public AccountService() {
        this.userRepository = new UserRepository();
    }

    public Optional<User> loginUser(String username, String password) {
        if(isUserValid(username, password)){
            return Optional.of(userRepository.findByUsername(username));
        } else {
            return Optional.empty();
        }
    }

    private boolean isUserValid(String username, String password) {
        Optional<User> user = Optional.of(userRepository.findByUsername(username));
        if(user.isPresent()) {
            return user.get().getPassword().equals(password);
        }
        return true;
    }
}
