/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jaxrs.rsvp.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 16 de mayo de 2017 17:53:47 ART
 */
@NamedQuery(name = "rsvp.entity.Event.getAllUpcomingEvent",
        query = "SELECT e FROM Event e")
@XmlRootElement(name = "Event")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Event implements Serializable{
    
    private static final long serialVersionUID = -7831286827167316160L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    protected String name;
    
    @ManyToOne
    private Person owner;
    
    protected String location;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date eventDate;
    
    @ManyToMany
    protected List<Person> invitees;
    
    @OneToMany(mappedBy = "event")
    private List<Response> responses;

    public Event() {
        this.invitees = new ArrayList<>();
        this.responses = new ArrayList<>();
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
        if (!(object instanceof Event)) {
            return false;
        }
        final Event other = (Event) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }        

    @Override
    public String toString() {
        return "rsvp.entity.Event[id=" + id + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public List<Person> getInvitees() {
        return invitees;
    }

    public void setInvitees(List<Person> invitees) {
        this.invitees = invitees;
    }

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }        

}
