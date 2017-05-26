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
 * @created 25 de mayo de 2017 10:04:37 ART
 */
public class PlayerDetails implements Serializable {
    private static final long serialVersionUID = -6183101573014497053L;
    
    private String id;
    private String name;
    private String position;
    private double salary;

    public PlayerDetails() {
    }

    public PlayerDetails(String id, String name, String position, double salary) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        String s = id + " " + name + " " + position + " " + salary;
        return s;
    }
    
    
}
