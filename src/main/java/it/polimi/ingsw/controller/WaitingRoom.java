package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.gamedata.Player;
import it.polimi.ingsw.network.ClientInterface;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class is used to handle the players before the game starts
 */
public class WaitingRoom {
    private Manager manager;
    private MatchTimerTask task;
    private long time;
    private ClientsContainer playerList;

    WaitingRoom(long time, Manager manager, ClientsContainer clientContainer) {
        playerList = clientContainer;
        this.time = time;
        this.manager = manager;
        task = new MatchTimerTask(this,time);
    }


    /**
     * This will add the player and verify if starts the timer or if starts the match in case of four player
     * @param player player to add
     */
    void addPlayer(ClientInterface player) {
        if (playerList.sizeContainer() == 1) {
            playerList.addClient(player,task.getTempTime());
            playerList.notifyPlayers();
            ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
            exec.scheduleWithFixedDelay(task,0,1000,TimeUnit.MILLISECONDS);
        } else if (playerList.sizeContainer() < 4) {
            playerList.addClient(player,task.getTempTime());
            playerList.notifyPlayers();
            if(playerList.sizeContainer() == 4) {
                this.notifyManager();
            }
        }
    }

    /**
     * When match starts or there aren't enough player to start it
     */
    void resetTimer() {
        task = new MatchTimerTask(this,time);
    }

    /**
     * Used when is created a new waiting room
     * @param clientContainer object that will group all clients.
     */
    void restore(ClientsContainer clientContainer) {
        this.playerList = clientContainer;
        this.resetTimer();
    }


    /**
     *
     * @return player list from client container
     */
    public List<Player> getPlayerList() {
        return playerList.getPlayerList();
    }


    /**
     * Used when match is ready to start
     */
    void notifyManager() {
        manager.createMatch(this);
    }

    /**
     *
     * @return client container
     */
    ClientsContainer getClients() {
        return playerList;
    }

}
