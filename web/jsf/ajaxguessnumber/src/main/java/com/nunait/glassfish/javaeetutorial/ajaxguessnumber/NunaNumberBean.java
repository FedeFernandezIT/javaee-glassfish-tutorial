/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.ajaxguessnumber;

import java.io.Serializable;
import java.util.Random;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 2 de mayo de 2017 0:07:53 ART
 */
@Named
@SessionScoped
public class NunaNumberBean implements Serializable {

    private static final long serialVersionUID = -7932202062153851172L;
    
    private Integer randomInt = null;
    private long maximum = 10;
    private long minimum = 0;

    public NunaNumberBean() {
        Random randomGR = new Random();
        long range = maximum + minimum + 1;
        randomInt = (int)(minimum + randomGR.nextDouble() * range);
    }

    public Integer getRandomInt() {
        return randomInt;
    }

    public void setRandomInt(Integer randomInt) {
        this.randomInt = randomInt;
    }

    public long getMaximum() {
        return maximum;
    }

    public void setMaximum(long maximum) {
        this.maximum = maximum;
    }

    public long getMinimum() {
        return minimum;
    }

    public void setMinimum(long minimum) {
        this.minimum = minimum;
    }
        
}
