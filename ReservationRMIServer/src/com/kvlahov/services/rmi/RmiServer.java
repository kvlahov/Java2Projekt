/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.services.rmi;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author evlakre
 */
public class RmiServer implements RmiInterface {

    private static final Logger LOG = Logger.getLogger(RmiServer.class.getName());
    private List<RmiClient> clients;

    @Override
    public void sayHello(RmiClient client) throws RemoteException {
        LOG.log(Level.INFO, "Hello to {0}", client.getName());
        client.incrementCounter();
        notifyAllClients("sayHello called from client: " + client.getName());
    }

    @Override
    public void registerClient(RmiClient client) throws RemoteException {
        this.clients.add(client);
    }

    @Override
    public void notifyAllClients(String msg) throws RemoteException {
        clients.forEach(c -> c.printMessageFromServer(msg));
    }

    public RmiServer() {
        clients = new ArrayList<>();
    }

    public static void main(String[] args) {
        try {
            RmiServer obj = new RmiServer();
            RmiInterface stub = (RmiInterface) UnicastRemoteObject.exportObject(obj, 12345);

            Registry registry = LocateRegistry.createRegistry(12345);
            registry.rebind("//localhost//server", stub);

            LOG.info("Server ready");

        } catch (RemoteException ex) {
            Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
