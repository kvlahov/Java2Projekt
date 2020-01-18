/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.model;

import com.kvlahov.model.enums.UserRoleEnum;
import java.io.Serializable;

/**
 *
 * @author lordo
 */
public class User implements IEntity, Serializable{
    private long id;
    private String username;
    private String password;
    private UserRoleEnum role;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = UserRoleEnum.REGULAR;
    }
    
    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User: "+ username;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User) {
            User other = ((User) obj);
            return other.id == this.id || 
                    other.username.equals(this.username);
        }
        return false;
    }
    
    
        
    
}
