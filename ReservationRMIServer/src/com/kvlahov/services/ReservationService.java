/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.services;

import com.kvlahov.model.ReservationInfo;
import com.kvlahov.services.rmi.IRmiClient;
import com.kvlahov.services.rmi.IRmiServer;
import com.kvlahov.services.rmi.RmiServer;
import com.kvlahov.utilities.PropertiesManager;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author evlakre
 */
public class ReservationService implements IRmiClient {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ReservationService.class.getName());
    private IRmiServer server;
    private long id;

    private Consumer<Integer> tableLockedConsumer;
    private Consumer<Integer> tableUnlockedConsumer;
    private Consumer<Integer> tableReservedConsumer;
    private Consumer<Integer> tableFreedConsumer;
    private Consumer<Integer> noClientsChangedConsumer;

    private boolean registerClientImmidiately = true;

//    private Socket socket;
    public ReservationService() {
    }

    public ReservationService(boolean registerClientImmidiately) {
        this.registerClientImmidiately = registerClientImmidiately;
    }

    public ReservationService(Consumer<Integer> tableLockedConsumer, Consumer<Integer> tableUnlockedConsumer, Consumer<Integer> tableReservedConsumer, Consumer<Integer> tableFreedConsumer) {
        this.tableLockedConsumer = tableLockedConsumer;
        this.tableUnlockedConsumer = tableUnlockedConsumer;
        this.tableReservedConsumer = tableReservedConsumer;
        this.tableFreedConsumer = tableFreedConsumer;
    }

    public boolean connectToServer() {

        try {
            PropertiesManager pm = new PropertiesManager();

            Registry registry = LocateRegistry.getRegistry(pm.getServerPort());
            try {
                this.server = ((IRmiServer) registry.lookup(pm.getServerUrl()));
                if (registerClientImmidiately) {
                    registerClient();
                }
            } catch (RemoteException | NotBoundException ex) {
                return false;
            }

            return true;

        } catch (IOException ex) {
            LOG.info(ex.getStackTrace().toString());
            return false;
        }
    }

    @Override
    public long getClientId() throws RemoteException {
        return id;
    }

    @Override
    public void setClientId(long id) throws RemoteException {
        this.id = id;
    }

    @Override
    public void notifyTableLocked(int tableId) throws RemoteException {
        getTableLockedConsumer().accept(tableId);
    }

    @Override
    public void notifyTableUnlocked(int tableId) throws RemoteException {
        getTableUnlockedConsumer().accept(tableId);
    }

    @Override
    public void notifyTableReserved(int tableId) throws RemoteException {
        getTableReservedConsumer().accept(tableId);
    }

    @Override
    public void notifyTableFreed(int tableId) throws RemoteException {
        getTableFreedConsumer().accept(tableId);
    }

    @Override
    public void notifyNoClientsChanges(int noOfClients) throws RemoteException {
        noClientsChangedConsumer.accept(noOfClients);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReservationService other = (ReservationService) obj;
        return this.id == other.id;
    }

    public IRmiServer getServer() {
        return server;
    }

    public void setServer(IRmiServer server) {
        this.server = server;
    }

    public Consumer<Integer> getTableLockedConsumer() {
        return tableLockedConsumer;
    }

    public void setTableLockedConsumer(Consumer<Integer> tableLockedConsumer) {
        this.tableLockedConsumer = tableLockedConsumer;
    }

    public Consumer<Integer> getTableUnlockedConsumer() {
        return tableUnlockedConsumer;
    }

    public void setTableUnlockedConsumer(Consumer<Integer> tableUnlockedConsumer) {
        this.tableUnlockedConsumer = tableUnlockedConsumer;
    }

    public Consumer<Integer> getTableReservedConsumer() {
        return tableReservedConsumer;
    }

    public void setTableReservedConsumer(Consumer<Integer> tableReservedConsumer) {
        this.tableReservedConsumer = tableReservedConsumer;
    }

    public Consumer<Integer> getTableFreedConsumer() {
        return tableFreedConsumer;
    }

    public void setTableFreedConsumer(Consumer<Integer> tableFreedConsumer) {
        this.tableFreedConsumer = tableFreedConsumer;
    }

    public Consumer<Integer> getNoClientsChangedConsumer() {
        return noClientsChangedConsumer;
    }

    public void setNoClientsChangedConsumer(Consumer<Integer> noClientsChangedConsumer) {
        this.noClientsChangedConsumer = noClientsChangedConsumer;
    }

    public void registerClient() throws RemoteException {
        this.server.registerClient((IRmiClient) UnicastRemoteObject.exportObject(this, 0));
    }

    public void lockTable(int tableId) {
        try {
            server.lockTable(id, tableId);
        } catch (RemoteException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void unlockTable(int tableId) {
        try {
            server.unlockTable(id, tableId);
        } catch (RemoteException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reserveTable(ReservationInfo reservation) {
        try {
            server.reserveTable(id, reservation);
        } catch (RemoteException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void freeTable(int tableId) {
        try {
            server.freeTable(id, tableId);
        } catch (RemoteException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        server.unregisterClient(this);
        super.finalize();
    }

}
