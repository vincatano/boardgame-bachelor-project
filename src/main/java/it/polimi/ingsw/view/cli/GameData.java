package it.polimi.ingsw.view.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represent the model for the cliHandler and is modified every time the model changes.
 */
public class GameData {
    private String player;
    private List<String> draftPool;
    private List<List<String>> roundTrack;
    private String[][] restrictions;
    private int lastSelectedDice;

    GameData(){
        roundTrack= new ArrayList<>();
        for(int i = 0; i<10;i++){
            roundTrack.add(i,new ArrayList<>());
        }
        restrictions = new String[4][5];
        lastSelectedDice=-1;
    }

    /**
     *
     * @param roundTrack set round track values
     */
    void setRoundTrack(String roundTrack) {
        String[] dices = roundTrack.split(",");
        if (roundTrack.length()>0) {
            for (String dice : dices) {
                int x = Integer.parseInt(String.valueOf(dice.charAt(2)));
                int y = Integer.parseInt(String.valueOf(dice.charAt(3)));
                String tmp = String.valueOf(dice.charAt(1));
                tmp = tmp+"," + (dice.charAt(0));
                this.roundTrack.get(x).add(y, tmp);
            }
        }
    }

    void setName(String name){
        this.player=name;
    }

    /**
     *
     * @param draftPool set draft pool values
     */
    void setDraftPool(String draftPool) {
        List<String> splitted = Arrays.asList(draftPool.split(","));
        this.draftPool = new ArrayList<>();
        for(int i = 0; i< splitted.size(); i++){
            String tmp;
            tmp = String.valueOf(splitted.get(i).charAt(1));
            tmp= tmp +","+(splitted.get(i).charAt(0));
            this.draftPool.add(tmp);
        }

    }

    /**
     *
     * @param restrictions set value of dice on pattern card
     */
    void setRestrictions(String restrictions) {
        List<String> dices = Arrays.asList(restrictions.split(","));
        if(dices.size()>1 && dices.get(0).equals(player)) {
            List<String> temp = new ArrayList<>();
            for(int i=1;i<dices.size();i++){
                temp.add(dices.get(i));
            }
            for (String dice : temp) {
                int x = Integer.parseInt(String.valueOf(dice.charAt(2)));
                int y = Integer.parseInt(String.valueOf(dice.charAt(3)));
                String tmp = String.valueOf(dice.charAt(1));
                tmp = tmp + "," + (dice.charAt(0));
                this.restrictions[x][y] = tmp;
            }
        }
    }

    /**
     *
     * @param x index
     * @return value at x index
     */
    public String getDraft(int x) {
        lastSelectedDice=x;
        return draftPool.get(x);
    }

    /**
     *
     * @param x index row
     * @param y index column
     * @return dice value at position x,y
     */
    public String getWindow(int x, int y) {
        return restrictions[x][y];
    }

    /**
     *
     * @param x index row
     * @param y index column
     * @return dice value at position x,y
     */
    public String getRound(int x, int y) {
        return roundTrack.get(x).get(y);
    }

    /**
     * Used for toolcard 11
     * @param num number chosen
     * @return value of dice on draft at index = lastSelectedDice with selected num value
     */
    public String getChangedDice(int num) {
        String s = draftPool.get(lastSelectedDice);
        return num+","+s.charAt(2)+lastSelectedDice;
    }
}
