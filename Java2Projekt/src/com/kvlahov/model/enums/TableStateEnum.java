/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.model.enums;

/**
 *
 * @author evlakre
 */
public enum TableStateEnum {
    RESERVED {
        @Override
        public String toString() {
            return "Reserved"; //To change body of generated methods, choose Tools | Templates.
        }

    },
    UNAVAILABLE {
        @Override
        public String toString() {
            return "Unavailable"; //To change body of generated methods, choose Tools | Templates.
        }
    },
    FREE {
        @Override
        public String toString() {
            return "Free"; //To change body of generated methods, choose Tools | Templates.
        }
    }
}
