package com.nunait.glassfish.javaeetutorial.persistence.roster.client;

import com.nunait.glassfish.javaeetutorial.persistence.roster.request.Request;
import com.nunait.glassfish.javaeetutorial.persistence.roster.util.LeagueDetails;
import com.nunait.glassfish.javaeetutorial.persistence.roster.util.PlayerDetails;
import com.nunait.glassfish.javaeetutorial.persistence.roster.util.TeamDetails;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;

/**
 * Enterprise Application Client main class.
 *
 */
public class RosterClient {
    
    @EJB
    private static Request request;

    public RosterClient(String[] args) {
    }
    
    
    public static void main( String[] args ) {
        RosterClient client = new RosterClient(args);
        try {
            client.insertInfo();
            client.getSomeInfo();
            client.getMoreInfo();
            client.removeInfo();
        } catch (Exception e) {
            System.err.println("El sistema atrapó una excepción.");
        }
    }

    private void insertInfo() {
        try {
            // Leagues
            request.createLeague(new LeagueDetails("L1", "Montaña", "Futbol"));
            request.createLeague(new LeagueDetails("L2", "Valle", "Basquet"));
            request.createLeague(new LeagueDetails("L3", "Colina", "Futbol"));
            request.createLeague(new LeagueDetails("L4", "Alpino", "Snowboard"));
            
            // Teams
            request.createTeamInLeague(new TeamDetails("T1", "Honey Bees", "Visalia"),
                    "L1");
            request.createTeamInLeague(new TeamDetails("T2", "Gophers", "Manteca"),
                    "L1");
            request.createTeamInLeague(new TeamDetails("T5", "Crows", "Orland"),
                    "L1");
            
            request.createTeamInLeague(new TeamDetails("T3", "Deer", "Bodie"),
                    "L2");
            request.createTeamInLeague(new TeamDetails("T4", "Trout", "Truckee"),
                    "L2");
            
            request.createTeamInLeague(new TeamDetails("T6", "Marmots", "Auburn"),
                    "L3");
            request.createTeamInLeague(new TeamDetails("T7", "Bobcats", "Grass Valley"), 
                    "L3");
            request.createTeamInLeague(new TeamDetails("T8", "Beavers", "Placerville"), 
                    "L3");
            
            request.createTeamInLeague(new TeamDetails("T9", "Penguins", "Incline Village"),
                    "L4");
            request.createTeamInLeague(new TeamDetails("T10", "Land Otters", "Tahoe City"),
                    "L4");
            
            // Players, Team T1
            request.createPlayer("P1", "Phil Jones", "goalkeeper", 100.00);
            request.addPlayer("P1", "T1");
            
            request.createPlayer("P2", "Alice Smith", "defender", 505.00);
            request.addPlayer("P2", "T1");

            request.createPlayer("P3", "Bob Roberts", "midfielder", 65.00);
            request.addPlayer("P3", "T1");

            request.createPlayer("P4", "Grace Phillips", "forward", 100.00);
            request.addPlayer("P4", "T1");

            request.createPlayer("P5", "Barney Bold", "defender", 100.00);
            request.addPlayer("P5", "T1");

            // Players, Team T2
            request.createPlayer("P6", "Ian Carlyle", "goalkeeper", 555.00);
            request.addPlayer("P6", "T2");

            request.createPlayer("P7", "Rebecca Struthers", "midfielder", 777.00);
            request.addPlayer("P7", "T2");

            request.createPlayer("P8", "Anne Anderson", "forward", 65.00);
            request.addPlayer("P8", "T2");

            request.createPlayer("P9", "Jan Wesley", "defender", 100.00);
            request.addPlayer("P9", "T2");

            request.createPlayer("P10", "Terry Smithson", "midfielder", 100.00);
            request.addPlayer("P10", "T2");

            // Players, Team T3
            request.createPlayer("P11", "Ben Shore", "point guard", 188.00);
            request.addPlayer("P11", "T3");

            request.createPlayer("P12", "Chris Farley", "shooting guard", 577.00);
            request.addPlayer("P12", "T3");

            request.createPlayer("P13", "Audrey Brown", "small forward", 995.00);
            request.addPlayer("P13", "T3");

            request.createPlayer("P14", "Jack Patterson", "power forward", 100.00);
            request.addPlayer("P14", "T3");

            request.createPlayer("P15", "Candace Lewis", "point guard", 100.00);
            request.addPlayer("P15", "T3");

            // Players, Team T4
            request.createPlayer("P16", "Linda Berringer", "point guard", 844.00);
            request.addPlayer("P16", "T4");

            request.createPlayer("P17", "Bertrand Morris", "shooting guard", 452.00);
            request.addPlayer("P17", "T4");

            request.createPlayer("P18", "Nancy White", "small forward", 833.00);
            request.addPlayer("P18", "T4");

            request.createPlayer("P19", "Billy Black", "power forward", 444.00);
            request.addPlayer("P19", "T4");

            request.createPlayer("P20", "Jodie James", "point guard", 100.00);
            request.addPlayer("P20", "T4");

            // Players, Team T5
            request.createPlayer("P21", "Henry Shute", "goalkeeper", 205.00);
            request.addPlayer("P21", "T5");

            request.createPlayer("P22", "Janice Walker", "defender", 857.00);
            request.addPlayer("P22", "T5");

            request.createPlayer("P23", "Wally Hendricks", "midfielder", 748.00);
            request.addPlayer("P23", "T5");

            request.createPlayer("P24", "Gloria Garber", "forward", 777.00);
            request.addPlayer("P24", "T5");

            request.createPlayer("P25", "Frank Fletcher", "defender", 399.00);
            request.addPlayer("P25", "T5");

            // Players, Team T9
            request.createPlayer("P30", "Lakshme Singh", "downhill", 450.00);
            request.addPlayer("P30","T9");
            
            request.createPlayer("P31", "Mariela Prieto", "freestyle", 420.00);
            request.addPlayer("P31", "T9");
            
            // Players, Team T10
            request.createPlayer("P32", "Soren Johannsen", "freestyle", 375.00);
            request.addPlayer("P32", "T10");
            
            request.createPlayer("P33", "Andre Gerson", "freestyle", 396.00);
            request.addPlayer("P33", "T10");
            
            request.createPlayer("P34", "Zoria Lepsius", "downhill", 431.00);
            request.addPlayer("P34", "T10");
            
            // Players, no team
            request.createPlayer("P26", "Hobie Jackson",
                    "pitcher", 582.00);
            request.createPlayer("P27", "Melinda Kendall",
                    "catcher", 677.00);

            // Players, multiple teams
            request.createPlayer("P28", "Constance Adams",
                    "substitute", 966.00);
            request.addPlayer("P28", "T1");
            request.addPlayer("P28", "T3");

            // Adding existing players to second soccer league
            request.addPlayer("P24", "T6");
            request.addPlayer("P21", "T6");
            request.addPlayer("P9", "T6");
            request.addPlayer("P7", "T5");
        
        } catch (Exception e) {
            System.err.println("El sistema atrapó una excepción.");
        }
    }

    private void getSomeInfo() {
        try {
            List<PlayerDetails> playerList;
            List<TeamDetails> teamList;
            List<LeagueDetails> leagueList;
            
            System.out.println("Lista de jugadores del equipo T2:");
            playerList = request.getPlayersOfTeam("T2");
            printDetailsList(playerList);
            System.out.println();
            
            System.out.println("Lista de equipos de la liga L1:");
            teamList = request.getTeamsOfLeague("L1");
            printDetailsList(teamList);
            System.out.println();
            
            System.out.println("Lista de todos los defensores:");
            playerList = request.getPlayersByPosition("defender");
            printDetailsList(playerList);
            System.out.println();
            
            System.out.println("Lista de las ligas en las que compite el jugador P28:");
            leagueList = request.getLeaguesOfPlayer("P28");
            printDetailsList(leagueList);
            System.out.println();
            
        } catch (Exception e) {
            System.err.println("El sistema atrapó una excepción.");
        }
    }

    private void getMoreInfo() {
        try {
            LeagueDetails leagueDetails;
            TeamDetails teamDetails;
            PlayerDetails playerDetails;
            
            List<PlayerDetails> playerList;
            List<TeamDetails> teamList;
            List<LeagueDetails> leagueList;
            List<String> sportList;
            
            System.out.println("Detalles de la liga L1:");
            leagueDetails = request.getLeague("L1");
            System.out.println(leagueDetails.toString());
            System.out.println();
            
            System.out.println("Detalles del equipo T3:");
            teamDetails = request.getTeam("T3");
            System.out.println(teamDetails.toString());
            System.out.println();
            
            System.out.println("Detalles del jugador P20:");
            playerDetails = request.getPlayer("P20");
            System.out.println(playerDetails.toString());
            System.out.println();
            
            System.out.println("Lista de los equipos en la liga L3:");
            teamList = request.getTeamsOfLeague("L3");
            printDetailsList(teamList);
            System.out.println();
            
            System.out.println("Lista de todos los jugadores:");
            playerList = request.getAllPlayers();
            printDetailsList(playerList);
            System.out.println();
            
            System.out.println("Lista de los jugadores sin equipo:");
            playerList = request.getPlayersNotOnTeam();
            printDetailsList(playerList);
            System.out.println();
            
            System.out.println("Detalles de Jack Patterson, un poderoso delantero:");
            playerList = request.getPlayersByPositionAndName("power forward", "Jack Patterson");
            printDetailsList(playerList);
            System.out.println();
            
            System.out.println("Lista de los jugadores de la ciudad de Truckee:");
            playerList = request.getPlayersByCity("Truckee");
            printDetailsList(playerList);
            System.out.println();
            
            System.out.println("Lista de los jugadores de fútbol:");
            playerList = request.getPlayersBySport("Futbol");
            printDetailsList(playerList);
            System.out.println();
            
            System.out.println("Lista de los jugadores de la liga L1:");
            playerList = request.getPlayersByLeagueId("L1");
            printDetailsList(playerList);
            System.out.println();
            
            System.out.println("Lista de los jugadores con un salario mayor que el de Ian Carlyle:");
            playerList = request.getPlayersByHigherSalary("Ian Carlyle");
            printDetailsList(playerList);
            System.out.println();
            
            System.out.println("Lista de los jugadores con un salario entre 500 y 800:");
            playerList = request.getPlayersBySalaryRange(500.00, 800.00);
            printDetailsList(playerList);
            System.out.println();
            
            System.out.println("Lista de los jugadores del equipo T5:");
            playerList = request.getPlayersOfTeam("T5");
            printDetailsList(playerList);
            System.out.println();
            
            System.out.println("Lista de las ligas en las que compite el jugador P28:");
            leagueList = request.getLeaguesOfPlayer("P28");
            printDetailsList(leagueList);
            System.out.println();
            
            System.out.println("Lista de los deportes en los que compite el jugador P28:");
            sportList = request.getSportsOfPlayer("P28");
            printDetailsList(sportList);
            System.out.println();

        } catch (Exception e) {
            System.err.println("El sistema atrapó una excepción.");
        }
    }

    private void removeInfo() {
        try {
            System.out.println("Removiendo equipo T6.");
            request.removeTeam("T6");
            System.out.println();
            
            System.out.println("Removiendo jugador P24.");
            request.removePlayer("P24");
            System.out.println();
            
        } catch (Exception e) {
            System.err.println("El sistema atrapó una excepción.");
        }
    }

    private static void printDetailsList(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Object details = it.next();
            System.out.println(details.toString());            
        }
        System.out.println();
    }
}
