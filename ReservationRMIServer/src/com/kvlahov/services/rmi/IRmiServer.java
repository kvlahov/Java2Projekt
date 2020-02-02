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
public interface RmiInterface extends Remote {

    public void sayHello(RmiClient client) throws RemoteException;

    public void registerClient(RmiClient client) throws RemoteException;
    
    public void notifyAllClients(String msg) throws RemoteException;
}
