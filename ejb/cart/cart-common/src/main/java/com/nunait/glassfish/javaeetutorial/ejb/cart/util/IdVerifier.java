/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.ejb.cart.util;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 19 de mayo de 2017 14:56:06 ART
 */
public class IdVerifier {

    public IdVerifier() {
    }
    
    public boolean validate(String id) {
        boolean result = true;
        for (int i = 0; i < id.length(); i++) {
            if (Character.isDigit(id.charAt(i)) == false) {
                return false;
            }
        }
        return result;
    }
}
