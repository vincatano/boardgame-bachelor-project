package it.polimi.ingsw.model.gamedata.gametools;


import it.polimi.ingsw.model.gamedata.Colour;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class represents the dicebag
 */
public class DiceBag {
    private List<Dice> diceContainer = new ArrayList<>(90);
    private int numPlayers;

    /**
     * The classic constructor that adds 18 dices for every colour to the arraylist
     */
    public DiceBag(){
        int i;
        int j;
        Colour[] coll = new Colour[5];
        coll[0] = Colour.BLUE;
        coll[1] = Colour.GREEN;
        coll[2] = Colour.PURPLE;
        coll[3] = Colour.RED;
        coll[4] = Colour.YELLOW;
        for(i = 0; i <5; i++){
            for(j = 0; j <18; j++){
                Dice x = new Dice(coll[i]);
                diceContainer.add(x);
            }
        }
    }

    /**
     * The setter method for the number of the players
     * @param n The number of players
     */
    public void setNumPlayers(int n){
        if(n > 0 && n < 5)
            this.numPlayers = n;
        else
            this.numPlayers = 0;
    }

    /**
     * The getter method for the remaining dices
     * @return The number of remaining dices
     */
    public int remainingDices(){
        return diceContainer.size();
    }

    /**
     * Pullout (2*numberOfPlayers + 1) dices from the bag
     * @return The dices pulled out
     */
    public List<Dice> pullOut(){
        int dimension = (2*numPlayers) + 1;
        List<Dice> result = new ArrayList<>(dimension);
        randomPullOut(dimension,result, diceContainer.size());
        return result;
    }

    /**
     * Add a dice into the dicebag
     * @param dice The dice to add
     */
    public void addDice(Dice dice){
        this.diceContainer.add(dice);
    }


    /**
     * PullOut the number requested of dices
     * @param number The number of dices to pullOut
     * @return The dices pulled out
     */
    public List<Dice> pullOut(int number){
        List<Dice> tmp = new ArrayList<>(number);
        randomPullOut(number,tmp, diceContainer.size());
        return tmp;
    }

    /**
     * The method that really pulls out randomly the dices from the bag
     * @param number Number of dices to pull out
     * @param tmp The list of dices to fill
     * @param cont The number of dices remaining in the dicebag
     */
    private void randomPullOut(int number, List<Dice> tmp, int cont){
        int randomNum;
        Random rand = new Random();
        Dice randomdice;
        for(int k = 0; k < number; k++) {
            randomNum = rand.nextInt((cont));
            randomdice = diceContainer.get(randomNum);
            tmp.add(randomdice);
            diceContainer.remove(randomNum);
            cont--;
        }
    }
}
