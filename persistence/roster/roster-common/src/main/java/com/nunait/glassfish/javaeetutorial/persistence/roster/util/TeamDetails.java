/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.persistence.roster.util;

import java.io.Serializable;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 25 de mayo de 2017 10:09:03 ART
 */
public class TeamDetails implements Serializable {
    private static final long serialVersionUID = -2644771949189218786L;

    private String id;
    private String name;
    private String city;

    public TeamDetails(String id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        String s = id + " " + name + " " + city;
        return s;
    }        
    
}
