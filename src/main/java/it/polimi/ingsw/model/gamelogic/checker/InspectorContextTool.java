package it.polimi.ingsw.model.gamelogic.checker;

import it.polimi.ingsw.model.gamedata.Pos;
import it.polimi.ingsw.model.gamedata.gametools.*;

import java.util.List;

/**
 * The class is used to verify dynamically if, chosen the tool card, the chosen dice is taken from the right place.
 * The parameters "pos" passed to the methods represent the exact position in the pattern card, draft pool or round track
 */
public class InspectorContextTool implements InspectorTool {
    private WindowPatternCard windowPatternCard;
    private DraftPool draftPool;
    private RoundTrack roundTrack;
    private int index;
    private Pos specialPos;

    public InspectorContextTool(WindowPatternCard window, DraftPool draftPool, RoundTrack roundTrack){
        this.windowPatternCard=window;
        this.draftPool=draftPool;
        this.roundTrack=roundTrack;
        this.index=0;
    }

    /**
     * For every tool card, except n°1 and n°11, this method is called by patter state. It takes from the chosen tool card the rules (name of methods)
     * to apply for checking dynamically if is taken from the right place and, using Java Reflection, calls on "ruleEngine" the right methods.
     * @param dice Chosen dice.
     * @param pos Position of chosen dice.
     * @param tool Tool card where take the rules.
     * @return True if the dice is taken from the right place.
     */
    public boolean check(Dice dice, Pos pos, ToolCard tool){
        RuleEngineC ruleEngine = new RuleEngineC(dice,pos,windowPatternCard,draftPool,roundTrack);
        List<String> nameMethods = tool.getNameCMethods();
        String method = nameMethods.get(index);
        if(method.equals("inRoundTrS")){
            specialPos=pos;
        }
        if(method.equals("inPatCardS")){
            ruleEngine.setSpecialPos(specialPos);
        }
        boolean result =doMethods(method, ruleEngine);
        if(result) {
            index++;
        }
        return result;
    }

    /**
     * Check if the dice chosen thanks to tool card n°11 respects the rule.
     * @param oldDice Dice pulled out from dice bag.
     * @param oldPos Position of dice on draft pool.
     * @param newDice Dice chosen from player.
     * @param newPos Position of dice chosen from player.
     * @return True if dice is chosen between all possibilities.
     */
    public boolean checkColourDice(Dice oldDice,Pos oldPos, Dice newDice, Pos newPos){
        return oldPos.getX() == newPos.getX() && oldPos.getY() == newPos.getY() && oldDice.getColour().toString().equals(newDice.getColour().toString());
    }

    /**
     * Check if the dice chosen thanks to tool card n°1 respects the rule.
     * @param oldDice Dice to change the value.
     * @param oldPos Position of oldDice on pattern card.
     * @param newDice Dice chosen with new value.
     * @param newPos Position of newDice on pattern card.
     * @return True if player chooses the right value.
     */
    public boolean checkNumDice(Dice oldDice,Pos oldPos, Dice newDice, Pos newPos){
        int oldNum = oldDice.getNumber();
        int newNum = newDice.getNumber();
        if(!(oldPos.getX()==newPos.getX() && (oldPos.getY()==newPos.getY()))){
            return false;
        }
        if(oldNum==1 && newNum==2){
            return true;
        }
        if(oldNum==6 && newNum==5){
            return true;
        }
        return oldNum == newNum + 1 || oldNum == newNum - 1;

    }

    /**
     * This class contains all the implementation of the needed methods that check if the dice is taken from the right place.
     */
    private class RuleEngineC implements RuleEngine{
        Dice dice;
        WindowPatternCard window;
        DraftPool pool;
        RoundTrack roundT;
        Pos pos;
        Pos specialPos;
        private RuleEngineC(Dice dice, Pos pos, WindowPatternCard window, DraftPool draftPool, RoundTrack roundT) {
            this.dice = dice;
            this.window = window;
            this.pool = draftPool;
            this.roundT=roundT;
            this.pos=pos;
        }

        /**
         * Check if dice is taken from draft pool.
         * @return True if is inside it.
         */
        protected boolean inDraftPool(){
            return pool.findDice(dice,pos.getX());

        }
        /**
         * Check if dice is taken from the player's pattern card.
         * @return True if is inside it.
         */
        protected boolean inPatCard(){
            return window.findDice(dice,pos);
        }
        /**
         * Check if dice is taken from the round track.
         * @return True if is inside it.
         */
        protected boolean inRoundTr(){
            return roundT.findDice(dice,pos);
        }

        protected boolean inRoundTrS(){
            return this.inRoundTr();
        }

        protected boolean inPatCardS(){
            return inPatCard() && dice.getColour().areEquals(roundT.getDice(specialPos).getColour());
        }
        protected void setSpecialPos(Pos specialPos){
            this.specialPos=specialPos;
        }

    }
}
