/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.utilities;

import javafx.util.StringConverter;

/**
 *
 * @author evlakre
 */
public class SafeDoubleStringConverter extends StringConverter<Double>{

    @Override
    public String toString(Double value) {
         if (value == null) {
            return "";
        }

        return Double.toString(value);
    }

    @Override
    public Double fromString(String value) {
         if (value == null) {
            return null;
        }

        value = value.trim();

        if (value.length() < 1) {
            return null;
        }

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException numberFormatException) {
            return null;
        }
    }
    
}
