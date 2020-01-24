/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.services;

import com.kvlahov.utilities.PropertiesManager;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author evlakre
 */
public class ReservationService {

    private static final Logger LOG = Logger.getLogger(ReservationService.class.getName());
    private Socket socket;

    public boolean connectToServer() throws IOException, IllegalArgumentException {

        PropertiesManager pm = null;

        try {
            pm = new PropertiesManager();
        } catch (NamingException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (pm == null) {
            return false;
        }

        socket = new Socket(pm.getServerUrl(), pm.getServerPort());

//        new Thread(() -> {
//            try (
//                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());) {
//
//                while (true) {
//                    Scanner sc = new Scanner(System.in);
//                    String msg = sc.next();
//                    LOG.info("Sending to server...");
//
//                    oos.writeUTF(msg);
//                    oos.flush();
//
//                    if (msg.equalsIgnoreCase("exit")) {
//                        socket.close();
//                        break;
//                    }
//
//                    LOG.info("Received from server: " + ois.readUTF());
//                }
//
//            } catch (IOException ex) {
//                LOG.log(Level.SEVERE, null, ex);
//            }
//        }).start();

        return true;
    }
    
    public void sendMessage(String message) {
        
    }

}
