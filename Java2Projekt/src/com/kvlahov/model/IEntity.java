/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author lordo
 */
public interface IEntity extends Serializable {

    long getId();

    void setId(long id);

    default void setEntity(IEntity newEntity) {
        if (!this.getClass().equals(newEntity.getClass())) {
            return;
        }

        Stream.of(this.getClass().getDeclaredFields())
                .filter(f -> !Modifier.isFinal(f.getModifiers()))
                .forEach(f -> {
                    try {
                        f.setAccessible(true);
                        f.set(this, f.get(newEntity));
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        Logger.getLogger(IEntity.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
    }
}
