/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Binding;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

/**
 *
 * @author evlakre
 */
public class JNDItest {

    public static void main(String[] arg) throws FileNotFoundException, IOException {
        // kreiramo Hashtable objekt
        Hashtable env = new Hashtable();

//        Properties props = new Properties();
//        String root = System.getProperty("user.dir");
//        String propsFile = root + "/data/properties/properties.props";
//        
//        
//        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(propsFile)));
//        String line;
//        try {
//            while ((line = br.readLine()) != null) {
//                System.out.println(line);
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(JNDItest.class.getName()).log(Level.SEVERE, null, ex);
//        }

        // Hashtable punimo sa podacima koji govore o kojoj implementaciji
        // usluge imenovanja se radi
        // i koja nam je po�etna to�ka u hijerarhiji gdje se pozicioniramo
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.fscontext.RefFSContextFactory");

        env.put(Context.PROVIDER_URL, "file:data/properties");
        try {
            // kreiramo objekt tipa Context
            Context ctxt = new InitialContext(env);
            Object lookup = ctxt.lookup("properties.props");
            
            System.out.println(lookup instanceof File);
            System.out.println(lookup.getClass());
            // ispi�i imena
//            while (flist.hasMore()) {
//                Binding p = (Binding) flist.next();
//                System.out.println(p.getName());
//            }
        } catch (NamingException ne) {
            System.out.println(ne);
        }
    }

}
