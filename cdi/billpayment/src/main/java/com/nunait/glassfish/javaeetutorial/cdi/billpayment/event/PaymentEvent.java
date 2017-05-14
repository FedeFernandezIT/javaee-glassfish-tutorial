/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.cdi.billpayment.event;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * Evento payment que maneja los tipos de pagos de Credit y Debit.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 14 de mayo de 2017 12:38:02 ART
 */
public class PaymentEvent implements Serializable {

    private static final long serialVersionUID = 7010735044096431414L;
    
    private String paymentType;
    private BigDecimal value;
    private Date datetime;

    public PaymentEvent() {
    }

    @Override
    public String toString() {
        return paymentType + " = $" + value + " el " + datetime;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
        
}
