/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.cdi.billpayment.payment;

import com.nunait.glassfish.javaeetutorial.cdi.billpayment.event.PaymentEvent;
import com.nunait.glassfish.javaeetutorial.cdi.billpayment.interceptor.Logged;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Digits;

/** 
 * Bean que dispara los eventos CREDIT y DEBIT según UI selection.
 * Verificar en los registros del servidor, los eventos que fueron
 * manejados.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 14 de mayo de 2017 13:23:05 ART
 */
@Named
@SessionScoped
public class PaymentBean implements Serializable {

    private static final long serialVersionUID = -8840560656417138622L;
    private static final Logger logger =
            Logger.getLogger(PaymentBean.class.getCanonicalName());
    
    @Inject @Credit
    Event<PaymentEvent> creditEvent;
    
    @Inject @Debit
    Event<PaymentEvent> debitEvent;
    
    private static final int DEBIT = 1;
    private static final int CREDIT = 2;
    private int paymentOption = DEBIT;
    
    @Digits(integer = 10, fraction = 2, message = "Valor no válido")
    private BigDecimal value;
    
    private Date datetime;
    
    /**
     * Dispara un evento payment.
     * 
     * @return localización de la respuesta web
     */
    @Logged
    public String pay() {
        this.setDatetime(Calendar.getInstance().getTime());
        switch (paymentOption) {
            case DEBIT:
                PaymentEvent debitPayload = new PaymentEvent();
                debitPayload.setPaymentType("Debit");
                debitPayload.setValue(value);
                debitPayload.setDatetime(datetime);
                debitEvent.fire(debitPayload);
                break;
            case CREDIT:
                PaymentEvent creditPayload = new PaymentEvent();
                creditPayload.setPaymentType("Credit");
                creditPayload.setValue(value);
                creditPayload.setDatetime(datetime);
                creditEvent.fire(creditPayload);
                break;
            default:
                logger.severe("Opción de pago (payment) inválida");
        }
        return "response";
    }
        
    /**
     * Restablece los valores de la página index.
     */
    @Logged
    public void reset() {
        setPaymentOption(DEBIT);
        setValue(BigDecimal.ZERO);
    }

    public int getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(int paymentOption) {
        this.paymentOption = paymentOption;
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
