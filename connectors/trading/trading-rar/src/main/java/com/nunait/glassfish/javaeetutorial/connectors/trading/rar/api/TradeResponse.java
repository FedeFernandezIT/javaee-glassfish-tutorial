/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.connectors.trading.rar.api;

/** 
 * Represents the response to a trade from the EIS
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de junio de 2017 13:00:10 ART
 */
public class TradeResponse {
    
    public enum Status {EXECUTED, FAILED};
    
    private Status status;
    private int orderNumber;
    private double total;
    private double fee;

    public TradeResponse() {
    }
    
    public TradeResponse(String response) {
        String[] words = response.split(" ");
        if (words[0].compareTo("EXECUTED") == 0)
            status = Status.EXECUTED;
        else 
            status = Status.FAILED;
        orderNumber = Integer.parseInt(words[1].substring(1));        
        total = Double.parseDouble(words[3].replace(',', '.'));
        fee = Double.parseDouble(words[5].replace(',', '.'));
    }

    @Override
    public String toString() {
        return String.format("EXECUTED #%d TOTAL %.2f FEE %.2f",
                orderNumber, total, fee);
    }
    
    /* Getters and setters */
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public int getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    public double getFee() {
        return fee;
    }
    public void setFee(double fee) {
        this.fee = fee;
    }   
}
