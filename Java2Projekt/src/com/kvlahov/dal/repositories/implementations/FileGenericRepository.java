/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.dal.repositories.implementations;

import com.kvlahov.dal.repositories.IRepository;
import com.kvlahov.model.IEntity;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author lordo
 */
public class FileGenericRepository<T extends IEntity> implements IRepository<T> {

    private final String filename;

    public FileGenericRepository(String filename) {
        this.filename = filename;

        File dataFile = new File(filename);
        try {
            dataFile.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Collection<T> getAll() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            return ((Collection<T>) inputStream.readObject())
                    .stream()
                    .sorted((o, o2) -> Long.compare(o.getId(),o2.getId()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            Logger.getLogger(FileGenericRepository.class.getName()).log(Level.SEVERE, null, e);
            return new ArrayList();
        }
    }

    @Override
    public Optional<T> getById(long id) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            Collection<T> collection = (Collection<T>) inputStream.readObject();
            return collection.stream().filter(m -> m.getId() == id).findFirst();
        } catch (Exception e) {
            Logger.getLogger(FileGenericRepository.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    @Override
    public void add(T entity) {
        Collection<T> allElements = getAll();
        long entityId = allElements.stream().mapToLong(el -> el.getId()).max().orElse(0) + 1;
        entity.setId(entityId);
        if (!allElements.contains(entity)) {
            allElements.add(entity);
        }
        saveChanges(allElements);
    }

    @Override
    public void update(long id, T newEntity) {
        Collection<T> allElements = getAll();
        newEntity.setId(id);
        allElements.removeIf(o -> o.getId() == id);
        allElements.add(newEntity);
        saveChanges(allElements);
    }

    @Override
    public void delete(long id) {
        Collection<T> allElements = getAll();
        allElements.removeIf(o -> o.getId() == id);
        saveChanges(allElements);
    }

    @Override
    public void saveChanges(Collection<T> allElements) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            outputStream.writeObject(allElements);
        } catch (Exception e) {
            Logger.getLogger(FileGenericRepository.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
