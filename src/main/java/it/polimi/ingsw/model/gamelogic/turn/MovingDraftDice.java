package it.polimi.ingsw.model.gamelogic.turn;

import it.polimi.ingsw.model.gamedata.Pos;
import it.polimi.ingsw.model.gamedata.gametools.Dice;
import it.polimi.ingsw.model.gamelogic.checker.InspectorPlaceTool;
import it.polimi.ingsw.model.gamelogic.turn.moveexceptions.WrongMoveException;

/**
 * Class defining the concrete state MovingDraftDice,in this state a player can choose a Position and the dice he
 * has previously chosen comes from the draftPool, or he can choose to pass the turn.
 * This state can be dynamically set only for toolCards number 8 and 9.
 * If the the player choose a Position then the concrete state will dynamically change.
 */
public class MovingDraftDice implements TurnState {
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
    public MovingDraftDice(Turn turn, Dice chosenDice, Pos posChosenDice, Dice toolDice, Pos toolPos) {
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
        if (inspectorPlaceTool.check(chosenDice, pos, turn.getToolCard())) {
            //call modifier
            turn.getModifier().positionDiceFromDraft(chosenDice, posChosenDice, pos);
            turn.setDynamicState(chosenDice, posChosenDice, toolDice, toolPos);
        } else {
            turn.resetSelectionDice();
            throw new WrongMoveException("Mossa sbagliata: selezionare una posizione della Vetrata che rispetti le regole di piazzamento e relative alla carta selezionata.");
        }
        turn.resetSelectionDice();
    }

    @Override
    public void receiveMove(String pass) {
        turn.setState(new EndTurn(turn));
    }
}