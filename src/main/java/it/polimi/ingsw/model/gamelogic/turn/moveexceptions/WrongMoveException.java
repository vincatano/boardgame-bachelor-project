package it.polimi.ingsw.model.gamelogic.turn.moveexceptions;

/**
 * Exception class defining a WrongMoveException that is thrown when a player do a wrong move, in order to let him know that the move he has done is wrong.
 */
public class WrongMoveException extends Exception {
    /**
     * Classic constructor
     * @param message String that describe the type of wrong move.
     */
    public WrongMoveException(String message) {
        super(message);
    }
}