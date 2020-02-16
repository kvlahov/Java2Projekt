/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.services.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author evlakre
 */
public class ReservationProcessor extends Thread {
    private final Logger LOG = Logger.getLogger(ReservationProcessor.class.getName());
    private final Socket clientSocket;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;

    ReservationProcessor(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        this.inputStream = new ObjectInputStream(clientSocket.getInputStream());
    }

    @Override
    public void run() {
        LOG.info( "Entered run...");
        while(true) {
//            try {
//                String incomingMsg = inputStream.readUTF();
//                LOG.info("Received msg: " + incomingMsg);
//                
//                if(incomingMsg.equalsIgnoreCase("exit")) {
//                    LOG.info("Closing connection: ");
//                    clientSocket.close();
//                    inputStream.close();
//                    outputStream.close();
//                    break;
//                } else {
//                    int random = new Random().nextInt(100);
//                    LOG.info("Sending random int: " + random);
//                    outputStream.writeUTF(String.valueOf(random));
//                    outputStream.flush();
//                }
//            } catch (IOException ex) {
//                LOG.log(Level.SEVERE, null, ex);
//            }
        }
    }

    private void close(Socket clientSocket) {
        try {
            clientSocket.close();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

}
