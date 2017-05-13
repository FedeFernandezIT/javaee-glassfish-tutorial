/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.guessnumber.cdi;

import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 13 de mayo de 2017 10:20:41 ART
 */
@ApplicationScoped
public class Generator implements Serializable {

    private static final long serialVersionUID = 6692933815425252461L;
    
    private final java.util.Random random =
            new java.util.Random(System.currentTimeMillis());
    
    private final int maxNumber = 100;

    java.util.Random getRandom() {
        return random;
    }
    
    @Produces @Random int next() {
        return getRandom().nextInt(maxNumber + 1);
    }
    
    @Produces @MaxNumber int getMaxNumber() {
        return maxNumber;
    }

}
