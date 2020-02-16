/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.services.rmi;

import com.kvlahov.model.ReservationInfo;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author evlakre
 */
public interface IRmiServer extends Remote {

    void registerClient(IRmiClient client) throws RemoteException;

    void unregisterClient(IRmiClient client) throws RemoteException;

    void lockTable(long clientId, int id) throws RemoteException;

    void unlockTable(long clientId, int id) throws RemoteException;

    void reserveTable(long clientId, ReservationInfo reservation) throws RemoteException;

    void freeTable(long clientId, int id) throws RemoteException;
}
