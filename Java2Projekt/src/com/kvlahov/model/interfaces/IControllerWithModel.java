/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.model.interfaces;

/**
 *
 * @author evlakre
 */
public interface IControllerWithModel<T> {
    public void setModel(T model);
    public T getModel();
}
