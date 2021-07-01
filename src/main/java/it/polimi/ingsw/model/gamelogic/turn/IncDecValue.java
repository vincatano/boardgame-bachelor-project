package it.polimi.ingsw.model.gamelogic.turn;


import it.polimi.ingsw.model.gamedata.Pos;
import it.polimi.ingsw.model.gamedata.gametools.Dice;
import it.polimi.ingsw.model.gamelogic.checker.InspectorContextTool;
import it.polimi.ingsw.model.gamelogic.turn.moveexceptions.WrongMoveException;

/**
 * Class defining the concrete state IncDecValue, in this state the player can only choose a Dice. The toolDice just chosen
 * will represent the new value he want's his chosenDice to assume. This state can be dynamically set only for the toolCard number 1.
 * If the the player choose a Dice then the concrete state will dynamically change.
 */
public class IncDecValue implements TurnState {
    private Turn turn;
    private Dice chosenDice;
    private Pos posChosenDice;
    private InspectorContextTool inspectContextTool;

    /**
     * Classic constructor.
     * @param turn Store the Turn object in order to call methods on it, like changing the concrete state of the turn calling setState().
     * @param chosenDice The Dice the player has just selected.
     * @param posChosenDice The Position of the Dice the player has just selected (chosenDice).
     * @param toolDice The dice has chosen through the toolCard.
     * @param toolPos The position of the the Dice the player has chosen through the toolCard (toolDice).
     */
    public IncDecValue(Turn turn, Dice chosenDice, Pos posChosenDice, Dice toolDice, Pos toolPos) {
        this.turn = turn;
        this.posChosenDice = posChosenDice;
        this.chosenDice = chosenDice;
        this.inspectContextTool = turn.getInspectorContextTool();
    }

    //GETTING MOVE METHODS

    /**
     * {@inheritDoc}
     * In this implementation the method check() on the inspectorContextTool is being called to actually understand whether the move is
     * valid or not. If it is valid then we change state, we throw the exception otherwise.
     */
    @Override
    public void receiveMove(Dice toolDice, Pos toolPos) throws WrongMoveException {
        if(inspectContextTool.checkNumDice(chosenDice,posChosenDice,toolDice,toolPos)) {
            //call modifier so the value of the dice will be changed
            turn.getModifier().changeDiceValue(chosenDice,posChosenDice,toolDice);
            turn.setDynamicState(toolDice,toolPos,new Dice(),new Pos());
        } else {
            throw new WrongMoveException("Mossa sbagliata: Decrementa o aumenta il valore del dado scelto di 1. Non puoi far diventare un 1 in un 6 e viceversa.");
        }
    }
}