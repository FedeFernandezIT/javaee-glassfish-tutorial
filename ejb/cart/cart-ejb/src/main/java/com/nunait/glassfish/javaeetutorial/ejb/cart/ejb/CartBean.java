/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.ejb.cart.ejb;

import com.nunait.glassfish.javaeetutorial.ejb.cart.util.BookException;
import com.nunait.glassfish.javaeetutorial.ejb.cart.util.IdVerifier;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remove;
import javax.ejb.Stateful;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 19 de mayo de 2017 15:30:17 ART
 */
@Stateful
public class CartBean implements Cart {
    
    String customerId;
    String customerName;
    List<String> contents;
    
    @Override
    public void initialize(String person) throws BookException {
        if (person == null) {
            throw new BookException("No está permitido null para person.");
        } else {
            customerName = person;
        }
        customerId = "0";
        contents = new ArrayList<>();
    }

    @Override
    public void initialize(String person, String id) throws BookException {
        if (person == null) {
            throw new BookException("No está permitido null para person.");
        } else {
            customerName = person;
        }
        
        IdVerifier idCheck = new IdVerifier();
        if (idCheck.validate(id)) {
            customerId = id;
        } else {
            throw new BookException("ID inválido: " + id);
        }
        contents = new ArrayList<>();
    }

    @Override
    public void addBook(String title) {
        contents.add(title);
    }

    @Override
    public void removeBook(String title) throws BookException {
        boolean result = contents.remove(title);
        if (result == false) {
            throw new BookException("\"" + title + "\" no se encontró en el carrito.");
        }
    }

    @Override
    public List<String> getContents() {
        return contents;
    }

    @Remove
    @Override
    public void remove() {
        contents = null;
    }
}
