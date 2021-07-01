package it.polimi.ingsw.model.gamedata.gametools;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the draftpool
 */
public class DraftPool {
    private List<Dice> diceList;

    /**
     * The classic constructor
     */
    public DraftPool(){
        diceList = new ArrayList<>(0);
    }

    /**
     * This constructor instantiate the diceList to the exact dimension
     * @param x The number of players currently present
     */
    public DraftPool(int x){
        int y = (x * 2) + 1;
        this.diceList = new ArrayList<>(y);
    }

    /**
     * Getter method for the draftpool
     * @return The list representing the draftpool
     */
    public List<Dice> getDraftPool() {
        return this.diceList;
    }

    /**
     * This method is used to take a dice from the indicated position
     * @param i The position from you wanto to take the dice
     * @return The dice requested
     */
    public Dice chooseDice(int i){
        if(i >= 0 && i < diceList.size()) {
            return diceList.get(i);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Add the dices pulled out from the bag
     * @param newDice The dices pulled out from the bag
     */
    public void addNewDices(List<Dice> newDice){
        this.diceList = newDice;
    }

    /**
     * Gives back the number of dices remaining in the draftpool
     * @return The number dices remaining in the draftpool
     */
    public int getNumOfDices(){
        return diceList.size();
    }

    /**
     * Returns true if the dices passed (d) is the same as the one that is at the position where of the draftpool
     * @param d A dice
     * @param where The position
     * @return a boolean
     */
    public boolean findDice(Dice d, int where){
        return this.diceList.get(where).areEquals(d);
    }

    /**
     * Returns true if the dice passed has the same properties of one dice that is present in the draftpool
     * @param d The dice to find
     * @return If the find has been found or not
     */
    public boolean findDice(Dice d){
        for (Dice x: this.diceList){
            if (x.areEquals(d))
                return true;
        }
        return false;
    }

    /**
     * Removes a dice from the list if is present
     * @param where The position where the dice is
     */
    public void removeDice(int where){
        this.diceList.remove(where);
    }

    /**
     * Adds a dice to the list
     * @param index The index where you want to add the dice
     * @param dice The dice to add
     */
    public void addDice(int index, Dice dice) {
        this.diceList.add(index,dice);
    }

    /**
     * Modify the variable "selected" of every dice present in the list "diceList"
     */
    public void resetSelection() {
        diceList.forEach(Dice::deSelect);
    }
}