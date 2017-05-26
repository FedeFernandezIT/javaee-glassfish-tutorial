/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.persistence.roster.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 25 de mayo de 2017 11:40:15 ART
 */
@Entity
@Table(name = "PERSISTENCE_ROSTER_LEAGUE")
public abstract class League implements Serializable {
    private static final long serialVersionUID = 6671059721071118835L;
    
    protected String id;
    protected String name;
    protected String sport;
    protected Collection<Team> teams;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    @OneToMany(mappedBy = "league", cascade = CascadeType.ALL)
    public Collection<Team> getTeams() {
        return teams;
    }

    public void setTeams(Collection<Team> teams) {
        this.teams = teams;
    }
    
    public void addTeam(Team team) {
        this.getTeams().add(team);
    }
    
    public void removeTeam(Team team) {
        this.getTeams().remove(team);
    }
}
