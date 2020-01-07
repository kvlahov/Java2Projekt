/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.dal.repositories;

import com.kvlahov.model.IEntity;
import java.util.Optional;

/**
 *
 * @author lordo
 */
public interface IRepository<T> {
    Iterable<T> getAll();
    Optional<T> getById(long id);
    void add(T entity);
    void update(long id, T newEntity);
    void delete(long id);
}
