package it.polimi.ingsw.model.gamedata;

import it.polimi.ingsw.model.gamedata.gametools.Cell;
import it.polimi.ingsw.model.gamedata.gametools.Dice;
import it.polimi.ingsw.model.gamedata.gametools.WindowPatternCard;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * The class used to represent the rules to check tue calculation of the points wiht the Objective cards
 */
public class Rules {

    private List<String> myRules;
    private String direction;

    /**
     * Classic constructor
     */
    public Rules(){
        myRules = new ArrayList<>();
        direction = "";
    }


    /**
     * Getter of the rules
     * @return The rules
     */
    public List<String> getRules(){
        return this.myRules;
    }

    /**
     * Method used to distinguish the method to call depending on the rule
     * @param rule The rule to check
     * @param window The window on which the rules need to be checked
     * @return The points obtained
     */
    public int verify(String rule, WindowPatternCard window){
        int result = 0;
        switch (rule){
            case "-":
                break;
            case "1,2":
            case "3,4":
            case "5,6":
                result = this.couple(window,(rule.charAt(0)-'0'),(rule.charAt(2)-'0'));
                break;
            case "1,2,3,4,5,6":
                result = this.five(window);
                break;
            case "Y,G,B,P,R":
                result = this.varietyColours(window);
                break;
            case "Y":
            case "G":
            case "B":
            case "P":
            case "R":
                result = this.countColor(window,Colour.isIn(rule.charAt(0)));
                break;
            case "ROW":
                direction = rule;
                break;
            case"COLUMN":
                direction = rule;
                break;
            case "NOCOLOR":
            case "NOVALUE":
                result = this.near(window,direction,rule);
                break;
            case "COLOR":
                if(direction.equals("DIAGONAL"))
                    result = this.diagonal(window);
                break;
            case "DIAGONAL":
                direction = rule;
                break;
                default:
        }
        return result;
    }

    /**
     * The method used to check the objective cards 5,6,7
     * @param window The Windowpattern card to check
     * @param first The number of the first of the two dices
     * @param second The number of the second of the two dices
     * @return
     */
    private int couple(WindowPatternCard window, int first, int second){
        int countfirst = 0;
        int countsecond = 0;
        int dicenumber;
        List<List<Cell>> w = window.getMatr();


        for(List<Cell> cells: w){
            for (Cell c: cells){
                if(c.isOccupied()){
                    dicenumber = c.getDice().getNumber();
                    if(dicenumber==first)
                        countfirst++;
                    else if(dicenumber == second)
                        countsecond++;
                }
            }
        }
        if(countfirst < countsecond)
            return countfirst;
        else
            return countsecond;
    }

    /**
     * Method used to check the rule of the objective card 8
     * @param window The WindowPattern card to check
     * @return The points earned with this rule
     */
    private int five(WindowPatternCard window){
        int[] arrayofvalues = new int[6];
        int min = 20;
        List<List<Cell>> w = window.getMatr();
        for(List<Cell> cells: w){
            for(Cell c: cells){
                if(c.isOccupied()) {
                    arrayofvalues[(c.getDice().getNumber()-1)]++;
                }
            }
        }
        for(int h: arrayofvalues){
            if(h == 0)
                return 0;
            else if(h < min)
                min = h;
        }
        if(min == 20)
            return 0;
        else
            return min;
    }

    /**
     * Method used to check the rule of the objective card 10
     * @param window The WindowPattern card to check
     * @return The points earned with this rule
     */
    private int varietyColours(WindowPatternCard window){
        int[] arrayOfValues = new int[5];
        int min = 20;
        List<List<Cell>> w = window.getMatr();
        for(List<Cell> cells: w){
            for(Cell cell: cells){
                if(cell.isOccupied()) {
                    switch (cell.getDice().getColour().toString()) {
                        case "Y":
                            arrayOfValues[0]++;
                            break;
                        case "G":
                            arrayOfValues[1]++;
                            break;
                        case "P":
                            arrayOfValues[2]++;
                            break;
                        case "R":
                            arrayOfValues[3]++;
                            break;
                        case "B":
                            arrayOfValues[4]++;
                            break;
                        default:
                    }
                }
            }
        }
        for(int h: arrayOfValues){
            if(h == 0)
                return 0;
            else if(h < min)
                min = h;
        }
        if(min == 20)
            min = 0;
        return min;
    }

    /**
     * Method used to check the rule of the private objective cards
     * @param window The WindowPattern card to check
     * @return The points earned with this rule
     */
    private int countColor(WindowPatternCard window, Colour c){
        List<List<Cell>> w = window.getMatr();
        String color = c.toString();
        int cont = 0;
        for(List<Cell> cells: w){
            for(Cell cell: cells){
                if(cell.isOccupied() && cell.getDice().getColour().toString().equals(color))
                    cont = cont + cell.getDice().getNumber();
            }
        }
        return cont;
    }

    /**
     * Method used to check the rule of the objective card 1,2
     * @param window The WindowPattern card to check
     * @param direction The direction to check(horizontal or vertical)
     * @param type The type to check (colour or number)
     * @return The points earned with this rule
     */
    private int near(WindowPatternCard window, String direction, String type){
        int result = 0;
        boolean column = direction.equals("COLUMN");
        if (type.equals("NOVALUE")) {
            result = this.columnOrRow(column,false,window);
        }
        else if (type.equals("NOCOLOR")) {
                result = this.columnOrRow(column,true,window);
            }

        return result;
    }

    /**
     * Call the right method depending if you wanto to check on the row or thr column and on the colour or the number
     * @param column If the check goes on the column
     * @param colour If the check is for colour or for number
     * @param window The WindowPattern card to check
     * @return The result of the check
     */
    private int columnOrRow(boolean column, boolean colour, WindowPatternCard window){
        int i;
        int x = window.getMatr().size();
        int result = 0;
        if (!column) {
            for (i = 0; i < x; i++) {
                if (allDifferent(window.getMatr().get(i), colour, 5))
                    result++;
            }
        } else if(!colour) {
            result = this.differentColumnNumber(window);
        } else {
            result = this.differentColumnColor(window);
        }
        return result;
    }

    /**
     * Method called to check on the column on the number
     * @param window The WindowPattern card to check
     * @return The result of the check
     */
    private int differentColumnNumber(WindowPatternCard window){
        int result = 0;
        Pos pos = new Pos(0,0);
        List<Character> colours = new ArrayList<>();
        List<List<Cell>> w = window.getMatr();
        int i;
        int j;
        int x = w.size();
        int y = w.get(0).size();

        for (i = 0; i < y; i++) {
            pos.setY(i);
            for (j = 0; j < x; j++) {
                pos.setX(j);
                if(window.getCell(pos).isOccupied() && !colours.contains(window.getDice(pos).getColour().toString().charAt(0))){
                    colours.add(window.getDice(pos).getColour().toString().charAt(0));
                }
            }
            if(colours.size()==4)
                result++;
            colours.clear();
        }
        return result;
    }

    /**
     * Method called to check on the column on the number
     * @param window The WindowPattern card to check
     * @return The result of the check
     */
    private int differentColumnColor(WindowPatternCard window){
        int result = 0;
        Pos pos = new Pos(0,0);
        List<Integer> numbers = new ArrayList<>();
        List<List<Cell>> w = window.getMatr();
        int i;
        int j;
        int x = w.size();
        int y = w.get(0).size();

        for (i = 0; i < y; i++) {
            pos.setY(i);
            for (j = 0; j < x; j++) {
                pos.setX(j);
                if(window.getCell(pos).isOccupied() && !numbers.contains(window.getDice(pos).getNumber())){
                    numbers.add((window.getDice(pos).getNumber()));
                }
            }
            if(numbers.size()==4)
                result++;
            numbers.clear();
        }
        return result;
    }

    /**
     * Method used to check the rule of the objective card 9
     * @param window The WindowPattern card to check
     * @return The points earned with this rule
     */
    private int diagonal(WindowPatternCard window){
        List<List<Cell>> w = window.getMatr();
        boolean[][] verified = new boolean[4][5];
        int result = 0;
        int j;
        int x = w.size()-1;
        int y = w.get(0).size()-1;
        for(int i = 0; i < 4; i++){
            for(int k = 0; k < 5; k++){
                verified[i][k] = false;
            }
        }
        for(int i = 0; i < x; i++){
            for(j = 0; j < y; j++){
                result = result + this.toAdd(i,j,w,verified);
            }
        }
        return result;
    }


    /**
     * @param i The row
     * @param j The column
     * @param w The windowpattern card
     * @param verified The matrix that indicates if a cell has been already verified
     * @return
     */
    private int toAdd(int i, int j, List<List<Cell>> w, boolean[][] verified){
        int adder;
        int result = 0;
        if(j != 4 && verifySameColour(w,i,j,i+1,j+1)){
            if(verified[i][j] || verified[i+1][j+1])
                adder = 1;
            else
                adder = 2;
            result = result + adder;
            verified[i][j] = true;
            verified[i+1][j+1] = true;
        }
        if(j != 0 && verifySameColour(w,i,j,i+1,j-1)){
            if(verified[i][j] || verified[i+1][j-1])
                adder = 1;
            else
                adder = 2;
            result = result + adder;
            verified[i][j] = true;
            verified[i+1][j-1] = true;
        }
        return result;
    }

    /**
     * Method used to check if the two cells taken from the 4 indices are occupied and have the same colour
     * @param matrix The WindowPattern card
     * @param i1 The first index
     * @param i2 The second index
     * @param i3 The third index
     * @param i4 The fourth index
     * @return If the two cells are occupied and have the same colour
     */
    private boolean verifySameColour(List<List<Cell>> matrix, int i1, int i2, int i3, int i4){
        return (matrix.get(i1).get(i2).isOccupied() && matrix.get(i3).get(i4).isOccupied() &&
                matrix.get(i1).get(i2).getDice().getColour().areEquals(matrix.get(i3).get(i4).getDice().getColour()));
    }

    /**
     * The methods that checks if the row has dices with all different colours or numbers
     * @param cells The List representing the rows
     * @param colour If you need to check the colour or the numbers
     * @param value How many dices should found
     * @return If they're different or not
     */
    private boolean allDifferent(List<Cell> cells,boolean colour,int value){
        if(colour)
            return(cells.parallelStream().filter(Cell::isOccupied).map(Cell::getDice).map(Dice::getColour).distinct().collect(Collectors.toList()).size() == value);
        else
            return(cells.parallelStream().filter(Cell::isOccupied).map(Cell::getDice).map(Dice::getNumber).distinct().collect(Collectors.toList()).size() == value);
    }

}
