/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.cdi.decorators;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 14 de mayo de 2017 21:53:00 ART
 */
@Decorator
public abstract class CoderDecorator implements Coder {
    
    @Inject @Any
    @Delegate
    Coder coder;
    
    @Override
    public String codeString(String s, int tval) {
        int len = s.length();
        
        return "\"" + s + "\" se transforma en \"" + coder.codeString(s, tval)
                + "\", " + len + " caracteres de longitud.";
    }
        
}
