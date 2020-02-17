/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.services;

import com.kvlahov.model.ReservationInfo;
import com.kvlahov.model.ReservationInfoList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author evlakre
 */
public class XmlService {

    private final String path = "data/xml/reservations.xml";

    public void writeReservations(List<ReservationInfo> reservations) {
        ReservationInfoList list = new ReservationInfoList();
        list.setReservations(reservations);
        writeXML(list, path);
    }

    public List<ReservationInfo> readReservations() {
        ReservationInfoList list = readXML(path);

        if (list != null) {
            return list.getReservations();
        }
        return new ArrayList<>();
    }

    private void writeXML(ReservationInfoList entities, String path) {
        try {
            JAXBContext jContext = JAXBContext.newInstance(ReservationInfoList.class);
            //creating the marshaller object
            Marshaller marshallObj = jContext.createMarshaller();
            //setting the property to show xml format output
            marshallObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshallObj.marshal(entities, new FileOutputStream(path));

        } catch (JAXBException | FileNotFoundException ex) {
            Logger.getLogger(XmlService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ReservationInfoList readXML(String path) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ReservationInfoList.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            //We had written this file in marshalling example
            ReservationInfoList reservationList = (ReservationInfoList) jaxbUnmarshaller.unmarshal(new FileInputStream(path));

            return reservationList;
        } catch (JAXBException | FileNotFoundException ex) {
            Logger.getLogger(XmlService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
