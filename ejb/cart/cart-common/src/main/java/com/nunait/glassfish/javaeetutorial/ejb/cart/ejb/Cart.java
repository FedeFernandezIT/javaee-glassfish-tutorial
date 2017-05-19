/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.ejb.cart.ejb;

import com.nunait.glassfish.javaeetutorial.ejb.cart.util.BookException;
import java.util.List;
import javax.ejb.Remote;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 19 de mayo de 2017 14:58:37 ART
 */
@Remote
public interface Cart {
    public void initialize(String person) throws BookException;
    
    public void initialize(String person, String id) throws BookException;
    
    public void addBook(String title);
    
    public void removeBook(String title) throws BookException;
    
    public List<String> getContents();
    
    public void remove();
}
