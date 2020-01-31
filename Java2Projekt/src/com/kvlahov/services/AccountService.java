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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if (isUserValid(username, password)) {
            return userRepository.findByUsername(username);
        } else {
            return Optional.empty();
        }
    }

    private boolean isUserValid(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get().getPassword().equals(password);
        }
        return false;
    }

    public Optional<RegistryUser> loginRegistryUser(long pin) {
        Optional<RegistryUser> registryUser = registryUserRepository.getAll().stream().filter(ru -> ru.getPin() == pin).findFirst();
        return registryUser;
    }

    //crud
    public List<User> getUsers() {
        return userRepository.getAll().stream().collect(Collectors.toList());
    }

    public void addUser(User u) {
        userRepository.add(u);
    }

    public boolean updateUser(User u) {
        return userRepository.update(u.getId(), u) == 1;
    }

    public void removeUser(User u) {
        userRepository.delete(u.getId());
    }

    public List<RegistryUser> getRegistryUsers() {
        return (List<RegistryUser>) registryUserRepository.getAll();
    }

    public void addRegistryUser(RegistryUser u) {
        registryUserRepository.add(u);
    }

    public boolean updateRegistryUser(RegistryUser u) {
        return registryUserRepository.update(u.getId(), u) == 1;
    }

    public void removeRegistryUser(RegistryUser u) {
        registryUserRepository.delete(u.getId());
    }
}
