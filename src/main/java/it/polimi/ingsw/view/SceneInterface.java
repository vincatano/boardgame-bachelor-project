package it.polimi.ingsw.view;

/**
 * This interface is used both by the CLI and the GUI.
 */
public interface SceneInterface {

    /**
     * This method is used to show to the users an error that has occurred
     * @param alertMessage Contains the alert message that needs to be shown to the user.
     */
    default void handleAlert(String alertMessage) {

    }

    /**
     * This method is called as soon as the client connect to the server
     * @param messageConnection Contains the message to display that the client has correctly connected to the server
     */
    default void handleClientConnected(String messageConnection) {

    }

    /**
     * This method is called as soon as a player connect, disconnect or reconnect to a match.
     * @param playerlist String containing the list of player currently connected to the match.
     */
    default void handleConnectedPlayers(String playerlist) {

    }

    /**
     * This method is used to communicate the second at which the timer is.
     * @param timer Time in seconds.
     */
    default void handleTimer(String timer) {

    }


    /**
     * This method is used to communicate the idNumber of the match that the client is currently playing
     * @param idMatch String ID number.
     */
    default void handleMatchId(String idMatch) {

    }

    /**
     * This method is used to let the client know witch player has the turn.
     * @param turnMessage String containing the player that has the turn
     */
    default void handleTurnMessage(String turnMessage) {

    }

    /**
     * This method is used to update the UI every time it changes.
     * @param setup String containing multiple messages divided by ';'.
     *              The meaning of those messages is listed here:
     *              1) gamePlayers : the list of all players that belongs to the match.
     *              2) draftpool : contains all the dice that belong to the draftPool.
     *              3) toolcards : contains a list of toolCard's id, their cost and their description.
     *              4) favors : contains the username of a player along with his favorToken's number.
     *              5) state : contains the player name along with his state (active/inactive).
     *              6) publiccards : contains the list of objectCard's id along with their description.
     *              7) privatecard : contains the idNumber of the privateObjectiveCard and it's color.
     *              8) roundtrack : contains all the dice that belong to the roundTrack.
     *              9) restrictions : contains all the restriction of a given player's patternCard.
     *              10) dices : contains all the dices placed on a given player's patternCard.
     */
    default void updateBoard(String setup) {}

    /**
     * This method is used to communicate the four patternCards that have been selected for that player
     * @param patternCards String containing the id of the patternCard and also the number of it's favor tokens.
     */
    default void setPatternCards(String patternCards) {

    }

    /**
     * This method is used to communicate the current turn's state.
     * @param gameState String name of the turn's state.
     */
    default void handleGameState(String gameState) {

    }

    /**
     * This method is used to communicate that the game ended and contains all the player's score
     * @param score String containing a list of players and their scores.
     */
    default void handleScore(String score) {

    }
}
