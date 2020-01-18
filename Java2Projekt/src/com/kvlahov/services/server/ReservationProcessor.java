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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author evlakre
 */
public class ReservationProcessor extends Thread {

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
        close(clientSocket);
    }

    private void close(Socket clientSocket) {
        try {
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ReservationProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
