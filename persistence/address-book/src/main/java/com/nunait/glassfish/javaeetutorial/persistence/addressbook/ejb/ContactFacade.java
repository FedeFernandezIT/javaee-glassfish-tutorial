/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.persistence.addressbook.ejb;

import com.nunait.glassfish.javaeetutorial.persistence.addressbook.entity.Contact;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 26 de mayo de 2017 12:27:40 ART
 */
@Stateless
public class ContactFacade extends AbstractFacade<Contact>{
    
    @PersistenceContext(unitName = "address_bookPU")
    private EntityManager em;
    
    public ContactFacade() {
        super(Contact.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
