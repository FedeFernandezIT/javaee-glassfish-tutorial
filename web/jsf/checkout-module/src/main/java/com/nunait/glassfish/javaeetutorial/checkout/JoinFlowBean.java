/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.checkout;

import java.io.Serializable;
import javax.faces.flow.FlowScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/** 
 * Bean de respaldo para JoinFlow
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 4 de mayo de 2017 11:47:01 ART
 */
@Named
@FlowScoped("joinFlow")
public class JoinFlowBean implements Serializable {

    private static final long serialVersionUID = -5549495160805722049L;
    
    private boolean fanClub;
    private String[] newsletters;
    private static final SelectItem[] newsletterItems = {
        new SelectItem("NunaIT trimestral"),
        new SelectItem("Alamanaque del Innovador"),
        new SelectItem("Revista NunaIT de dieta y ejercicios"),
        new SelectItem("Temas aleatorios")
    };

    public JoinFlowBean() {
        newsletters = new String[0];
    }
    
    public String getReturnValue() {
        return "/exithome";
    }

    public boolean isFanClub() {
        return fanClub;
    }

    public void setFanClub(boolean fanClub) {
        this.fanClub = fanClub;
    }

    public String[] getNewsletters() {
        return newsletters;
    }

    public void setNewsletters(String[] newsletters) {
        this.newsletters = newsletters;
    }

    public SelectItem[] getNewsletterItems() {
        return newsletterItems;
    }        
    
}
