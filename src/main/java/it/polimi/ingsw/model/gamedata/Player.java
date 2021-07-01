package it.polimi.ingsw.model.gamedata;

import it.polimi.ingsw.model.gamedata.gametools.*;
import it.polimi.ingsw.view.virtualview.VirtualViewObserver;

import java.util.List;

/**
 * This class has all information about himself and others, like pattern card, objective card, toolcard ecc...
 */
public class Player {
    private String username;
    private boolean active;
    private PublicObjects publicObjects;
    private ObjectiveCard myObjCard;
    private WindowPatternCard myWindow;
    private int myFavTokens;
    private VirtualViewObserver observer;
    private boolean hasMoved;


    public Player(String username) {
        this.username = username;
        this.active = true;
        this.hasMoved = false;
    }

    /**
     *
     * @return state of player, is true if he's connected or he moves
     */
    public synchronized boolean isActive() {
        return active;
    }

    /**
     * reset hasMoved when a turn ends
     */
    public void resetMove(){
        hasMoved = false;
    }

    /**
     * called when a player moves but he don't complete it
     */
    public void moveDone(){
        hasMoved=true;
    }

    /**
     * Verify if a player moved or not before and set his activity.
     */
    public void noMove(){
        if (!this.hasMoved){
            setActivity(false);
        }
    }

    /**
     * set player's activity
     * @param active activity
     */
    public synchronized void setActivity(boolean active) {
        this.active = active;
        if(active){
            notifyPlayer();
        }
    }


    /**
     * set private objective card
     * @param obj objective card
     */
    void setMyObjCard(ObjectiveCard obj){
        myObjCard =obj;
    }

    /**
     *
     * @param w window patter card to set
     */
    public void setMyWindow(WindowPatternCard w) {
        myWindow = w;
        myFavTokens = myWindow.getDifficulty();
    }

    /**
     *
     * @param publicObjects set what a player can see from other
     */
    public void setPublicObjects(PublicObjects publicObjects) {
        this.publicObjects = publicObjects;
    }


    /**
     *
      * @return player's username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @return all toolcard on table
     */
    public List<ToolCard> getToolCards(){
        return publicObjects.getToolCards();
    }

    /**
     *
     * @return draft pool on table
     */
    public DraftPool getDraftPool(){
        return publicObjects.getDraftPool();
    }

    /**
     *
     * @return players pattern card
     */
    public WindowPatternCard getWindowPatternCard(){
        return myWindow;
    }

    /**
     *
     * @return round track on table
     */
    public RoundTrack getRoundTrack(){
        return publicObjects.getRoundTrack();
    }

    /**
     *
     * @return public objects
     */
    public PublicObjects getPublicObjects(){return this.publicObjects;}

    /**
     *
     * @return private objective card
     */
    public ObjectiveCard getPrivateCard(){return this.myObjCard;}

    /**
     *
     * @return favour token
     */
    public int getMyFavTokens() {
        return myFavTokens;
    }

    /**
     * Notify observer to choose between four window pattern card
      * @param windows list of four possibilities
     */
    public void chooseWindow(List<WindowPatternCard> windows) {
        observer.chooseWindow(windows);
    }

    /**
     * Used when a player choose to use a toolcard
     * @param used true if the toolcard is used
     */
    public void useToken(boolean used){
        if(!used) {
            myFavTokens--;
        }else {
            myFavTokens -= 2;
        }
    }

    /**
     * Notify changes virtual view
     */
    public void notifyPlayer() {
        observer.update();
    }

    /**
     * Notify virtual view
     * @param whoIsTurn who is turn name
     * @param timeSleep timer move
     */
    public void notifyTurn(String whoIsTurn, long timeSleep) {
            observer.updateStateTurn(whoIsTurn,timeSleep);
    }

    /**
     * Calculate the points made at the end of the match
     * @return The points
     */
    public int calculatePoints() {
        return myFavTokens + this.calculatePointsPrivate() + this.calculatePointsPublic() - this.calculateMissingCells();

    }

    /**
     * Calculate the points made with the public cards
     * @return The points
     */
    private int calculatePointsPublic(){
        int score = 0;

        List<ObjectiveCard> cards = publicObjects.getObjectiveCards();
        for(ObjectiveCard c : cards){
            score = score + c.finalpoints(myWindow);
        }

        return score;
    }

    /**
     * Calculate the points removed due to empty cells
     * @return The points
     */
    private int calculateMissingCells(){
        int numberOfMissingCells = 0;

        for(List<Cell> cells: myWindow.getMatr()){
            for (Cell c: cells){
                if(!c.isOccupied())
                    numberOfMissingCells++;
            }
        }
        return numberOfMissingCells;
    }

    /**
     * Calculate points made with private cards
     * @return The points
     */
    int calculatePointsPrivate(){
        return myObjCard.finalpoints(myWindow);
    }

    /**
     *
     * @param o observer to add
     */
    public void addObserver(VirtualViewObserver o){
        observer=o;
    }

    /**
     *
     * @param s kind of wrong move
     */
    public void wrongMove(String s) {
        observer.wrongMove(s);
    }

    /**
     * Notify virtual view
     * @param timerWindows timer to choose patter card
     */
    public void timerChoose(long timerWindows) {
        observer.timerChoose(timerWindows);
    }

    /**
     * Notify virtual view
     * @param state state of move
     */
    public void notifyState(String state) {
        observer.notifyState(state);
    }

    /**
     * Notify virtual view
     * @param s players score
     */
    public void notifyScore(String s) {
        observer.notifyScore(s);
    }

    /**
     * Notify virtual view that player is reconnecting
     */
    public void reconnectingMessage() {
        observer.reconnectingMessage();
    }

    /**
     * Check if can use a toolcard
     * @param used if toolcard is used
     * @return true if toolcard can be used
     */
    public boolean canUseTool(boolean used) {
        if(used){
            return myFavTokens >= 2;
        }else{
            return myFavTokens >= 1;
        }
    }
}

