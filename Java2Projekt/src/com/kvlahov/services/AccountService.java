/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.services;

import com.kvlahov.dal.repositories.IRepository;
import com.kvlahov.dal.repositories.IUserRepository;
import com.kvlahov.dal.repositories.implementations.RegistryUserRepository;
import com.kvlahov.dal.repositories.implementations.UserRepository;
import com.kvlahov.model.RegistryUser;
import com.kvlahov.model.User;
import java.util.Optional;

/**
 *
 * @author lordo
 */
public class AccountService {
    
    private IUserRepository userRepository;
    private IRepository<RegistryUser> registryUserRepository;

    public AccountService() {
        this.userRepository = new UserRepository();
        this.registryUserRepository = new RegistryUserRepository();
    }

    public Optional<User> loginUser(String username, String password) {
        if(isUserValid(username, password)){
            return userRepository.findByUsername(username);
        } else {
            return Optional.empty();
        }
    }

    private boolean isUserValid(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()) {
            return user.get().getPassword().equals(password);
        }
        return false;
    }
    
    public Optional<RegistryUser> loginRegistryUser(long pin) {
        Optional<RegistryUser> registryUser = registryUserRepository.getAll().stream().filter(ru -> ru.getPin() == pin).findFirst();
        return registryUser;
    }
}
