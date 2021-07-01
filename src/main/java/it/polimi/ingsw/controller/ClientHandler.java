package it.polimi.ingsw.controller;

import java.util.HashMap;

import static java.lang.System.*;

/**
 * This class stores all the players and the number of match where they are playing.
 */
class ClientHandler {
    private HashMap<String,Integer> allPlayers;


    ClientHandler(){
        allPlayers = new HashMap<>();
    }

    /**
     * Search if there is a player with that name.
     * @param name player's name
     * @return true if there is.
     */
    synchronized boolean isAPlayerIn(String name){
        return allPlayers.containsKey(name);
    }

    /**
     * Add player to the table
     * @param name player's name
     * @param idMatch match id
     */
    synchronized void addPlayer(String name, int idMatch){
        allPlayers.put(name,idMatch);
        out.println(allPlayers.size() + " players playing");
    }

    /**
     * remove the player with that name
     * @param name player's name
     */
    synchronized void removePlayer(String name){
        allPlayers.remove(name);
        out.println(allPlayers.size() + " players playing");
    }

    /**
     * get the number of match where the player is playing
     * @param name player's name
     * @return number of match
     */
    int getGame(String name) {

        return allPlayers.get(name);
    }
}
