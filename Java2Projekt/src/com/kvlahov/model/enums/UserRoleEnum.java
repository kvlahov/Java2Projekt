/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.model.enums;

/**
 *
 * @author lordo
 */
public enum UserRoleEnum {
    REGULAR {
        @Override
        public String toString() {
            return "Regular";
        }
    }, 
    ADMIN {
        @Override
        public String toString() {
            return "Admin";
        }
    }
}
