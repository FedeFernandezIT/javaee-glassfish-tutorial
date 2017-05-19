/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.ejb.cart.util;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 19 de mayo de 2017 14:52:23 ART
 */
public class BookException extends Exception {
    private static final long serialVersionUID = 1883152202920563531L;

    public BookException() {
    }

    public BookException(String msg) {
        super(msg);
    }        

}
