/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.cdi.producerfields.ejb;

import com.nunait.glassfish.javaeetutorial.cdi.producerfields.db.UserDatabase;
import com.nunait.glassfish.javaeetutorial.cdi.producerfields.entity.ToDo;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 13 de mayo de 2017 19:54:10 ART
 */
@ConversationScoped
@Stateful
public class RequestBean {
    
    @Inject
    @UserDatabase
    EntityManager em;
    
    public ToDo createToDo(String task) {
        ToDo toDo;
        Date currentTime = Calendar.getInstance().getTime();
        
        try {
            toDo = new ToDo();
            toDo.setTaskText(task);
            toDo.setTimeCreated(currentTime);
            em.persist(toDo);
            return toDo;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<ToDo> getToDos() {
        try {
            List<ToDo> toDos = (List<ToDo>) em.createQuery(
                    "SELECT t FROM ToDo t ORDER BY t.timeCreated")
                    .getResultList();
            return toDos;
        } catch(Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
