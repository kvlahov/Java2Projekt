/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.services.rmi;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author evlakre
 */
public class RmiClient implements Serializable {

    private static final Logger LOG = Logger.getLogger(RmiClient.class.getName());
    private String name;
    private int counter;
    RmiInterface remote;

    public RmiClient() {
        try {
            Registry registry = LocateRegistry.getRegistry(12345);
            this.remote = (RmiInterface) registry.lookup("//localhost//server");
        } catch (Exception ex) {
            Logger.getLogger(RmiClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public RmiClient(String name, int counter) {
        this();
        this.name = name;
        this.counter = counter;
    }

    public void printMessageFromServer(String msg) {
        LOG.info("Message from server: " + msg);
        LOG.log(Level.INFO, "Counter state for {0}: {1}", new Object[]{getName(), getCounter()});
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCounter() {
        return counter;
    }

    public void incrementCounter() {
        this.counter++;
    }
}
