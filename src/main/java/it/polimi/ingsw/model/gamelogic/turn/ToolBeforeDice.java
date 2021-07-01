package it.polimi.ingsw.model.gamelogic.turn;

import it.polimi.ingsw.model.gamedata.gametools.Dice;
import it.polimi.ingsw.model.gamedata.Pos;
import it.polimi.ingsw.model.gamelogic.checker.InspectorContext;
import it.polimi.ingsw.model.gamelogic.turn.moveexceptions.WrongMoveException;

/**
 * Class defining the concrete state ToolBeforeDice, in this state the player can chose a Dice, or Pass move.
 * If he choose a Dice the next state would be "ChooseDice2". Finally if he choose to pass
 * the next state would be "EndTurn".
 */
public class ToolBeforeDice implements TurnState {
    private Turn turn;
    private InspectorContext inspectorContext;

    public ToolBeforeDice(Turn turn) {
        this.turn = turn;
        this.inspectorContext = turn.getInspectorContext();
    }

    //GETTING MOVE METHODS

    /**
     * {@inheritDoc}
     * In this implementation the method check() on the inspectorContext is being called to actually understand whether the move is
     * valid or not. If it is valid then we change state, we throw the exception otherwise.
     */
    @Override
    public void receiveMove(Dice dice,Pos pos) throws WrongMoveException {
        if(inspectorContext.check(dice,pos,turn.getPlayer().getDraftPool())) {
            turn.setState(new ChooseDice2(turn, dice, pos, new Dice(), new Pos()));
        } else {
            throw new WrongMoveException("Mossa sbagliata: Non è possibile scegliere questo dado.");
        }
    }

    @Override
    public void receiveMove(String pass) {
        turn.setState(new EndTurn(turn));
    }
}
