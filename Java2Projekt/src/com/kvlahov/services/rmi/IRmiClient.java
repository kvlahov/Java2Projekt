/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.services.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author evlakre
 */
public interface IRmiClient extends Remote {

    long getClientId() throws RemoteException;

    void setClientId(long id) throws RemoteException;

    void notifyNoClientsChanges(int noOfClients) throws RemoteException;

    void notifyTableLocked(int tableId) throws RemoteException;

    void notifyTableUnlocked(int tableId) throws RemoteException;

    void notifyTableReserved(int tableId) throws RemoteException;

    void notifyTableFreed(int tableId) throws RemoteException;

}
