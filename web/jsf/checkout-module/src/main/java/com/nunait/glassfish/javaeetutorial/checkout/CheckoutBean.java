/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.checkout;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/** 
 * Bean de respaldo para index.xhtml
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 4 de mayo de 2017 12:32:08 ART
 */

@Named
@SessionScoped
public class CheckoutBean implements Serializable {
    
    private static final long serialVersionUID = 2850340689200909618L;
    
    int numItems = 3;
    double cost = 101.25;

    public int getNumItems() {
        return numItems;
    }

    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
            
}
