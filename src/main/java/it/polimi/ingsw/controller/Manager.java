package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.gamelogic.Round;
import it.polimi.ingsw.network.ClientInterface;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.System.*;

/**
 * This class is the main controller of game, it has references of all games, waiting room and client connected. Every
 * user input passes throw it.
 */
public class Manager {
    private HashMap<Integer,Game> games;
    private ClientsContainer clients;
    private int numOfMatch;
    private WaitingRoom lobby;
    private ClientHandler clientHandler;
    private InputAnalyzer analyzer;
    private long timerCard;
    private long timerMove;

    public Manager(int timerRoom,int timerCard, int timerMove) {
        long timerRoom1 = timerRoom * 1000;
        this.timerCard=timerCard*1000;
        this.timerMove=timerMove*1000;
        games = new HashMap<>();
        numOfMatch = 0;
        clients = new ClientsContainer(this);
        lobby = new WaitingRoom(timerRoom1, this, clients);
        clientHandler = new ClientHandler();
        analyzer = new InputAnalyzer(this);
    }

    /**
     * When the waiting room timer is expired, it creates a new match and starts the associated thread, after that
     * creates a new waiting room and waits players.
     * @param lobby current waiting room
     */
    void createMatch(WaitingRoom lobby) {
        Game g = new Game(clients,new Match(lobby.getPlayerList(), this, numOfMatch,timerCard,timerMove),numOfMatch);
        g.notifyGame();
        games.put(numOfMatch, g);
        /*START MATCH*/
        lobby.getPlayerList().forEach(player -> clientHandler.addPlayer(player.getUsername(), numOfMatch));
        g.start();
        numOfMatch++;
        clients = new ClientsContainer(this);
        lobby.restore(clients);
    }

    /**
     * add player to the waiting room
     * @param client object for communication
     */
    public synchronized void addPlayerInQueue(ClientInterface client) {
        lobby.addPlayer(client);
    }

    /**
     * Look for waiting room and started match
     * @param name player to connect
     * @return true if found.
     */
    public synchronized boolean checkIfPlayerIsLogged(String name) {
        if(clients.findClient(name)){
            return true;
        }
        for (Map.Entry<Integer, Game> pair : games.entrySet()) {
            if (pair.getValue().findClient(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if is playing
     * @param name player name
     * @return true if found
     */
    public boolean checkIfPlayerIsPlaying(String name) {
        return (clientHandler.isAPlayerIn(name));
    }


    /**
     * Remove player from games or current waiting room
     * @param name
     */
    public void remove(String name){
        clients.remove(name);
        for (Map.Entry<Integer,Game> pair : games.entrySet()) {
            pair.getValue().remove(name);
        }
    }


    /**
     * Sends user command to input analyzer
     * @param cmd user command
     */
    public void analyze(String cmd) {
        analyzer.analyse(cmd);
    }

    /**
     * Sends the move to the processor, checking if the player who moved is correct
     * @param match player match
     * @param name player name
     * @param move player move
     */
    void move(int match,String name, String move){
        Round round = games.get(match).getCurrRound();
        if(round.getCurrTurn().equals(name)){
            Proc processor = new Proc(round,move);
            processor.process();
        }else if(move.equals("active")){
            setPlayerActivity(name,true);
            round.update();
        }
    }

    /**
     * Set a player activity
     * @param name player's name
     * @param b true if active
     */
    void setPlayerActivity(String name, boolean b) {
        int numMatch = clientHandler.getGame(name);
        games.get(numMatch).setPlayerActivity(name,b);
    }

    /**
     * used to reconnect the player
     * @param c client interface for communication
     */
    public void reconnectPlayer(ClientInterface c) {
        try {
            ClientBox clientB = new ClientBox(c,c.getName());
            int num = clientHandler.getGame(clientB.getName());
            clientB.setNumMatch(num);
            games.get(num).reconnect(clientB);
        } catch (RemoteException e) {
            out.println("Cannot reconnect player");
        }
    }

    /**
     * When a match ends remove the player from client handler
     * @param username player to remove
     */
    void matchEnded(String username) {
        clientHandler.removePlayer(username);
    }

    /**
     * set the player chosen window pattern card
     * @param num id match
     * @param name player name
     * @param window id card
     */
    void setPlayerWindow(int num, String name, String window) {
        games.get(num).setPlayerWindow(name,Integer.parseInt(window));
    }

    /**
     * remove player from a match
     * @param num id match
     * @param name player name
     */
    void disconnectPlayer(int num, String name) {
        games.get(num).remove(name);
    }

    /**
     * set boolean value in game
     * @param id id match
     */
    void endGame(int id) {
        games.get(id).setEnd();
    }

    /**
     *This method removes the games object where there are no more client connected, so after the end of the game
     * they just exit from application whiteout play again.
     */
    public void checkEndGame(){
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(10);
        exec.scheduleWithFixedDelay(() -> {
            for (Map.Entry<Integer,Game> pair : games.entrySet()) {
                if(pair.getValue().endedMatch()&&pair.getValue().sizeClient()==0){
                    games.remove(pair.getValue());
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * Used if a player wants to revenge
     * @param name player's name
     */
    void revenge(String name) {
        for (Map.Entry<Integer,Game> pair : games.entrySet()){
            if(pair.getValue().endedMatch()&&pair.getValue().findClient(name)){
                lobby.addPlayer(pair.getValue().getClientBox(name));
            }
        }
    }

    /**
     * Check if the string has forbidden characters
     * @param name string to verify
     * @return true if it is good
     */
    public boolean checkFormatName(String name) {
        return analyzer.verifyName(name);
    }
}
