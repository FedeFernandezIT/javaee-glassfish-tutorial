/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.persistence.roster.entity;

import com.nunait.glassfish.javaeetutorial.persistence.roster.util.IncorrectSportException;
import java.io.Serializable;
import javax.persistence.Entity;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 25 de mayo de 2017 12:26:10 ART
 */
@Entity
public class SummerLeague extends League implements Serializable {
    private static final long serialVersionUID = 8031100905686150495L;

    /**
     * Crea una nueva instancia de SummerLeague
     */
    public SummerLeague() {
    }
    
    public SummerLeague(String id, String name, String sport)
            throws IncorrectSportException{
        this.id = id;
        this.name = name;
        if (sport.equalsIgnoreCase("natacion") ||
                sport.equalsIgnoreCase("futbol") ||
                sport.equalsIgnoreCase("basquet") ||
                sport.equalsIgnoreCase("baseball")) {
            this.sport = sport;
        } else {
            throw new IncorrectSportException("''sport'' no es un deporte de verano.");
        }
    }

}
