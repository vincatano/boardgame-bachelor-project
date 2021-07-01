package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.gamedata.Player;
import it.polimi.ingsw.network.ClientInterface;
import it.polimi.ingsw.view.virtualview.VirtualView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.System.*;

/**
 * This class is used to group all the clients that are playing the same game or are waiting in the same waiting room
 * This class also starts a scheduled thread which ping each client to verify if them are still alive.
 */
public class ClientsContainer {
    private List<ClientBox> clients;
    private boolean matchStarted;
    private Manager manager;


    ClientsContainer(Manager manager){
        clients = new ArrayList<>();
        this.manager= manager;
        matchStarted = false;
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleWithFixedDelay(() -> {
            for (ClientBox c : clients) {
                try {
                    c.ping();
                } catch (IOException e) {
                    out.println(c.getName() + " is disconnected");
                    remove(c.getName());

                }
            }
        },0,500,TimeUnit.MILLISECONDS);

    }


    /**
     * Add client to the container, creating the a ClientBox object and send to it the timer of the waiting room
     * @param c client
     * @param tempTime timer waiting room
     */
    synchronized void addClient(ClientInterface c, long tempTime){
        try {
            ClientBox cli =new ClientBox(c,c.getName());
            cli.setTimer(tempTime);
            clients.add(cli);
        } catch (RemoteException e) {
            out.println("Unable get client name");
        }
    }
    synchronized int sizeContainer(){
        return clients.size();
    }

    /**
     * Create a list of players from the client connected ready to play
     * @return List of player connected
     */
    public synchronized List<Player>  getPlayerList(){
        List<Player> p = new ArrayList<>();
        Iterator<ClientBox> i = clients.iterator();
        while (i.hasNext()){
            ClientBox c = i.next();
                Player player = new Player(c.getName());
                player.addObserver(new VirtualView(c,player));
                p.add(player);
        }
        return p;
    }

    /**
     * Find if there is a client who has the same name
     * @param name client name
     * @return true if there is.
     */
    synchronized boolean findClient(String name) {
        for (ClientBox cli : clients){
                if(cli.getName().equals(name)){
                    return true;
                }
        }
        return false;
    }

    /**
     * Remove a client from the container.
     * @param name name of client to disconnect.
     * @return true if found it.
     */
    public synchronized boolean remove(String name) {
        for (ClientBox cli : clients){
                if(cli.getName().equals(name)){
                    clients.remove(cli);
                    if(matchStarted) {
                        manager.setPlayerActivity(cli.getName(),false);
                    }
                    notifyPlayers();
                    return true;
                }
        }
        return false;
    }

    /**
     * Create the list of client currently connected and sends it to everyone.
     */
    synchronized void notifyPlayers(){
        StringBuilder str = new StringBuilder();
            for (ClientBox c : clients){
                 str.append(" ").append(c.getName());
            }
            String playersIn = str.toString();
            for(ClientBox c : clients) {
                c.updatePlayers(playersIn);
            }
    }

    void setMatchStarted() {
        this.matchStarted = true;
    }

    /**
     * Sends to every client the number of the match that is going to start
     * @param numOfMatch number of match
     */
    synchronized void notifyIdMatch(int numOfMatch) {
        for (ClientBox c : clients) {
            c.setNumMatch(numOfMatch);
        }
    }


    /**
     * Reconnect the client object to the match
     * @param cb client box to add
     */
    void reconnect(ClientBox cb) {
        clients.add(cb);
        notifyPlayers();
    }


    /**
     * Used to manage the revenge of a player
     * @param name player's name that wants to play again
     * @return Client interface for communication
     */
    ClientInterface getClientBox(String name) {
        for (ClientBox c : clients){
            if (c.getName().equals(name)){
                clients.remove(c);
                return c.getInterface();
            }
        }
        return null;
    }

    /**
     * Called when a match ends
     */
    void setEnd() {
        matchStarted=false;
    }
}
