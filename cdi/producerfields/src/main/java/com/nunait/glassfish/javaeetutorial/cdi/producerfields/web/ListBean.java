/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.cdi.producerfields.web;

import com.nunait.glassfish.javaeetutorial.cdi.producerfields.ejb.RequestBean;
import com.nunait.glassfish.javaeetutorial.cdi.producerfields.entity.ToDo;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 13 de mayo de 2017 20:11:16 ART
 */
@Named
@ConversationScoped
public class ListBean implements Serializable{

    private static final long serialVersionUID = 448740389029271933L;
    
    @EJB
    private RequestBean request;
    
    @NotNull
    private String inputString;
    
    private ToDo toDo;
    private List<ToDo> toDos;

    public void createTask() {
        this.toDo = request.createToDo(inputString);
    }
    
    public String getInputString() {
        return inputString;
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
    }

    public ToDo getToDo() {
        return toDo;
    }

    public void setToDo(ToDo toDo) {
        this.toDo = toDo;
    }

    public List<ToDo> getToDos() {
        return request.getToDos();
    }

    public void setToDos(List<ToDo> toDos) {
        this.toDos = toDos;
    }
        
}
