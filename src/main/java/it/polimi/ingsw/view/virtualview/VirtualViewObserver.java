package it.polimi.ingsw.view.virtualview;

import it.polimi.ingsw.controller.ClientBox;
import it.polimi.ingsw.model.gamedata.Player;
import it.polimi.ingsw.model.gamedata.gametools.WindowPatternCard;

import java.util.List;

/**
 * This interface is used to notify with pattern observer the virtual view observer
 */
public abstract class VirtualViewObserver {
    ClientBox clientBox;
    protected Player player;

    /**
     * It will notify the view to update the model
     */
    public abstract void update();

    /**
     *
     * @param whoIsTurn name of player's turn
     * @param timeSleep timer move
     */
    public abstract void updateStateTurn(String whoIsTurn, long timeSleep);

    /**
     *
     * @param s wrong move message
     */
    public abstract void wrongMove(String s);

    /**
     *
     * @param windows choose window pattern card message
     */
    
    public abstract void chooseWindow(List<WindowPatternCard> windows);

    /**
     *
     * @param timerWindows timer choose pattern card
     */
    public abstract void timerChoose(long timerWindows);

    /**
     *
     * @param state name turn state move
     */
    public abstract void notifyState(String state);

    /**
     *
     * @param s all players score message
     */
    public abstract void notifyScore(String s);

    /**
     * notify view of reconnection
     */
    public abstract void reconnectingMessage();
}
