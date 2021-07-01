package it.polimi.ingsw.controller;


import it.polimi.ingsw.model.gamelogic.Round;
import it.polimi.ingsw.network.ClientInterface;

/**
 * This class is used to associate the current game to the client connected
 */
public class Game {
    private ClientsContainer clients;
    private Match match;
    private int id;
    private boolean started;

    Game(ClientsContainer c, Match m, int i){
        clients=c;
        match=m;
        id=i;
        started=false;
    }

    /**
     * Used to reconnect a client to a game
     * @param cb client box
     */
    void reconnect(ClientBox cb){
        match.setPlayerActivity(cb.getName(),true);
        clients.reconnect(cb);
        match.reconnect(cb);
        match.update(cb.getName());
    }

    /**
     * Used when a match starts to notify players
     */
    void notifyGame(){
        clients.notifyIdMatch(id);
    }

    /**
     * Starts the match Thread
     */
    public void start() {
        started=true;
        clients.setMatchStarted();
        match.start();
    }

    /**
     * Look for a client
     * @param name client name
     * @return true if found
     */
    boolean findClient(String name) {
        return clients.findClient(name);
    }

    /**
     * Remove a client from the container
     * @param name client name
     */
    public void remove(String name) {
            clients.remove(name);
    }

    /**
     *
     * @return current round
     */
    Round getCurrRound() {
        return match.getCurrRound();
    }

    /**
     * Set a player activity
     * @param name player's name
     * @param b true if active
     */
    void setPlayerActivity(String name, boolean b) {
        if(started) {
            match.setPlayerActivity(name, b);
        }
    }


    /**
     * Set to a player the chosen pattern card
     * @param name player's name
     * @param idWindow id Window Pattern Card
     */
    void setPlayerWindow(String name, int idWindow) {
        match.setPlayerWindow(name,idWindow);
    }

    void setEnd() {
        started=false;
        clients.setEnd();
    }

    boolean endedMatch() {
        return !started;
    }

    int sizeClient() {
        return clients.sizeContainer();
    }

    /**
     * used to revenge
     * @param name player's name
     * @return client box
     */
    ClientInterface getClientBox(String name) {
        return clients.getClientBox(name);
    }
}
