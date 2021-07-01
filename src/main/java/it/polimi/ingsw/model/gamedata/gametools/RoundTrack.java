package it.polimi.ingsw.model.gamedata.gametools;

import it.polimi.ingsw.model.gamedata.Pos;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the roundtrack
 */
public class RoundTrack {
    private List<List<Dice>> diceOnRoundTrack;

    /**
     * The classic constructor
     */
    public RoundTrack(){
        diceOnRoundTrack = new ArrayList<>();
    }

    /**
     * Gives the list of dices corresponding to the round
     * @param numRound The round from where you want to take the dices
     * @return The list of dices
     */
    public List<Dice> getDiceOnRoundtrack(int numRound){
        return diceOnRoundTrack.get(numRound-1);
    }

    /**
     * Sets the list of dices to the specified round
     * @param numRound The round where will be placed the dices
     * @param dice The dices to place
     */
    public void setDiceOnRoundTrack(int numRound, List<Dice> dice){
        diceOnRoundTrack.add(numRound-1, dice);
    }

    /**
     * Getter for the list representing the roundtrack
     * @return The list representing the roundtrack
     */
    public List<List<Dice>> getRoundTrack(){
        return diceOnRoundTrack;
    }

    /**
     * Getter for a dice place in that specified position
     * @param pos The position from which the dice will be taken
     * @return The dice requested
     */
    public Dice getDice(Pos pos){
        return this.diceOnRoundTrack.get(pos.getX()).get(pos.getY());
    }

    /**
     * Says if the at the specified position is present a dice that has the same properties of the specified one (dice)
     * @param dice The specified dice
     * @param pos The specified position
     * @return If the two dices have the same properties
     */
    public boolean findDice(Dice dice, Pos pos){
        return this.getDice(pos).areEquals(dice);
    }

    /**
     * Remove the dice at the position specified
     * @param pos The position specified
     */
    public void removeDice(Pos pos) {
        this.diceOnRoundTrack.get(pos.getX()).remove(pos.getY());
    }

    /**
     * Add the dice passed at the position specified
     * @param pos The position specified
     * @param dice The dice passed
     */
    public void addDice(Pos pos,Dice dice) {
        this.diceOnRoundTrack.get(pos.getX()).add(pos.getY(), dice);
    }

    /**
     * Reset the (selected) value for every dice in roundtrack
     */
    public void resetSelection() {
        diceOnRoundTrack.forEach(dice -> dice.forEach(Dice::deSelect));
    }
}
