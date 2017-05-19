/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.ejb.counter.web;

import com.nunait.glassfish.javaeetutorial.ejb.counter.ejb.CounterBean;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 19 de mayo de 2017 19:02:24 ART
 */
@Named
@ConversationScoped
public class Count implements Serializable {
    private static final long serialVersionUID = -2190234149507698247L;
    
    @EJB
    private CounterBean counterBean;
    
    private int hitCount;

    public Count() {
        this.hitCount = 0;
    }

    public int getHitCount() {
        hitCount = counterBean.getHits();
        return hitCount;
    }

    public void setHitCount(int newHits) {
        this.hitCount = newHits;
    }                
}
