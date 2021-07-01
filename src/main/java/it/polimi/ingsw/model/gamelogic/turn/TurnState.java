package it.polimi.ingsw.model.gamelogic.turn;

import it.polimi.ingsw.model.gamedata.gametools.Dice;
import it.polimi.ingsw.model.gamedata.Pos;
import it.polimi.ingsw.model.gamedata.gametools.ToolCard;
import it.polimi.ingsw.model.gamelogic.turn.moveexceptions.WrongMoveException;

/**
 * Interface implemented by concrete states object, defines default methods for them.
 */
public interface TurnState {

    /**
     * This method is called on concrete states when the player does a move, more precisely when he choose a ToolCard.
     * As default throws a WrongMoveException.
     * @param toolCard The toolCard the player has chosen
     * @throws WrongMoveException Describe the type of wrong move the player has done.
     */
    default void receiveMove(ToolCard toolCard) throws WrongMoveException {
        throw new WrongMoveException("Mossa sbagliata: non è possibile scegliere una Carta Strumento in questa fase del turno.");
    }

    /**
     * This method is called on concrete states when the player does a move, more precisely when he choose a Dice.
     * As default throws a WrongMoveException.
     * @param dice The Dice the player has chosen
     * @param pos The position of the dice the player has chosen
     * @throws WrongMoveException Describe the type of wrong move the player has done.
     */
    default void receiveMove(Dice dice,Pos pos) throws WrongMoveException {
        throw new WrongMoveException("Mossa sbagliata: non è possibile scegliere un Dado in questa fase del turno.");
    }

    /**
     * This method is called on concrete states when the player does a move, more precisely when he choose a position to move the dice
     * previously chosen. As default throws a WrongMoveException.
     * @param pos The position the player wants to move a dice to.
     * @throws WrongMoveException Describe the type of wrong move the player has done.
     */
    default void receiveMove(Pos pos) throws WrongMoveException {
        throw new WrongMoveException("Mossa sbagliata: non è possibile posizionare un dado in questa fase del turno.");
    }

    /**
     * This method is called on concrete states when the player does a move, more precisely when he choose to pass the turn.
     * As default throws a WrongMoveException.
     * @param pass A string containing "pass" in order to recognize he want's to pass his turn.
     * @throws WrongMoveException Describe the type of wrong move the player has done.
     */
    default void receiveMove(String pass) throws WrongMoveException {
        throw new WrongMoveException("Non è possibile passare il turno: finire la mossa prima di passare.");
    }

    /**
     * This method is called to automatically pass the turn of a player when he can no longer play a move. Set to default as empty
     * and overridden only by the concrete state "EndTurn".
     */
    default void automaticPass() {
    }
}