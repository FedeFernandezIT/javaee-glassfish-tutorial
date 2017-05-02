/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.ajaxguessnumber;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 2 de mayo de 2017 0:03:40 ART
 */
@Named
@RequestScoped
public class UserNumberBean implements Serializable {

    private static final long serialVersionUID = -5909936232014690801L;
    
    @Inject
    NunaNumberBean nunaNumberBean;
    private Integer userNumber = null;
    String response = null;

    public Integer getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(Integer userNumber) {
        this.userNumber = userNumber;
    }

    public String getResponse() {
        if ((userNumber != null)
                && (userNumber.compareTo(nunaNumberBean.getRandomInt()) == 0)) {
            return "Muy bien! Ha acertado!";
        }
        if (userNumber == null) {
            return null;
        } else {
            return "Lo siento, " + userNumber + " es incorrecto ";
        }        
    }
    
}
