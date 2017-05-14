/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.cdi.producerfields.db;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 13 de mayo de 2017 19:04:29 ART
 */
@Singleton
public class UserDatabaseEntityManager {
    
    // declares a producer field
    @Produces @UserDatabase
    @PersistenceContext
    private EntityManager em;
    
    // use methods to create and dispose of a producer field
  /*@PersistenceContext
    private EntityManager em;
    
    @Produces @UserDatabase
    public EntityManager create() {
        return em;
    }
    
    public void close(@Disposes @UserDatabase EntityManager em) {
        em.close();
    }*/
}
