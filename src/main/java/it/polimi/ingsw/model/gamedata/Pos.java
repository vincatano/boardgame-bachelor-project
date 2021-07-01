package it.polimi.ingsw.model.gamedata;

/**
 * A class representing the position
 */
public class Pos {
    private int x;
    private int y;

    /**
     * The classic constructor
     */
    public Pos(){
        this.x = -1;
        this.y = -1;
    }

    /**
     * @param x The row
     * @param y The column
     */
    public Pos(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * @return The row
     */
    public int getX(){
        return this.x;
    }

    /**
     * @return The column
     */
    public int getY(){
        return this.y;
    }

    /**
     * Set the row
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * Set the column
     */
    public void setY(int y){
        this.y = y;
    }
}
