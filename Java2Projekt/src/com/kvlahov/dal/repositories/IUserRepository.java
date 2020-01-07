/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.dal.repositories;

import com.kvlahov.model.User;

/**
 *
 * @author lordo
 */
public interface IUserRepository extends IRepository<User>  {
    User findByUsername(String username);
}
