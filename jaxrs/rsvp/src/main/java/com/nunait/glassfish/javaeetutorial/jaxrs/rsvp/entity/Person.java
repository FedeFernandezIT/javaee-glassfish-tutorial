/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jaxrs.rsvp.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 16 de mayo de 2017 17:41:43 ART
 */
@XmlRootElement(name = "Person")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Person implements Serializable {

    private static final long serialVersionUID = 5263946441967911677L;
        
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    protected String firstName;
    
    protected String lastName;
    
    @OneToMany(mappedBy = "person")
    @XmlTransient
    private List<Response> responses;
    
    @OneToMany(mappedBy = "owner")
    @XmlTransient
    private List<Event> ownedEvents;
    
    @ManyToMany(mappedBy = "invitees")
    @XmlTransient
    private List<Event> events;

    public Person() {
        this.responses = new ArrayList<>();
        this.ownedEvents = new ArrayList<>();
        this.events = new ArrayList<>();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - este método podría no funcionar en el caso que los
        // campos id no estén configurados
        if (!(object instanceof Person)) {
            return false;
        }
        final Person other = (Person) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }        

    @Override
    public String toString() {
        return "rsvp.entity.Person[id=" + id + "]";
    }
            
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    public List<Event> getOwnedEvents() {
        return ownedEvents;
    }

    public void setOwnedEvents(List<Event> ownedEvents) {
        this.ownedEvents = ownedEvents;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
        
}
