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
 * @created 25 de mayo de 2017 10:11:35 ART
 */
public class LeagueDetails implements Serializable {
    private static final long serialVersionUID = -7217468010007410223L;

    private String id;
    private String name;
    private String sport;

    public LeagueDetails(String id, String name, String sport) {
        this.id = id;
        this.name = name;
        this.sport = sport;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSport() {
        return sport;
    }

    @Override
    public String toString() {
        String s = id + " " + name + " " + sport;
        return s;
    }
        
}
