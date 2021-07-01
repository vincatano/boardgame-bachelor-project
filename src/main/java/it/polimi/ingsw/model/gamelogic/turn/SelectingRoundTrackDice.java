package it.polimi.ingsw.model.gamelogic.turn;

import it.polimi.ingsw.model.gamedata.Pos;
import it.polimi.ingsw.model.gamedata.gametools.Dice;
import it.polimi.ingsw.model.gamelogic.checker.InspectorContextTool;
import it.polimi.ingsw.model.gamelogic.turn.moveexceptions.WrongMoveException;

/**
 * Class defining the concrete state SelectingRoundTrackDice, in this state the player can choose a Dice that belongs to the roundTrack,
 * or he can choose to pass the round. This state can be dynamically set only for the toolCards number 5,12.
 * If the the player choose a Dice then the concrete state will dynamically change.
 */
public class SelectingRoundTrackDice implements TurnState {
    private Turn turn;
    private Dice chosenDice;
    private Pos posChosenDice;
    private InspectorContextTool inspectorContextTool;

    /**
     * Classic constructor.
     * @param turn Store the Turn object in order to call methods on it, like changing the concrete state of the turn calling setState().
     * @param chosenDice The Dice the player has just selected.
     * @param posChosenDice The Position of the Dice the player has just selected (chosenDice).
     * @param toolDice The dice has chosen through the toolCard.
     * @param toolPos The position of the the Dice the player has chosen through the toolCard (toolDice).
     */
    public SelectingRoundTrackDice(Turn turn, Dice chosenDice, Pos posChosenDice, Dice toolDice, Pos toolPos) {
        this.turn = turn;
        this.chosenDice = chosenDice;
        this.posChosenDice = posChosenDice;
        this.inspectorContextTool = turn.getInspectorContextTool();
    }

    //GETTING MOVE METHODS

    /**
     * {@inheritDoc}
     * In this implementation the method check() on the inspectorContextTool is being called to actually understand whether the move is
     * valid or not. If it is valid then we change state, we throw the exception otherwise.
     */
    @Override
    public void receiveMove(Dice toolDice,Pos toolPos) throws WrongMoveException {
        if(inspectorContextTool.check(toolDice,toolPos,turn.getToolCard())) {
            turn.setDynamicState(chosenDice,posChosenDice,toolDice,toolPos);
        } else {
            throw new WrongMoveException("Mossa sbagliata: non è possibile scegliere questo dado.");
        }
    }

    @Override
    public void receiveMove(String pass) {
        turn.setState(new EndTurn(turn));
    }
}
