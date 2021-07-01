package it.polimi.ingsw.model.gamelogic.turn;

/**
 * Class defining the concrete state EndTurn, in this state the turn automatically ends for there isn't any valid move the player can make.
 */
public class EndTurn implements TurnState {
    private Turn turn;

    /**
     * Classic constructor
     * @param turn Store the Turn object in order to call methods on it.
     */
    public EndTurn(Turn turn) {
        this.turn = turn;
    }

    @Override
    public void automaticPass() {
        turn.notifyEndTurn();
    }
}
