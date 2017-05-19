/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.ejb.counter.ejb;

import javax.ejb.Singleton;

/** 
 * CounterBean es un simple singleton session bean que registra el
 * números de veces que fue visitada una página web.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 19 de mayo de 2017 18:58:13 ART
 */
@Singleton
public class CounterBean {
    private int hits = 1;
    
    // Incrementa y retorna el número de visitas
    public int getHits() {
        return hits++;
    }
}
