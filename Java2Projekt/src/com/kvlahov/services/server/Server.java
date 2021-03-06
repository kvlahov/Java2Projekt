/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.services.server;

import com.kvlahov.utilities.PropertiesManager;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author evlakre
 */
public class Server {

    private static final Logger LOG = Logger.getLogger(Server.class.getName());

    public static void main(String[] args) {
        try {
            PropertiesManager pm = new PropertiesManager();
            ServerSocket s = new ServerSocket(pm.getServerPort());
            
            while (true) {
                LOG.info("Server waiting for client...");
                Socket clientSocket = s.accept();

                LOG.log(Level.INFO, "Accepting client {0}", clientSocket);

                new ReservationProcessor(clientSocket).start();
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
}
