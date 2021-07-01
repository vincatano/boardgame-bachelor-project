package it.polimi.ingsw.model.gamelogic;


import it.polimi.ingsw.controller.Match;
import it.polimi.ingsw.model.gamedata.Player;
import it.polimi.ingsw.model.gamedata.Pos;
import it.polimi.ingsw.model.gamedata.Table;
import it.polimi.ingsw.model.gamedata.gametools.Dice;
import it.polimi.ingsw.model.gamedata.gametools.ToolCard;
import it.polimi.ingsw.model.gamelogic.turn.Turn;
import it.polimi.ingsw.model.gamelogic.turn.moveexceptions.WrongMoveException;

import java.util.Iterator;
import java.util.List;

/**
 * This class handle the timeout for a move and set the move to the object Turn, which verify the it correctness
 */
public class Round {
    private static final String PATH ="it.polimi.ingsw.model.gamelogic.turn.";

    private int roundNumber;
    private PlayersContainer players;
    private Table table;
    private Turn turn;
    private Player currPlayer;
    private Match match;
    private long timerMove;
    private String usedTool8="";

    public Round(List<Player> playerList, int roundNumber, Table table, Match m, long timerMove) {
        this.roundNumber = roundNumber;
        this.players = new PlayersContainer(playerList);
        this.table=table;
        this.timerMove =timerMove;
        this.match = m;
    }

    /**
     * This method starts the round,notify all players who has to move and it does a thread.sleep(), if the player will not do the move
     * it will be set inactive. If he does a right move, will be called an interrupt on the sleep and the round goes on with the turn of next
     * player.
     * @throws NotEnoughPlayersException if player are less then two
     */
    public void go() throws NotEnoughPlayersException {
        Player p;
        Iterator<Player> iterator = players.getIterator();
        while(iterator.hasNext()){
            p = iterator.next();
            currPlayer = p;
            if (p.isActive()&&!p.getUsername().equals(usedTool8)) {
                turn = new Turn(p, this, players.isFirstBracket(), table);
                turn.startTurn();
                players.notifyTurn(p.getUsername(), timerMove);
                updateBeforeMove();
                try {
                    Thread.sleep(timerMove);
                    p.noMove();
                    players.notifyChanges();
                } catch (InterruptedException e) {
                    players.notifyChanges();
                }
                if (!players.checkActivity()) {
                    throw new NotEnoughPlayersException();
                }
                p.resetMove();
            }
        }
        setLastDice();
        if(!usedTool8.equals("")){
            usedTool8="";
        }
    }

    /**
     * This method set last dices on round track at the end of round.
     */
    private void setLastDice() {
        table.setLastDices(roundNumber);
    }

    /**
     *
     * @return name of player who has turn
     */
    public String getCurrTurn() {
        return currPlayer.getUsername();
    }

    /**
     * sends the current state of move
     */
    private void updateAfterMove(){
        players.notifyChanges();
        String name = turn.getState().getClass().getName();
        name = name.replace(PATH,"");
        currPlayer.notifyState(name);
    }

    /**
     * sends the start turn message to the current player
     */
    private void updateBeforeMove(){
        String name = turn.getState().getClass().getName().replace(PATH,"");
        currPlayer.notifyState(name);
    }

    /**
     * Set chosen position to the Turn
     * @param p chosen position
     */
    public void setTurn(Pos p){
        try {
            turn.receiveMove(p);
            updateAfterMove();
        } catch (WrongMoveException e) {
            currPlayer.wrongMove(e.getMessage());
        }
    }

    /**
     * Set pass to the Turn
     * @param s pass string
     */
    public void setTurn(String s){
        try {
            turn.receiveMove(s);
            updateAfterMove();
        } catch (WrongMoveException e) {
            currPlayer.wrongMove(e.getMessage());
        }
    }

    /**
     * Set chosen tool card to the Turn
     * @param t chosen toolcard
     */
    public void setTurn(ToolCard t){
        try {
            turn.receiveMove(t);
            updateAfterMove();
        } catch (WrongMoveException e) {
            currPlayer.wrongMove(e.getMessage());
        }
    }

    /**
     * Set chosen dice to the Turn and its position in the model
     * @param d chosen dice
     * @param p dice position in draft pool, roundtrack or window pattern card
     */
    public void setTurn(Dice d, Pos p){
        try {
            turn.receiveMove(d,p);
            updateAfterMove();
        } catch (WrongMoveException e) {
            currPlayer.wrongMove(e.getMessage());
        }
    }

    /**
     * used when a player uses tool card 8
     * @param p
     */
    public void inactivatePlayer(Player p) {
        this.usedTool8=p.getUsername();
    }

    /**
     * Used when a move ends.
     */
    public  void interrupt(){
        match.interrupt();
    }

    /**
     * Update when a player became active again
     */
    public void update() {
        players.notifyChanges();
    }

    public void check(){
        if(!players.checkActivity()){
            interrupt();
        }
    }
}
