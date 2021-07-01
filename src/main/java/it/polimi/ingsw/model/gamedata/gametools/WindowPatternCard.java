package it.polimi.ingsw.model.gamedata.gametools;

import it.polimi.ingsw.model.gamedata.Colour;
import it.polimi.ingsw.model.gamedata.Pos;
import it.polimi.ingsw.model.gamedata.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the WindowPatternCard
 */
public class WindowPatternCard {
    private int num;
    private List<List<Cell>> matr = new ArrayList<>(4);
    private int difficulty;
    private String name;
    private String player;

    /**
     * This constructor is used to create a card with its idnumber, difficulty and name
     * @param num The idnumber
     * @param difficulty The difficulty
     * @param name The name
     */
    public WindowPatternCard(int num, int difficulty, String name){
        this.num = num;
        this.difficulty = difficulty;
        this.name = name;
        Cell y;
        Pos p;
        for(int i = 0; i < 4; i++){
            ArrayList<Cell> x = new ArrayList<>(4);
            this.matr.add(x);
            for(int j = 0; j < 5; j++){
                y = new Cell();
                p = new Pos(i,j);
                y.setPos(p);
                y.setOccupation(false);
                this.matr.get(i).add(y);
            }
        }
    }

    /**
     * Getter for the idnumber
     * @return The idnumber
     */
    public int getNum(){
        return this.num;
    }

    /**
     * Getter for the idnumber
     * @return The idnumber
     */
    public int getDifficulty() {
        return this.difficulty;
    }

    /**
     * Getter for the idnumber
     * @return The idnumber
     */
    public String getName(){
        return this.name;
    }

    /**
     * Getter for the matrix
     * @return The matrix
     */
    public List<List<Cell>> getMatr(){
        return this.matr;
    }


    /**
     * Getter for the dice at the specified position
     * @param p The position requested
     * @return If present, the dice asked
     */
    public Dice getDice(Pos p){
        if (!(this.getCell(p).isOccupied()))
            throw new IllegalStateException();
        return (this.getCell(p).getDice());
    }

    /**
     * Getter for the cell at the specified position
     * @param p The specified position
     * @return The cell requested
     */
    public Cell getCell(Pos p){
        return (this.matr.get(p.getX()).get(p.getY()));
    }

    /**
     * Getter for the name of the player to whom is assigned the card
     * @return The name of the player
     */
    public String getPlayer(){
        return this.player;
    }


    /**
     * The setter for the idnumber of the card
     * @param x The number to assign
     */
    public void setNum(int x){
        this.num = x;
    }

    /**
     * The setter for the difficulty of the card
     * @param x The difficulty to assign
     */
    public void setDifficulty(int x){
        this.difficulty = x;
    }

    /**
     * The setter for the name of the card
     * @param x The name to assign
     */
    public void setName(String x){
        this.name = x;
    }

    /**
     * The setter for the the of the player to whom of the card
     * @param player The name of the player to assign
     */
    public void setPlayer(String player){
        this.player = player;
    }

    /**
     * This method is used to place a dice on the WindowPattern card
     * @param d The dice to place
     * @param x The row
     * @param y The column
     * @return If the dice has been placed or not
     */
    public boolean placeDice(Dice d, int x, int y){
        Pos p = new Pos(x,y);
        if(this.getCell(p).isOccupied() || d == null)
            return false;
        else{
            this.getCell(p).setOccupation(true);
            this.getCell(p).setDice(d);
            return true;
        }
    }

    /**
     * Verify if the cell at the specified position is occupied
     * @param p The specified position
     * @return If the cell is occupied
     */
    public boolean isIn(Pos p){
        return this.getCell(p).isOccupied();
    }


    /**
     * Method to find out if the dice specified has the same properties to the one at the specified position
     * @param d The specified dice
     * @param p The specified position
     * @return If the dices have the same properties or not
     */
    public boolean findDice(Dice d, Pos p){
        return (this.getCell(p).getDice().areEquals(d));
    }

    /**
     * Method to add restrictions to a cell of the patternCard
     * @param c The restriction to add
     * @param x The row
     * @param y The column
     */
    public void addRestrictions(char c, int x, int y) {
        Pos p = new Pos(x, y);
        if (Character.isLetter(c)) {
            Property pro = new Property(Colour.isIn(c),false);
            this.getCell(p).setProperty(pro);
        } else if (Character.isDigit(c)) {
            Property pro = new Property(Character.getNumericValue(c));
            this.getCell(p).setProperty(pro);
        }
    }

    /**
     * Method to remove a dice by setting the occupation of the cell to false
     * @param pos The position specified
     */
    public void removeDice(Pos pos){
        this.getCell(pos).setOccupation(false);
    }

    /**
     * Reset the "selected" attribute to false for every dice in the WindowPattern card
     */
    public void resetSelection() {
        matr.forEach(cells -> cells.forEach(cell -> cell.getDice().deSelect()));
    }

    /**
     * Find out if the WindowPattern card doesn't have dice placed
     * @return If the WindowPattern card doesn't have dice placed
     */
    public boolean isEmpty() {
        for (List<Cell> cells:matr) {
            for (Cell c:cells) {
                if(c.isOccupied())
                    return false;
            }
        }
        return true;
    }
}


