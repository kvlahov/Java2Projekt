/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.services.rmi;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author evlakre
 */
public class Client2 extends RmiClient{

    public Client2() {
        super("client2", 5);
    }
    
     public static void main(String[] args) {
        try {
            Client2 client = new Client2();
            client.remote.registerClient(client);
//            client.remote.sayHello("test from client2");
        } catch (RemoteException ex) {
            Logger.getLogger(Client1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
