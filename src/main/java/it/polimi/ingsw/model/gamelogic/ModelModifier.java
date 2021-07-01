package it.polimi.ingsw.model.gamelogic;

import it.polimi.ingsw.model.gamedata.Player;
import it.polimi.ingsw.model.gamedata.Pos;
import it.polimi.ingsw.model.gamedata.Table;
import it.polimi.ingsw.model.gamedata.gametools.*;
import java.util.List;

/**
 * Class that Modifies the model due to moves done by the player.
 */
public class ModelModifier {
    private static final int NOT_USED = 1;
    private RoundTrack roundTrack;
    private DraftPool draftPool;
    private WindowPatternCard windowPatternCard;
    private DiceBag diceBag;
    private List<ToolCard> toolCards;

    /**
     * Classic constructor
     * @param table Table object that contains model models data
     * @param player Player object representing the player that has done the move.
     */
    public ModelModifier(Table table, Player player) {
        this.roundTrack = table.getRoundTrack();
        this.draftPool = table.getDraftPool();
        this.windowPatternCard = player.getWindowPatternCard();
        this.diceBag = table.getDiceBag();
        this.toolCards = table.getToolCards();
    }

    //Automatic moves

    /**
     * Automatic move that switches the chosenDice with the toolDice, used in ToolCard number 5.
     * @param chosenDice Dice chosen during normal turn
     * @param posChosenDice Position of the dice chosen during normal turn
     * @param toolDice Dice chosen during toolCard
     * @param toolPos Position of the dice chosen during toolCard
     */
    public void switchDice(Dice chosenDice, Pos posChosenDice, Dice toolDice, Pos toolPos) {
        Dice draftDice = draftPool.chooseDice(posChosenDice.getX()); //reference copy
        draftPool.removeDice(posChosenDice.getX()); //remove the dice from the draftPool

        Dice roundDice = roundTrack.getDice(toolPos); //reference copy
        roundTrack.removeDice(toolPos); //remove the dice from the roundTrack

        draftPool.addDice(posChosenDice.getX(),roundDice);
        roundTrack.addDice(toolPos,draftDice);

        //switch also the copy of the dice
        chosenDice.setNumber(roundDice.getNumber());
        chosenDice.setColour(roundDice.getColour().toString());
        toolDice.setNumber(draftDice.getNumber());
        toolDice.setColour(draftDice.getColour().toString());
    }

    /**
     * Automatic move that rolls the chosenDice, used in ToolCard number 6.
     * @param chosenDice Dice chosen during normal turn
     * @param posChosenDice Position of the dice chosen during normal turn
     * @param toolDice Dice chosen during toolCard
     * @param toolPos Position of the dice chosen during toolCard
     */
    public void launchDice(Dice chosenDice, Pos posChosenDice, Dice toolDice, Pos toolPos) {
        chosenDice.rollDice();
        draftPool.chooseDice(posChosenDice.getX()).setNumber(chosenDice.getNumber());
    }

    /**
     * Automatic move that rolls all the dices of the draftPool, used in ToolCard number 7.
     * @param chosenDice Dice chosen during normal turn
     * @param posChosenDice Position of the dice chosen during normal turn
     * @param toolDice Dice chosen during toolCard
     * @param toolPos Position of the dice chosen during toolCard
     */
    public void resetDraftPool(Dice chosenDice, Pos posChosenDice, Dice toolDice, Pos toolPos){
        for(Dice dice: draftPool.getDraftPool()){
            dice.rollDice();
        }
    }

    /**
     * Automatic move that spin the chosenDice, used in ToolCard number 10.
     * @param chosenDice Dice chosen during normal turn
     * @param posChosenDice Position of the dice chosen during normal turn
     * @param toolDice Dice chosen during toolCard
     * @param toolPos Position of the dice chosen during toolCard
     */
    public void spinDice(Dice chosenDice, Pos posChosenDice, Dice toolDice, Pos toolPos) {
        switch (chosenDice.getNumber()) {
            case 1: chosenDice.setNumber(6);
                break;
            case 2: chosenDice.setNumber(5);
                break;
            case 3: chosenDice.setNumber(4);
                break;
            case 4: chosenDice.setNumber(3);
                break;
            case 5: chosenDice.setNumber(2);
                break;
            case 6: chosenDice.setNumber(1);
                break;
                default:
        }

        draftPool.chooseDice(posChosenDice.getX()).setNumber(chosenDice.getNumber());
    }

    /**
     * Automatic move that put back the chosenDice into the DiceBag and pulls out a new one, used in ToolCard number 11.
     * @param chosenDice Dice chosen during normal turn
     * @param posChosenDice Position of the dice chosen during normal turn
     * @param toolDice Dice chosen during toolCard
     * @param toolPos Position of the dice chosen during toolCard
     */
    public void newDice(Dice chosenDice, Pos posChosenDice, Dice toolDice, Pos toolPos) {
        Dice draftDice = draftPool.chooseDice(posChosenDice.getX()); //reference copy
        draftPool.removeDice(posChosenDice.getX()); //remove the dice from the draftPool

        Dice newBagDice = diceBag.pullOut(1).get(0); //reference copy and removes the dice from the bag

        diceBag.addDice(draftDice); //add to the bag the dice chosen by the player
        draftPool.addDice(posChosenDice.getX(),newBagDice);

        chosenDice.setNumber(newBagDice.getNumber());
        chosenDice.setColour(newBagDice.getColour().toString());
    }


    /**
     * Method called when a player chose to use a ToolCard and can actually use it, increments the cost of the toolCard and call
     * updatePlayerTokens() method which update the number of tokens the player have.
     * @param toolCard ToolCard object representing the toolCard the layer has chosen.
     * @param player Player object representing the player that has done the move.
     */
    public void updateToolCardPrice(ToolCard toolCard,Player player) {
        for (ToolCard card: toolCards) {
            if(toolCard.getID() == card.getID()) {
                updatePlayerTokens(player,card.getCost());
                card.setUsed();
            }
        }
    }

    /**
     * Method called by updateToolCardPrice in order to also update the favor tokens of the player.
     * @param player Player object representing the player that has done the move.
     * @param toolCardCost The cost of the toolCard actually before it has been used by this player.
     */
    private void updatePlayerTokens(Player player,int toolCardCost) {
        if(toolCardCost == NOT_USED) {
            player.useToken(false);
        } else {
            player.useToken(true);
        }
    }

    /**
     * Method called when the player moves a dice chosen in the DraftPool
     * @param chosenDice Dice chosen during normal turn
     * @param posChosenDice Position of the dice chosen during normal turn
     * @param pos Position of the cell in which the player wants to move the chosenDice
     */
    public void positionDiceFromDraft(Dice chosenDice, Pos posChosenDice, Pos pos) {
        windowPatternCard.placeDice(draftPool.chooseDice(posChosenDice.getX()), pos.getX(),pos.getY());
        draftPool.removeDice(posChosenDice.getX());
    }

    /**
     * Method called when the player moves a dice chosen in the PatternCard
     * @param chosenDice Dice chosen during normal turn
     * @param posChosenDice Position of the dice chosen during normal turn
     * @param pos Position of the cell in which the player wants to move the chosenDice
     */
    public void positionDiceFromWindow(Dice chosenDice, Pos posChosenDice, Pos pos) {
        Dice windowDice = windowPatternCard.getDice(posChosenDice);
        windowPatternCard.removeDice(posChosenDice);
        windowPatternCard.placeDice(windowDice, pos.getX(),pos.getY());
    }

    /**
     * Method called when the player is using the toolCard 11. More precisely as soon as he chose the new value of the dice.
     * This method will change the value of the dice to the one the player has chosen.
     * @param chosenDice Dice chosen during normal turn
     * @param posChosenDice Position of the dice chosen during normal turn
     * @param toolDice Dice chosen during toolCard
     */
    public void changeDiceValue(Dice chosenDice, Pos posChosenDice, Dice toolDice) {
        //set the value of chosenDice to the toolDice one
        chosenDice.setNumber(toolDice.getNumber());
        draftPool.chooseDice(posChosenDice.getX()).setNumber(toolDice.getNumber());
    }
}
