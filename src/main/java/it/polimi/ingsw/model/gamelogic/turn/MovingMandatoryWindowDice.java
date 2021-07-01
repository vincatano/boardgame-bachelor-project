package it.polimi.ingsw.model.gamelogic.turn;

import it.polimi.ingsw.model.gamedata.Pos;
import it.polimi.ingsw.model.gamedata.gametools.Dice;
import it.polimi.ingsw.model.gamelogic.checker.InspectorPlaceTool;
import it.polimi.ingsw.model.gamelogic.turn.moveexceptions.WrongMoveException;

/**
 * Class defining the concrete state MovingMandatoryWindowDice,in this state a player can only choose a Position and the dice he
 * has previously chosen comes from his windowPatternCard. This state can be dynamically set only for toolCards number 2,3,4,12.
 * If the the player choose a Position then the concrete state will dynamically change.
 */
public class MovingMandatoryWindowDice implements TurnState {
    private Turn turn;
    private Dice chosenDice;
    private Dice toolDice;
    private Pos posChosenDice;
    private Pos toolPos;
    private InspectorPlaceTool inspectorPlaceTool;

    /**
     * Classic constructor.
     * @param turn Store the Turn object in order to call methods on it, like changing the concrete state of the turn calling setState().
     * @param chosenDice The Dice the player has just selected.
     * @param posChosenDice The Position of the Dice the player has just selected (chosenDice).
     * @param toolDice The dice has chosen through the toolCard.
     * @param toolPos The position of the the Dice the player has chosen through the toolCard (toolDice).
     */
    public MovingMandatoryWindowDice(Turn turn, Dice chosenDice, Pos posChosenDice, Dice toolDice, Pos toolPos) {
        this.turn = turn;
        this.toolDice = toolDice;
        this.toolPos = toolPos;
        this.posChosenDice = posChosenDice;
        this.chosenDice = chosenDice;
        this.inspectorPlaceTool = turn.getInspectorPlaceTool();
    }

    //GETTING MOVE METHODS

    /**
     * {@inheritDoc}
     * In this implementation the method check() on the inspectorPlaceTool is being called to actually understand whether the move is
     * valid or not. If it is valid then we change state, we throw the exception otherwise.
     */
    @Override
    public void receiveMove(Pos pos) throws WrongMoveException {
        if(inspectorPlaceTool.check(chosenDice,pos,turn.getToolCard())) {
            //call modifier
            turn.getModifier().positionDiceFromWindow(chosenDice,posChosenDice,pos);
            turn.setDynamicState(chosenDice,posChosenDice,toolDice,toolPos);
        } else {
            turn.resetSelectionDice();
            throw new WrongMoveException("Mossa sbagliata: selezionare una posizione della Vetrata che rispetti le regole di piazzamento della relativa carta.");
        }
        turn.resetSelectionDice();
    }
}
