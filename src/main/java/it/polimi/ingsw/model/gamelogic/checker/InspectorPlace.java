package it.polimi.ingsw.model.gamelogic.checker;

import it.polimi.ingsw.model.gamedata.gametools.Cell;
import it.polimi.ingsw.model.gamedata.gametools.Dice;
import it.polimi.ingsw.model.gamedata.Pos;
import it.polimi.ingsw.model.gamedata.gametools.WindowPatternCard;


/**
 * This class check if the position rules are checked.
 */
public class InspectorPlace {
    static final int MINCOL=0;
    static final int MAXCOL=4;
    static final int MINROW=0;
    static final int MAXROW=3;


    /**
     * Check if the dice could be placed on the chosen position at first turn
     * @param dice Dice to place.
     * @param pos Chosen position.
     * @param window Player's pattern card where check rules.
     * @return True if conditions are verified.
     */
    private boolean checkFirst(Dice dice, Pos pos, WindowPatternCard window){
        return (checkPos(pos, window) && checkColour(window, pos, dice) && checkNumber(window, pos, dice) && checkFrame(pos));
    }

    /**
     * Check if the dice could be placed on the chosen position verifying if is the first placement.
     * @param dice Dice to place.
     * @param pos Chosen position.
     * @param window Player's pattern card where check rules.
     * @return True if conditions are verified.
     */
    public boolean check(Dice dice, Pos pos, WindowPatternCard window) {
        if(window.isEmpty()){
            return checkFirst(dice,pos,window);
        }else{
          return checkNormal(dice,pos,window);
        }
    }

    /**
     * Check the normal placement restrictions.
     * @param dice Dice to place.
     * @param pos Chosen position.
     * @param window Player's pattern card where check rules.
     * @return True if conditions are verified.
     */

    private boolean checkNormal(Dice dice, Pos pos, WindowPatternCard window) {
        return (checkPos(pos,window) && checkColour(window,pos,dice) && checkNumber(window,pos,dice) && checkCol(pos,window,dice) && checkRow(pos,window,dice) && !particularFrame(pos,window));

    }

    /**
     * Check if pos could be out of window's frame and if the position is occupied.
     * @param pos Chosen position
     * @param window Player's pattern card
     * @return True if conditions are verified.
     */
    protected boolean checkPos(Pos pos,WindowPatternCard window) {
        return pos.getX()>= MINROW && pos.getX()<=MAXROW && pos.getY()>=MINCOL && pos.getY()<=MAXCOL && (!window.getCell(pos).isOccupied());
    }

    /**
     * Check colour restriction on the chosen pos.
     * @param window Player's pattern card.
     * @param pos Chosen position.
     * @param dice Dice to place.
     * @return True if the Cell doesn't have colour restriction or it is verified.
     */
    protected boolean checkColour(WindowPatternCard window, Pos pos, Dice dice){
        Cell c =window.getCell(pos);
        return ((c.getProperty().getColour().toString().equals("W")) || (c.getProperty().getColour().areEquals(dice.getColour())));
    }

    /**
     * Check number restriction on the chosen pos.
     * @param window Player's pattern card.
     * @param pos Chosen position.
     * @param dice Dice to place.
     * @return True if the Cell doesn't have number restriction or it is verified.
     */
    protected boolean checkNumber(WindowPatternCard window, Pos pos, Dice dice){
        Cell c= window.getCell(pos);
        return ((c.getProperty().getNumber()== 0) || (c.getProperty().getNumber()==dice.getNumber()));
        }

    /**
     * Check if the chosen position is on the frame of window pattern card.
     * @param pos Chosen pos.
     * @return True if is on the frame.
     */
    protected boolean checkFrame(Pos pos){
        int x = pos.getX();
        int y = pos.getY();
        if((x>MINROW && x<MAXROW) && (y==MINCOL || y==MAXCOL)){
                return true;
        }
        else if(x == MINROW && y == MINCOL || x == MINROW && y == MAXCOL || x == MAXROW && y == MINCOL || x == MAXROW && y == MAXCOL)
            return true;
        return(y>MINCOL && y<MAXCOL && (x == MINROW || (x == MAXROW)));

    }

    /**
     * Check if the chosen position check the colour and number restriction on the column nearby.
     * @param pos Chosen position.
     * @param window Player's window pattern card.
     * @param dice Dice to place.
     * @return True if conditions are verified.
     */
    protected boolean checkRow(Pos pos,WindowPatternCard window, Dice dice){
        Pos p = new Pos(pos.getX(),pos.getY());
        if(p.getX()==MINROW){
            p.setX(p.getX()+1);
            return checkSide(p,window,dice);
        }else if(p.getX()==MAXROW){
            p.setX(p.getX()-1);
            return checkSide(p,window,dice);
        }else{
            p.setX(p.getX()+1);
            if(checkSide(p,window,dice)){
                p.setX(p.getX()-2);
                return checkSide(p, window, dice);
            }
            return false;
        }
    }
    /**
     * Check if the chosen position check the colour and number restriction on the rows nearby.
     * @param pos Chosen position.
     * @param window Player's window pattern card.
     * @param dice Dice to place.
     * @return True if conditions are verified.
     */
    protected boolean checkCol(Pos pos,WindowPatternCard window, Dice dice){
        Pos p = new Pos(pos.getX(),pos.getY());
        if(p.getY()==MINCOL){
            p.setY(p.getY()+1);
            return checkSide(p,window,dice);
        }else if(p.getY()==MAXCOL){
            p.setY(p.getY()-1);
            return checkSide(p,window,dice);
        }else{
            p.setY(p.getY()+1);
                if(checkSide(p,window,dice)){
                    p.setY(p.getY()-2);
                    return checkSide(p, window, dice);
                }
            return false;
        }

    }

    /**
     * Check if the side dice respect the number and colour restriction
     * @param p Position where check the nearby dice.
     * @param window Player's pattern card.
     * @param dice Dice to place
     * @return True if conditions are verified.
     */
    private boolean checkSide(Pos p,WindowPatternCard window,Dice dice) {
        if(window.isIn(p)){
            Dice d = window.getDice(p);
            return (d.getNumber() != dice.getNumber() && !d.getColour().areEquals(dice.getColour())) || d.isSelected();
        }else{
            return true;
        }
    }

    /**
     * This method check if the placing is far from other dice.
     * @return True if conditions are respected.
     */
    protected boolean particularFrame(Pos pos, WindowPatternCard window) {
        int x = pos.getX();
        int y = pos.getY();
        for (int i = -1; i<=1; i++){
            for(int j =1 ; j>= -1; j--){
                if(x+i>=MINROW && x+i<=MAXROW && y+j>=MINCOL && y+j<=MAXCOL && window.getCell(new Pos(x+i,y+j)).isOccupied()&&
                        !window.getDice(new Pos(x+i,y+j)).isSelected()){
                    return false;
                }
            }
        }
        return true;
    }
}
