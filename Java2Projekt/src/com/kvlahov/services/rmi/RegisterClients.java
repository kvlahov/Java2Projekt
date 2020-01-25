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
public class RegisterClients {

    public static void main(String[] args) throws InterruptedException {

        try {
            Client1 client = new Client1();
            Client2 client2 = new Client2();
            Client3 client3 = new Client3();

            client.remote.registerClient(client);
            client.remote.registerClient(client2);
            client.remote.registerClient(client3);
            for (int i = 0; i < 5; i++) {
                Thread.sleep(2000);
                client.remote.sayHello(client);
            }

        } catch (RemoteException ex) {
            Logger.getLogger(RegisterClients.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
