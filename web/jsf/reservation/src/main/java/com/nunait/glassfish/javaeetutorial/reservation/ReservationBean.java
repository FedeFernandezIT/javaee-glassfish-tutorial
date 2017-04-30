/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.reservation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 28 de abril de 2017 17:50:27 ART
 */
@Named
@SessionScoped
public class ReservationBean implements Serializable {
    
    private static final long serialVersionUID = 964672935029668534L;
    
    private String name = "";
    private String totalValue = "120.00";
    private String email = "";
    private String emailAgain = "";
    private String date = "";
    private String tickets = "1";
    private String price = "120";
    private Map<String, Object> ticketAttrs;

    public ReservationBean() {
        ticketAttrs = new HashMap<>();
        ticketAttrs.put("type", "number");
        ticketAttrs.put("min", "1");
        ticketAttrs.put("max", "4");
        ticketAttrs.put("required", "required");
        ticketAttrs.put("title", "Ingrese un número entre 1 y 4 incluído.");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(String totalValue) {
        this.totalValue = totalValue;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailAgain() {
        return emailAgain;
    }

    public void setEmailAgain(String emailAgain) {
        this.emailAgain = emailAgain;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTickets() {
        return tickets;
    }

    public void setTickets(String tickets) {
        this.tickets = tickets;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Map<String, Object> getTicketAttrs() {
        return ticketAttrs;
    }

    public void setTicketAttrs(Map<String, Object> ticketAttrs) {
        this.ticketAttrs = ticketAttrs;
    }
    
    public void CalculateTotal(AjaxBehaviorEvent event)
            throws AbortProcessingException {
        int ticketNum = 1;
        int ticketPrice = 0;
        int total;
        
        if (tickets.trim().length() > 0) {
            ticketNum = Integer.parseInt(tickets);
        }
        if (price.trim().length() > 0) {
            ticketPrice = Integer.parseInt(price);
        }
        total = ticketNum * ticketPrice;
        totalValue = String.valueOf(total) + ".00";
    }
    
    public void Clear(AjaxBehaviorEvent event)
            throws AbortProcessingException {
        name = "";
        email = "";
        emailAgain = "";
        date = "";
        price = "120.00";
        totalValue = "120.00";                
        tickets = "1";        
    }
}
