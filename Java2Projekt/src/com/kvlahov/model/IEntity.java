/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.model;

import java.io.Serializable;

/**
 *
 * @author lordo
 */
public interface IEntity extends Serializable{
    long getId();
    void setId(long id);
}
