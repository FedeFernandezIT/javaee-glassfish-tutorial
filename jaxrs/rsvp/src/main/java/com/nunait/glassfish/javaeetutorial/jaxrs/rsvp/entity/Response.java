/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jaxrs.rsvp.entity;

import com.nunait.glassfish.javaeetutorial.jaxrs.rsvp.util.ResponseEnum;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 16 de mayo de 2017 17:52:23 ART
 */
@NamedQuery(name = "rsvp.entity.Response.findResponseByEventAndPerson",
        query = "SELECT r FROM Response r " +
                "JOIN r.event e " + 
                "JOIN r.person p " +
                "WHERE e.id = :eventId AND p.id = :personId")
@XmlRootElement(name = "Response")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Response implements Serializable {

    private static final long serialVersionUID = 5894067464249337822L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;    
    
    @ManyToOne
    @XmlTransient
    private Event event;
    
    @ManyToOne
    private Person person;
    
    @Enumerated(EnumType.STRING)
    private ResponseEnum response;

    public Response() {
        this.response = ResponseEnum.NOT_RESPONDED;
    }

    public Response(Event event, Person person, ResponseEnum response) {        
        this.event = event;
        this.person = person;
        this.response = response;
    }

    public Response(Event event, Person person) {    
        this.event = event;
        this.person = person;
        this.response = ResponseEnum.NOT_RESPONDED;
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
        if (!(object instanceof Response)) {
            return false;
        }
        final Response other = (Response) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }        

    @Override
    public String toString() {
        return "rsvp.entity.Response[id=" + id + "]";
    }
        
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public ResponseEnum getResponse() {
        return response;
    }

    public void setResponse(ResponseEnum response) {
        this.response = response;
    }
    
    public String getResponseText() {
        return response.getLabel();
    }
}
