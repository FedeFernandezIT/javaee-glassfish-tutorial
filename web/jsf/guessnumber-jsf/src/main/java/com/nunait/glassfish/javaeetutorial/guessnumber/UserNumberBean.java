/*
 * Copyright 2017 NunaIT.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nunait.glassfish.javaeetutorial.guessnumber;

import java.io.Serializable;
import java.util.Random;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/** 
 * Managed bean class that generate a random number from a
 * minimum to a maximum inclusive.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 26 de abril de 2017 19:07:20 ART
 */
@Named
@SessionScoped
public class UserNumberBean implements Serializable {                    
    private static final long serialVersionUID = 283458515904580519L;
    
    Integer randomInt = null;
    Integer userNumber = null;
    String response = null;
    
    private int maximum = 10;
    private int minimum = 0;

    public UserNumberBean() {
        Random randomGR = new Random();
        randomInt = new Integer(randomGR.nextInt(maximum + 1));
        // Print number to server log.
        System.out.println("[INFO] NunaIT generó el núemro: " +  randomInt);
    }

    public Integer getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(Integer userNumber) {
        this.userNumber = userNumber;
    }

    public String getResponse() {
        if ((userNumber == null) || (userNumber.compareTo(randomInt) != 0)) {
            return "Lo siento, " + userNumber + " es incorrecto ";
        }
        return "Muy bien! Ha acertado!";
    }
    
    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public int getMinimum() {
        return minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }        
            
}
