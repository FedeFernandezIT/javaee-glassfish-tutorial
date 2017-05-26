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
 * @created 25 de mayo de 2017 12:37:11 ART
 */
@Entity
public class WinterLeague extends League implements Serializable {    
    private static final long serialVersionUID = 3217861569241563188L;

    /**
     * Crea una nueva instancia de WinterLeague
     */
    public WinterLeague() {
    }
    
    public WinterLeague(String id, String name, String sport)
            throws IncorrectSportException {
        this.id = id;
        this.name = name;
        if (sport.equalsIgnoreCase("hockey") ||
                sport.equalsIgnoreCase("esqui") ||
                sport.equalsIgnoreCase("snowboard")) {
            this.sport = sport;
        } else {
            throw new IncorrectSportException("''sport'' no es un deporte de invierno.");
        }
    }
    
}
