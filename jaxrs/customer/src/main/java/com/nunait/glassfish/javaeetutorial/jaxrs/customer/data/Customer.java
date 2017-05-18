/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jaxrs.customer.data;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 17 de mayo de 2017 20:43:21 ART
 */
@Entity
@Table(name = "CUSTOMER_CUSTOMER")
@NamedQuery(name = "findAllCustomers",
        query = "SELECT c FROM Customer c ORDER BY c.id")
@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer implements Serializable {

    private static final long serialVersionUID = -331436370313062294L;
    private static final Logger logger =
            Logger.getLogger(Customer.class.getCanonicalName());
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlAttribute(required = true)
    protected int id;
    
    @XmlElement(required = true)
    protected String firstName;
    
    @XmlElement(required = true)
    protected String lastName;
    
    @XmlElement(required = true)
    protected Address address;
    
    @XmlElement(required = true)
    protected String email;
    
    @XmlElement(required = true)
    protected String phone;

    public Customer() {
        this.address = new Address();
    }
        
    public int getId() {
        return id;
    }

    public void setId(int id) {
        logger.log(Level.INFO, "setId invocado y establece a {0}", id);
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        logger.log(Level.INFO, "setFirstName invocado y establece a {0}", firstName);
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        logger.log(Level.INFO, "setLastName invocado y establece a {0}", lastName);
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        logger.log(Level.INFO, "setAddress invocado", address);
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        logger.log(Level.INFO, "setEmail invocado y establece a {0}", email);
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        logger.log(Level.INFO, "setPhone invocado y establece a {0}", phone);
        this.phone = phone;
    }
        
}
