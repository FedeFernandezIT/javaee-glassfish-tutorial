/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.checkout;

import java.io.Serializable;
import javax.faces.flow.FlowScoped;
import javax.inject.Named;

/** 
 * Bean de respaldo para CheckoutFlow.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 5 de mayo de 2017 9:39:53 ART
 */
@Named
@FlowScoped("checkoutFlow")
public class CheckoutFlowBean implements Serializable {

    private static final long serialVersionUID = -8431793938024540883L;
    
    private String name;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country = "Argentina";
    private String postalCode;
    private String ccName;
    private String ccNum;
    private String ccExpDate;
    
    public String getReturnValue() {
        return "/index";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCcName() {
        return ccName;
    }

    public void setCcName(String ccName) {
        this.ccName = ccName;
    }

    public String getCcNum() {
        return ccNum;
    }

    public void setCcNum(String ccNum) {
        this.ccNum = ccNum;
    }

    public String getCcExpDate() {
        return ccExpDate;
    }

    public void setCcExpDate(String ccExpDate) {
        this.ccExpDate = ccExpDate;
    }
    
}
