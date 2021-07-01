package it.polimi.ingsw.controller;

import java.util.TimerTask;

/**
 * Timer to handle the waiting room.
 */
public class MatchTimerTask extends TimerTask {
    private WaitingRoom lobby;
    private long tempTime;

    MatchTimerTask(WaitingRoom lobby, long time) {
        this.lobby = lobby;
        this.tempTime = time;
    }

    @Override
    public void run() {
        tempTime -= 1000;
        ClientsContainer playerList = lobby.getClients();

        if (playerList.sizeContainer() >= 2 && tempTime == 0) {
            lobby.resetTimer();
            lobby.notifyManager();
        }
        else if (playerList.sizeContainer() < 2) {
            lobby.resetTimer();
        }

    }

    synchronized long getTempTime () {
            return tempTime;
        }


}
