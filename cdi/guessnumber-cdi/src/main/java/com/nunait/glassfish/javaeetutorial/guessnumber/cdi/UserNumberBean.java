/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.guessnumber.cdi;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 13 de mayo de 2017 10:41:06 ART
 */
@Named
@SessionScoped
public class UserNumberBean implements Serializable {

    private static final long serialVersionUID = 6939341097803122986L;
    
    private int number;
    private int minimum;
    private int maximum;
    private int remainingGuesses;
    
    private Integer userNumber;
    
    @Inject @MaxNumber private int maxNumber;
    @Inject @Random private Instance<Integer> randomInt;

    public UserNumberBean() {
    }

    public int getNumber() {
        return number;
    }
    
    public int getMinimum() {
        return minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public Integer getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(Integer userNumber) {
        this.userNumber = userNumber;
    }

    public int getRemainingGuesses() {
        return remainingGuesses;
    }
    
    public String check() throws InterruptedException {
        if (userNumber > number) {
            maximum = userNumber - 1;
        }
        if (userNumber < number) {
            minimum = userNumber + 1;
        }
        if (userNumber == number) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Correcto!!!"));
        }
        remainingGuesses--;
        return null;
    }
    
    @PostConstruct
    public void reset() {
        this.minimum = 0;
        this.maximum = maxNumber;
        this.remainingGuesses = 10;
        this.number = randomInt.get();
    }
    
    public void validateNumberRange(FacesContext context, 
            UIComponent toValidate, Object value) {
        int input = (Integer) value;
        
        if (minimum > input || input > maximum) {
            ((UIInput) toValidate).setValid(false);
            
            FacesMessage message = new FacesMessage("Ingreso inválido");
            context.addMessage(toValidate.getClientId(context), message);
        }
    }

}
