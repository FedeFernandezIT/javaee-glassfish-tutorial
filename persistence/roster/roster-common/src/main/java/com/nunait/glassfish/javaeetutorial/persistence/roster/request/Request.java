/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.persistence.roster.request;

import com.nunait.glassfish.javaeetutorial.persistence.roster.util.LeagueDetails;
import com.nunait.glassfish.javaeetutorial.persistence.roster.util.PlayerDetails;
import com.nunait.glassfish.javaeetutorial.persistence.roster.util.TeamDetails;
import java.util.List;
import javax.ejb.Remote;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 25 de mayo de 2017 10:18:38 ART
 */
@Remote
public interface Request {
    void addPlayer(String playerId, String teamId);
    
    void createLeague(LeagueDetails leagueDetails);
    
    void createPlayer(String id, String name, String position, double salary);
    
    void createTeamInLeague(TeamDetails teamDetails, String leagueId);
    
    void dropPlayer(String playerId, String teamId);
    
    List<PlayerDetails> getAllPlayers();
    
    LeagueDetails getLeague(String leagueId);
    
    List<LeagueDetails> getLeaguesOfPlayer(String playerId);
    
    PlayerDetails getPlayer(String playerId);
    
    List<PlayerDetails> getPlayersByCity(String city);
    
    List<PlayerDetails> getPlayersByHigherSalary(String name);
    
    List<PlayerDetails> getPlayersByLeagueId(String leagueId);
    
    List<PlayerDetails> getPlayersByPosition(String position);
    
    List<PlayerDetails> getPlayersByPositionAndName(String position, String name);
    
    List<PlayerDetails> getPlayersBySalaryRange(double low, double high);
    
    List<PlayerDetails> getPlayersBySport(String sport);
    
    List<PlayerDetails> getPlayersNotOnTeam();
    
    List<PlayerDetails> getPlayersOfTeam(String teamId);
    
    List<String> getSportsOfPlayer(String playerId);
    
    TeamDetails getTeam(String teamId);
    
    List<TeamDetails> getTeamsOfLeague(String leagueId);
    
    void removeLeague(String leagueId);
    
    void removePlayer(String playerId);
    
    void removeTeam(String teamId);
}
