package it.polimi.ingsw.model.gamedata.gametools;

import it.polimi.ingsw.model.gamedata.Pos;
import it.polimi.ingsw.model.gamedata.Property;


/**
 *This class represents the Cells that make the WindowPatternCard
 */
public class Cell {
    private Property property;
    private Pos pos;
    private boolean occupied = false;
    private Dice dice;

    /**
     * The classic constructor
     */
    public Cell(){
        this.property = new Property();
        this.pos = new Pos();
        this.dice = new Dice();
    }

    /**
     * Getter of the property
     * @return The property of the cell
     */
    public Property getProperty(){
        return this.property;
    }

    /**
     * Getter of the Position
     * @return The position of the cell
     */
    public Pos getPosition(){
        return this.pos;
    }

    /**
     * Getter of the dice
     * @return The dice place on the cell
     */
    public Dice getDice(){
       return this.dice;
    }

    /**
     * Says if the cell is occupied by a dice
     * @return A boolean that indicates if the cell is occupied by a dice
     */
    public boolean isOccupied(){
        return this.occupied;
    }

    /**
     * Sets the value "occupied" of the class to the one passed
     * @param x The parameter to set
     */
    protected void setOccupation(boolean x){
        this.occupied = x;
    }

    /**
     * Sets the value "pos" of the class to the one passed
     * @param x The parameter to set
     */
    protected void setPos(Pos x) {
        this.pos = x;
    }

    /**
     * Sets the value "property" of the class to the one passed
     * @param x The parameter to set
     */
    protected void setProperty(Property x){
        this.property = x;
    }

    /**
     * Sets the value "dice" of the class to the one passed
     * @param x The parameter to set
     */
    protected void setDice(Dice x){
        this.dice = x;
    }

}
