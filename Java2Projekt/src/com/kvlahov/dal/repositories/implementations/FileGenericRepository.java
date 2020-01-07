/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.dal.repositories.implementations;

import com.kvlahov.dal.repositories.IRepository;
import com.kvlahov.model.IEntity;
import com.kvlahov.utilities.Streams;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author lordo
 */
public class FileGenericRepository<T extends IEntity> implements IRepository<T> {
    
    private final String filename;
    public FileGenericRepository(String filename) {
        this.filename = filename;
    }

    @Override
    public Iterable<T> getAll() {
        try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            return (Iterable<T>) inputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    @Override
    public Optional<T> getById(long id) {
        try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            Iterable<T> collection = (Iterable<T>) inputStream.readObject();
            return Streams.streamOf(collection).filter(m -> m.getId() == id).findFirst();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void add(T entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(long id, T newEntity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
