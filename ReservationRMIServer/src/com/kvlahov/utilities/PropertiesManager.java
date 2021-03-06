/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketImpl;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author evlakre
 */
public class PropertiesManager {

    private static final String PROPS_ROOT = "file:data/properties/";
    private static final String PROPS_FILE = "properties.props";
    private static final Logger LOG = Logger.getLogger(PropertiesManager.class.getName());

    private File file;

    public PropertiesManager(){
        try {
            initJNDI();
        } catch (NamingException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    private void initJNDI() throws NamingException {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.fscontext.RefFSContextFactory");

        env.put(Context.PROVIDER_URL, PROPS_ROOT);

        Context context = new InitialContext(env);
        file = (File) context.lookup(PROPS_FILE);

    }

    public String getProperty(String key) {
        Properties props = new Properties();
        try(FileInputStream fis = new FileInputStream(file)) {
            props.load(fis);
        } catch (FileNotFoundException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return props.getProperty(key);
    }

    public int getServerPort() {
        return Integer.parseInt(getProperty("server.port"));
    }
    
    public String getServerUrl() {
        return getProperty("server.url");
    }
    
    public String getDateFormat() {
        return getProperty("date.format");
    }
}
