package it.polimi.ingsw.model.gamedata;

import java.util.Random;

/**
 * The class repsenting the properties of a dice or of a cell
 */
public class Property {
    private Colour colour;
    private int number;

    /**
     * The classic constructor
     */
    public Property(){
        this.colour = Colour.WHITE;
        this.number = 0;
    }

    /**
     * The constructor used to set the colour of the property and to set a random number if is called by a dice
     * @param colour The colour to set
     * @param isDice If who called this is a dice or not, to decide if the number should be zero or random
     */
    public Property(Colour colour,boolean isDice){
        this.colour = colour;
        if(isDice) {
            this.rollDice();
        }
        else
            this.number = 0;
    }

    /**
     * The constructor used by restrictions to set a specific number
     * @param value The number to set
     */
    public Property(int value){
        this.colour = Colour.WHITE;
        this.number = value;
    }

    /**
     * Getter for the colour
     * @return The colour of the current property
     */
    public Colour getColour() {
        return this.colour;
    }

    /**
     * Getter for the number
     * @return The number of the current property
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * Setter for the colour
     */
    public void setColour(Colour z){
        this.colour = z;
    }

    /**
     * Getter for the number
     */
    public void setNumber(int k){
        this.number = k;
    }

    /**
     * Method used to roll a dice so to set the property number to a random number
     */
    public void rollDice(){
        int randomNum;
        Random rand = new Random();
        randomNum = 1 + rand.nextInt(6);
        this.number = randomNum;
    }
}
