/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.batch.webserverlog;

import java.io.Serializable;

/** 
 * Class for checkpoint objects.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 20 de junio de 2017 16:24:57 ART
 */
public class ItemNumberCheckpoint implements Serializable{
    private static final long serialVersionUID = -2518447425577431215L;
    
    private long lineNume;

    public ItemNumberCheckpoint() {
        lineNume = 0;
    }        

    public long getLineNum() {
        return lineNume;
    }

    public void nextLine() {
        lineNume++;
    }

}
