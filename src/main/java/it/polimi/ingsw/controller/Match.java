package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.gamedata.Player;
import it.polimi.ingsw.model.gamedata.Table;
import it.polimi.ingsw.model.gamelogic.NotEnoughPlayersException;
import it.polimi.ingsw.model.gamelogic.Round;
import it.polimi.ingsw.view.virtualview.VirtualView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class has all the info about the current match like players and their attributes and the game table which contains
 * all the data for play. It is a thread to realize the multi-match function.
 */
public class Match extends Thread {
    private  Manager manager;
    private int id;
    private List<Player> playerList;
    private Table table;
    private List<Round> roundList;
    private int roundNumber;
    private Round currRound;
    private long timerCard;
    private long timerMove;

    public Match(List<Player> playerList, Manager manager, int id, long timerCard, long timerMove) {
        this.timerCard=timerCard;
        this.timerMove=timerMove;
        this.playerList = playerList;
        this.id = id;
        this.roundList = new ArrayList<>();
        this.manager=manager;
        playerList.forEach(player -> player.timerChoose(timerCard));
    }


    /**
     * When the thread is ran it first stars a scheduled executor which verify if the player choose the toolcard, if he
     * don't choose the card it will set him a random card between the four possibility.
     * After that starts rounds and when the game end, notify the manager and the thread will return.
     */
    @Override
    public void run() {
        table = new Table(playerList);
        table.initialize();
        table.selectWindowCards();
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(10);
        exec.scheduleWithFixedDelay(() -> {
            for(Player p : playerList){
                if(p.getWindowPatternCard()==null){
                    return;
                }
            }
            this.interrupt();

        }, 0, 500, TimeUnit.MILLISECONDS);
        try {
            Thread.sleep(timerCard);
            playerList.stream().filter(player -> player.getWindowPatternCard()==null)
                    .forEach(player -> table.setWindow(player.getUsername()));
            exec.shutdown();
        } catch (InterruptedException e) {
            exec.shutdown();
        }
        for(roundNumber = 1; roundNumber <= 10; roundNumber++) {
            try {
                this.table.fillDraftPool();
                playerList.forEach(Player::notifyPlayer);
                this.startNextRound();
                changeOrder();
            } catch (NotEnoughPlayersException e) {
                break;
            }
        }

        String score = table.calculatePoints();
        playerList.forEach(player -> player.notifyScore(score));
        playerList.forEach(player -> manager.matchEnded(player.getUsername()));
        manager.endGame(id);
    }


    /**
     * Create and starts new round
     * @throws NotEnoughPlayersException if the number of players is < 2
     */
    private void startNextRound() throws NotEnoughPlayersException {
            currRound = new Round(this.playerList, roundNumber,table,this,timerMove);
            roundList.add(currRound);
            roundList.get(roundNumber-1).check();
            roundList.get(roundNumber-1).go();

    }

    /**
     * After each round it changes the order of players, in this way the first of the turn will not be always the same player.
     */
    private synchronized void changeOrder(){
        ArrayList tmpList = new ArrayList();
        Player tmp;
        tmp = playerList.get(0);
        for (int i = 1; i<playerList.size();i++){
            tmpList.add(playerList.get(i));
        }
        tmpList.add(tmp);
        playerList = tmpList;
    }

    /**
     *
     * @return player list.
     */
    public List<Player> getPlayerList() {
        return this.playerList;
    }


    /**
     *
     * @return the current round object.
     */
    Round getCurrRound() {
        return currRound;
    }

    /**
     * If a player will connect or disconnect, set the player's activity. It handle if the player disconnected is the one
     * who has the current turn.
     * @param name player to change activity
     * @param b true if active
     */
    void setPlayerActivity(String name, boolean b) {
        for (Player p : playerList){
            if(p.getUsername().equals(name)){
                p.setActivity(b);
                if(currRound!=null && p.getUsername().equals(currRound.getCurrTurn())&&!b){
                    currRound.interrupt();
                }else if(currRound!=null){
                    currRound.check();
                }
            }
        }


    }

    /**
     * Set the chosen pattern card to the player
     * @param name player's name
     * @param idCard id card
     */
    synchronized void setPlayerWindow(String name, int idCard){
        for(Player p : playerList){
            if (p.getUsername().equals(name)) {
                table.setWindow(p,idCard);
            }
        }
    }

    /**
     * Used when a player is reconnecting, it will notify each player
     * @param name player who is reconnecting
     */
    public void update(String name) {
        if(currRound!=null) {
            playerList.forEach(Player::notifyPlayer);
            for (Player p : playerList) {
                if (p.getUsername().equals(name)) {
                    p.reconnectingMessage();
                }
            }
        }
    }

    /**
     * Add to the right player object, the new virtual view observer after a reconnection.
     * @param cb encapsulated in the virtual view of the player
     */
    void reconnect(ClientBox cb) {
        for (Player p : playerList){
            if(p.getUsername().equals(cb.getName())){
                p.addObserver(new VirtualView(cb,p));
            }
        }
    }
}