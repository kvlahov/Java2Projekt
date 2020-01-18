/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.services;

import com.kvlahov.utilities.PropertiesManager;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author evlakre
 */
public class ReservationService {

    private Socket socket;

    public boolean connectToServer() throws IOException {

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
           
        return true;
    }
    
    
}
