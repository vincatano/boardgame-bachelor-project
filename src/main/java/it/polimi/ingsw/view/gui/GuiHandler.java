package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.AbstractView;
import it.polimi.ingsw.view.SceneInterface;
import javafx.application.Platform;

/**
 * {@inheritDoc}
 * This class is used to launch SceneInterface methods on the GuiControllers using Platform.runLater()
 */
public class GuiHandler extends AbstractView implements SceneInterface {
    private SceneInterface view;

    /**
     * Constructor: binds the SceneInterface object inherited by Abstract view to itself.
     */
    public GuiHandler() {
        scene=this;
    }

    /**
     * Setter method, store the current GuiController in order to launch methods on it.
     * @param gui Specific GuiController objects.
     */
    public void setGui(SceneInterface gui) {
        this.view = gui;
    }

    /**
     * {@inheritDoc}
     * Call method handleAlert() using Platform.later();
     */
    @Override
    public void handleAlert(String alert) {
        Platform.runLater(() -> view.handleAlert(alert));
    }

    /**
     * {@inheritDoc}
     * Call method handleClientConnected() using Platform.later();
     */
    @Override
    public void handleClientConnected(String messageConnection) {
        Platform.runLater(() -> view.handleClientConnected(messageConnection));
    }

    /**
     * {@inheritDoc}
     * Call method handleConnectedPlayers() using Platform.later();
     */
    @Override
    public void handleConnectedPlayers(String playerlist) {
        Platform.runLater(() -> view.handleConnectedPlayers(playerlist));
    }

    /**
     * {@inheritDoc}
     * Call method handleTimer() using Platform.later();
     */
    @Override
    public void handleTimer(String timer){
        Platform.runLater(() -> view.handleTimer(timer));
    }

    /**
     * {@inheritDoc}
     * Call method handleMatchId() using Platform.later();
     */
    @Override
    public void handleMatchId(String idMatch) {
        Platform.runLater(() -> view.handleMatchId(idMatch));
    }

    /**
     * {@inheritDoc}
     * Call method handleTurnMessage() using Platform.later();
     */
    @Override
    public void handleTurnMessage(String turnMessage) {
        Platform.runLater(() -> view.handleTurnMessage(turnMessage));
    }

    /**
     * {@inheritDoc}
     * Call method updateBoard() using Platform.later();
     */
    @Override
    public void updateBoard(String setup) {
        Platform.runLater(() -> view.updateBoard(setup));
    }

    /**
     * {@inheritDoc}
     * Call method setPatternCards() using Platform.later();
     */
    @Override
    public void setPatternCards(String patternCards) {
        Platform.runLater(() -> view.setPatternCards(patternCards));
    }

    /**
     * {@inheritDoc}
     * Call method handleGameState() using Platform.later();
     */
    @Override
    public void handleGameState(String gameState) {
        Platform.runLater(() -> view.handleGameState(gameState));
    }

    /**
     * {@inheritDoc}
     * Call method handleScore() using Platform.later();
     */
    @Override
    public void handleScore(String score) {
        Platform.runLater(() -> view.handleScore(score));
    }
}
