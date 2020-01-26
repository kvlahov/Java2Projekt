/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.dal.repositories;

import java.util.Collection;
import java.util.Optional;

/**
 *
 * @author lordo
 */
public interface IRepository<T> {
    Collection<T> getAll();
    Optional<T> getById(long id);
    long add(T entity);
    void addRange(Collection<T> entities);
    void update(long id, T newEntity);
    void delete(long id);
    void deleteAll();
    void saveChanges(Collection<T> allElements);
}
