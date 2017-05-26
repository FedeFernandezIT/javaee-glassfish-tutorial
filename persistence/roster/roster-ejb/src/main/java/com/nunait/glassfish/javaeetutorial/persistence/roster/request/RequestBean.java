/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.persistence.roster.request;

import com.nunait.glassfish.javaeetutorial.persistence.roster.entity.League;
import com.nunait.glassfish.javaeetutorial.persistence.roster.entity.League_;
import com.nunait.glassfish.javaeetutorial.persistence.roster.entity.Player;
import com.nunait.glassfish.javaeetutorial.persistence.roster.entity.Player_;
import com.nunait.glassfish.javaeetutorial.persistence.roster.entity.SummerLeague;
import com.nunait.glassfish.javaeetutorial.persistence.roster.entity.Team;
import com.nunait.glassfish.javaeetutorial.persistence.roster.entity.Team_;
import com.nunait.glassfish.javaeetutorial.persistence.roster.entity.WinterLeague;
import com.nunait.glassfish.javaeetutorial.persistence.roster.util.IncorrectSportException;
import com.nunait.glassfish.javaeetutorial.persistence.roster.util.LeagueDetails;
import com.nunait.glassfish.javaeetutorial.persistence.roster.util.PlayerDetails;
import com.nunait.glassfish.javaeetutorial.persistence.roster.util.TeamDetails;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CollectionJoin;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 25 de mayo de 2017 12:45:33 ART
 */
@Stateful
public class RequestBean implements Request, Serializable {
    private static final long serialVersionUID = -8549015074632632647L;
    private static final Logger logger =
            Logger.getLogger(RequestBean.class.getCanonicalName());
    
    @PersistenceContext
    private EntityManager em;
    private CriteriaBuilder cb;
    
    @PostConstruct
    private void init() {
        cb = em.getCriteriaBuilder();
    }

    @Override
    public void addPlayer(String playerId, String teamId) {
        logger.info("addPlayer");
        try {
            Player player = em.find(Player.class, playerId);
            Team team = em.find(Team.class, teamId);
            
            team.addPlayer(player);
            player.addTeam(team);
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Override
    public void createLeague(LeagueDetails leagueDetails) {
        logger.info("createLeague");        
                
        try {
            if (leagueDetails.getSport().equalsIgnoreCase("natacion") ||
                    leagueDetails.getSport().equalsIgnoreCase("futbol") ||
                    leagueDetails.getSport().equalsIgnoreCase("basquet") ||
                    leagueDetails.getSport().equalsIgnoreCase("baseball")) {
                SummerLeague league = new SummerLeague(
                        leagueDetails.getId(),
                        leagueDetails.getName(),
                        leagueDetails.getSport());
                em.persist(league);
            } else if (leagueDetails.getSport().equalsIgnoreCase("hockey") ||
                    leagueDetails.getSport().equalsIgnoreCase("esqui") ||
                    leagueDetails.getSport().equalsIgnoreCase("snowboard")) {
                WinterLeague league = new WinterLeague(
                        leagueDetails.getId(),
                        leagueDetails.getName(),
                        leagueDetails.getSport());
                em.persist(league);
            } else {
                throw new IncorrectSportException("El deporte especificado no es válido.");
            }
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Override
    public void createPlayer(String id, String name, String position, double salary) {
        logger.info("createPlayer");
        try {
            Player player = new Player(id, name, position, salary);
            em.persist(player);
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Override
    public void createTeamInLeague(TeamDetails teamDetails, String leagueId) {
        logger.info("createTeamInLeague");
        
        try {
            League league = em.find(League.class, leagueId);
            Team team = new Team(teamDetails.getId(), teamDetails.getName(), teamDetails.getCity());
            em.persist(team);
            team.setLeague(league);
            league.getTeams().add(team);
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Override
    public void dropPlayer(String playerId, String teamId) {
        logger.info("dropPlayer");
        try {
            Player player = em.find(Player.class, playerId);
            Team team = em.find(Team.class, teamId);

            team.dropPlayer(player);
            player.dropTeam(team);
        } catch (Exception e) {
            throw new EJBException(e);
        }        
    }

    @Override
    public List<PlayerDetails> getAllPlayers() {
        logger.info("getAllPlayers");
        List<Player> players = null;
        
        try {
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            if (cq != null) {
                Root<Player> player = cq.from(Player.class);
                
                cq.select(player);
                TypedQuery<Player> q = em.createQuery(cq);
                players = q.getResultList();
            }
            return this.copyPlayersToDetails(players);
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Override
    public LeagueDetails getLeague(String leagueId) {
        logger.info("getLeague");
        LeagueDetails leagueDetails = null;
        
        try {
            League league = em.find(League.class, leagueId);
            leagueDetails = new LeagueDetails(league.getId(), league.getName(), league.getSport());
        } catch (Exception e) {
            throw new EJBException(e);
        }
        return leagueDetails;
    }

    @Override
    public List<LeagueDetails> getLeaguesOfPlayer(String playerId) {
        logger.info("getLeaguesOfPlayer");
        List<LeagueDetails> detailsList = new ArrayList<>();
        List<League> leagues = null;
                
        try {
            CriteriaQuery<League> cq = cb.createQuery(League.class);
            if (cq != null) {
                Root<League> league = cq.from(League.class);
                
                // recuperar MetaModel desde Root
                //EntityType<League> League_ = league.getModel();
                Join<League, Team> team = league.join(League_.teams);
                Join<Team, Player> player = team.join(Team_.players);
                
                cq.where(cb.equal(player.get(Player_.id), playerId));
                cq.select(league).distinct(true);
                TypedQuery<League> q = em.createQuery(cq);
                leagues = q.getResultList();
            }            
        } catch (Exception e) {
            throw new EJBException(e);
        }
        
        if (leagues == null) {
            logger.log(Level.WARNING, "No se encontraron ligas para el jugador con ID {0}.", playerId);
            return null;
        } else {
            Iterator<League> it = leagues.iterator();
            while (it.hasNext()) {
                League league = it.next();
                LeagueDetails leagueDetails = new LeagueDetails(
                        league.getId(), league.getName(), league.getSport());
                detailsList.add(leagueDetails);
            }
        }
        return detailsList;
    }

    @Override
    public PlayerDetails getPlayer(String playerId) {
        logger.info("getPlayerDetails");
        try {
            Player player = em.find(Player.class, playerId);
            PlayerDetails playerDetails = new PlayerDetails(
                    player.getId(), player.getName(), player.getPosition(), player.getSalary());
            return playerDetails;
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Override
    public List<PlayerDetails> getPlayersByCity(String city) {
        logger.info("getPlayersByCity");
        List<Player> players = null;
        
        try {
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            if (cq != null) {
                Root<Player> player = cq.from(Player.class);
                Join<Player, Team> team = player.join(Player_.teams);
                
                // recuperar MetaModel desde Root
                //EntityType<Player> Player_ = player.getModel();
                
                // establecer la cláusula WHERE
                cq.where(cb.equal(team.get(Team_.city), city));
                cq.select(player).distinct(true);
                TypedQuery<Player> q = em.createQuery(cq);
                players = q.getResultList();
            }
            return this.copyPlayersToDetails(players);
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Override
    public List<PlayerDetails> getPlayersByHigherSalary(String name) {
        logger.info("getPlayersByHigherSalary");
        List<Player> players = null;
        
        try {
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            if (cq != null) {
                Root<Player> player1 = cq.from(Player.class);
                Root<Player> player2 = cq.from(Player.class);
                
                // Recuperar MetaModel desde Root
                // EntityType<Player> Player_ = player1.getModel();
                
                // crea un objeto Predicate que encuentra jugadores con
                // un salario mayor que el de player1
                Predicate gtPredicate = cb.gt(
                        player1.get(Player_.salary),
                        player2.get(Player_.salary));
                
                // crea un objeto Predicate que encuentra un jugador
                // basado en el parámetro 'name'
                Predicate equalPredicate = cb.equal(
                        player2.get(Player_.name),
                        name);
                
                // establece la cláusula WHERE con los predicates
                cq.where(gtPredicate, equalPredicate);
                
                // establece la cláusula SELECT y retorna las entradas únicas
                cq.select(player1).distinct(true);
                TypedQuery<Player> q = em.createQuery(cq);
                players = q.getResultList();
            }
            return this.copyPlayersToDetails(players);
        } catch (Exception e) {
            throw new EJBException(e);
        }        
    }

    @Override
    public List<PlayerDetails> getPlayersByLeagueId(String leagueId) {
        logger.info("getPlayersByLeagueId");
        List<Player> players = null;
        
        try {
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            if (cq != null) {
                Root<Player> player = cq.from(Player.class);                
                Join<Player, Team> team = player.join(Player_.teams);
                Join<Team, League> league = team.join(Team_.league);
                
                // recuperar MetaModel desde Root
                //EntityType<Player> Player_ = player.getModel();
                
                // establecer la cláusula WHERE
                cq.where(cb.equal(league.get(League_.id), leagueId));
                cq.select(player).distinct(true);
                TypedQuery<Player> q = em.createQuery(cq);
                players = q.getResultList();                
            }
            return this.copyPlayersToDetails(players);
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Override
    public List<PlayerDetails> getPlayersByPosition(String position) {
        logger.info("getPlayersByPosition");
        List<Player> players = null;
        
        try {
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            if (cq != null) {
                Root<Player> player = cq.from(Player.class);

                // Recupera MetaModel desde Root
                // EntityType<Player> Player_ = player.getModel();
                
                // Establecer la cláusula WHERE
                cq.where(cb.equal(player.get(Player_.position), position));
                cq.select(player);
                TypedQuery<Player> q = em.createQuery(cq);
                players = q.getResultList();
            }
            return this.copyPlayersToDetails(players);
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Override
    public List<PlayerDetails> getPlayersByPositionAndName(String position, String name) {
        logger.info("getPlayersByPositionAndName");
        List<Player> players = null;
                
        try {
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            if (cq != null) {
                Root<Player> player = cq.from(Player.class);
                
                // recuperar MetaModel desde Root
                //EntityType<Player> Player_ = player.getModel();
                
                // establecer cláusula WHERE
                cq.where(cb.equal(player.get(Player_.position), position),
                         cb.equal(player.get(Player_.name), name));
                cq.select(player).distinct(true);
                TypedQuery<Player> q = em.createQuery(cq);
                players = q.getResultList();
            }
            return this.copyPlayersToDetails(players);
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Override
    public List<PlayerDetails> getPlayersBySalaryRange(double low, double high) {
        logger.info("getPlayersBySalaryRange");
        List<Player> players = null;
        
        try {
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            if (cq != null) {
                Root<Player> player = cq.from(Player.class);
                
                // recupera MetaModel desde Root
                //EntityType<Player> Player_ = player.getModel();
                
                // establece la cláusula WHERE
                cq.where(cb.between(player.get(Player_.salary), low, high));
                
                // establece la cláusula SELECT
                cq.select(player).distinct(true);
                TypedQuery<Player> q = em.createQuery(cq);
                players = q.getResultList();
            }
            return this.copyPlayersToDetails(players);
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Override
    public List<PlayerDetails> getPlayersBySport(String sport) {
        logger.info("getPlayersBySport");
        List<Player> players = null;
        
        try {
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            if (cq != null) {
                Root<Player> player = cq.from(Player.class);
                Join<Player, Team> team = player.join(Player_.teams);
                Join<Team, League> league = team.join(Team_.league);
                
                // recuperar MetaModel desde Root
                //EntityType<Player> Player_ = player.getModel();
                
                // establecer la cáusula WHERE
                cq.where(cb.equal(league.get(League_.sport), sport));
                cq.select(player).distinct(true);
                TypedQuery<Player> q = em.createQuery(cq);
                players = q.getResultList();
            }
            return this.copyPlayersToDetails(players);
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Override
    public List<PlayerDetails> getPlayersNotOnTeam() {
        logger.info("getPlayersNotOnTeam");
        List<Player> players = null;
                
        try {
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            if (cq != null) {
                Root<Player> player = cq.from(Player.class);
                
                // recuperar MetaModel desde Root
                //EntityType<Player> Player_ = player.getModel();
                
                // establecer la cláusula WHERE
                cq.where(cb.isEmpty(player.get(Player_.teams)));
                cq.select(player).distinct(true);
                TypedQuery<Player> q = em.createQuery(cq);
                players = q.getResultList();
            }
            return this.copyPlayersToDetails(players);
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Override
    public List<PlayerDetails> getPlayersOfTeam(String teamId) {
        logger.info("getPlayersOfTeam");
        List<PlayerDetails> playerList = null;
        try {
            Team team = em.find(Team.class, teamId);
            playerList = this.copyPlayersToDetails((List<Player>) team.getPlayers());
        } catch (Exception e) {
            throw new EJBException(e);
        }
        return playerList;
    }

    @Override
    public List<String> getSportsOfPlayer(String playerId) {
        logger.info("getSportsOfPlayer");
        List<String> sports = null;
                
        try {
            CriteriaQuery<String> cq = cb.createQuery(String.class);
            if (cq != null) {
                Root<Player> player = cq.from(Player.class);
                Join<Player, Team> team = player.join(Player_.teams);
                Join<Team, League> league = team.join(Team_.league);
                
                // establecer la clásula WHERE
                cq.where(cb.equal(player.get(Player_.id), playerId));
                cq.select(league.get(League_.sport)).distinct(true);
                TypedQuery<String> q = em.createQuery(cq);
                sports = q.getResultList();
            }            
        } catch (Exception e) {
            throw new EJBException(e);
        }
        return sports;
    }

    @Override
    public TeamDetails getTeam(String teamId) {
        logger.info("getTeam");
        TeamDetails teamDetails = null;
                
        try {
            Team team = em.find(Team.class, teamId);
            teamDetails = new TeamDetails(team.getId(), team.getName(), team.getCity());
        } catch (Exception e) {
            throw new EJBException(e);
        }
        return teamDetails;
    }

    @Override
    public List<TeamDetails> getTeamsOfLeague(String leagueId) {
        logger.info("getTeamsOfLeague");
        List<TeamDetails> detailsList = new ArrayList<>();
        Collection<Team> teams = null;
        
        try {
            League league = em.find(League.class, leagueId);
            teams = league.getTeams();
        } catch (Exception e) {
            throw new EJBException(e);
        }
        
        Iterator<Team> it = teams.iterator();
        while (it.hasNext()) {
            Team team = it.next();
            TeamDetails teamDetails = new TeamDetails(team.getId(), team.getName(), team.getCity());
            detailsList.add(teamDetails);
        }
        return detailsList;
    }

    @Override
    public void removeLeague(String leagueId) {
        logger.info("removeLeague");
        
        try {
            League league = em.find(League.class, leagueId);
            em.remove(league);
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Override
    public void removePlayer(String playerId) {
        logger.info("removePlayer");
        try {
            Player player = em.find(Player.class, playerId);
            
            Collection<Team> teams = player.getTeams();
            Iterator<Team> it = teams.iterator();
            while (it.hasNext()) {
                Team team = it.next();
                team.dropPlayer(player);
            }
            em.remove(player);
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Override
    public void removeTeam(String teamId) {
        logger.info("removeTeam");
                        
        try {
            Team team = em.find(Team.class, teamId);
            
            Collection<Player> players = team.getPlayers();
            Iterator<Player> it = players.iterator();
            while (it.hasNext()) {
                Player player = it.next();
                player.dropTeam(team);
            }
            
            em.remove(team);
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    private List<PlayerDetails> copyPlayersToDetails(List<Player> players) {
        List<PlayerDetails> detailsList = new ArrayList<>();
        Iterator<Player> it = players.iterator();
        while (it.hasNext()) {
            Player player = it.next();
            PlayerDetails playerDetails = new PlayerDetails(
                    player.getId(), player.getName(), player.getPosition(), player.getSalary());
            detailsList.add(playerDetails);
        }
        return detailsList;
    }

}
