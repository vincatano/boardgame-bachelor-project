package it.polimi.ingsw.model.gamelogic;

import it.polimi.ingsw.model.gamedata.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is used to group all Players in a round and iterate on them with the turn logic
 */
public class PlayersContainer {
    private List<Player> players;
    private boolean firstBracket;

    public PlayersContainer(List<Player> p){
        players = p;
        firstBracket=true;
    }

    /**
     * Check if there are enough player active to go on playing
     * @return if there are at least two
     */
    public boolean checkActivity(){
        List<Player> tmp = players.stream().filter(Player::isActive).collect(Collectors.toCollection(ArrayList::new));
        return tmp.size() > 1;
    }

    /**
     *
     * @return true if is the first move in the turn
     */
    boolean isFirstBracket(){
        return firstBracket;
    }

    /**
     *
     * @return iterator for players
     */
    public IterPlayer getIterator(){
        return new IterPlayer();
    }

    /**
     * Used to notify players when there is a change in the model.
     */
    void notifyChanges() {
        players.forEach(Player::notifyPlayer);
    }

    /**
     *
     * @param username player's name who has the turn
     * @param timeSleep timer for the move
     */
    void notifyTurn(String username, long timeSleep) {
        players.forEach(p->p.notifyTurn(username,timeSleep));
    }

    /**
     * Iterator for player in a round
     */
    private class IterPlayer implements Iterator{
        private int i;

        private IterPlayer() {
            i = -1;
        }

        @Override
        public boolean hasNext() {
            if(isFirstBracket()) {
                return i <= players.size();
            }else{
                return i != 0;
            }
        }

        /**
         * Scan players like game specifics.
         * @return Players who has the current turn
         */
        @Override
        public Player next() {
            if (i == players.size()-1 && isFirstBracket()) {
                setFirstBracket();
                i++;
            }
            if (this.hasNext()) {
                if (isFirstBracket()) {
                    i++;
                    return players.get(i);
                } else {
                    i--;
                    return players.get(i);
                }
            }else {
                return null;
            }
        }

        private void setFirstBracket(){
            firstBracket = false;
        }
    }
}

