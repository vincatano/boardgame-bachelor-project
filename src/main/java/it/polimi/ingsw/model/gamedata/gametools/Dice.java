package it.polimi.ingsw.model.gamedata.gametools;

import it.polimi.ingsw.model.gamedata.Colour;
import it.polimi.ingsw.model.gamedata.Property;

/**
 * This class represents the dice
 */
public class Dice {
    private Property prop;
    private boolean selected;

    /**
     * Classic constructor that creates a dice with white colour and random number
     */
    public Dice(){
        this.prop = new Property(Colour.WHITE,true);
        selected = false;
    }


    /**
     * Constructor that creates a dice with the colour specified
     * @param y The colour specified
     */
    public Dice(Colour y){
        this.prop = new Property(y,true);
        selected = false;
    }

    /**
     * Says if a dice is selected or not
     * @return The value of the "selected" variable
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Getter for the colour of the dice
     * @return The colour of the dice
     */
    public Colour getColour(){
        return prop.getColour();
    }

    /**
     * Getter for the number of the dice
     * @return The number of the dice
     */
    public int getNumber(){
        return  prop.getNumber();
    }

    /**
     * A method that says if this dice has the same properties
     * @param d The dice to compare
     * @return If the dice has the same properties or not
     */
    public boolean areEquals(Dice d) {
        if (this.prop.getNumber() == d.prop.getNumber() && this.prop.getColour().areEquals(d.prop.getColour())){
                selected = true;
                return true;
        }
        return false;
    }

    /**
     * Setter for the number of the dice
     * @param number The number to set
     */
    public void setNumber(int number){
        this.prop.setNumber(number);
    }

    /**
     * Setter for the colour of the dice
     * @param colour The colour to set
     */
    public void setColour(String colour) {
        this.prop.setColour(Colour.isIn(colour.charAt(0)));
    }

    /**
     * Setter for the dice
     * @param d The dice to set
     */
    public void setDice(Dice d){
        this.prop.setNumber(d.getNumber());
        this.prop.setColour(d.getColour());
        selected=false;
    }

    /**
     * This method assigns a random number to the dice
     */
    public void rollDice(){
        this.prop.rollDice();
    }

    /**
     * Sets the value of the "selected" variable to false
     */
    void deSelect() {
        selected=false;
    }
}
