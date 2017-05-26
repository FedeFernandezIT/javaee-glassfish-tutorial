/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.persistence.roster.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 25 de mayo de 2017 11:13:36 ART
 */
@Entity
@Table(name = "PERSISTENCE_ROSTER_TEAM")
public class Team implements Serializable {
    private static final long serialVersionUID = 3642451114126718053L;
    
    private String id;
    private String name;
    private String city;        
    private Collection<Player> players;
    private League league;

    /**
     * Crea una nueva instancia de Team
     */
    public Team() {
    }

    public Team(String id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;        
    }

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @ManyToMany
    @JoinTable(
            name = "PERSISTENCE_ROSTER_TEAM_PLAYER",
            joinColumns = @JoinColumn(name = "TEAM_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "PLAYER_ID", referencedColumnName = "ID")
    )
    public Collection<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Collection<Player> players) {
        this.players = players;
    }

    @ManyToOne
    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public void addPlayer(Player player) {
        this.getPlayers().add(player);
    }
    
    public void dropPlayer(Player player) {
        this.getPlayers().remove(player);
    }
}
