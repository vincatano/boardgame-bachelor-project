package it.polimi.ingsw.view;

import it.polimi.ingsw.network.client.MessageQueue;

/**
 * Abstract class extended by GuiHandler and CliHandler
 */
public abstract class AbstractView {
    public SceneInterface scene;
    private MessageQueue queue;

    /**
     * Method called by MessageQueue object
     */
    public void notifyMessage() {
        readQueue();
    }

    /**
     * Get each message stored in the queue and call analyze() method on each of them.
     */
    private void readQueue() {
        while(queue.size()>0) {
            analyze(queue.poll());
        }
    }

    /**
     * Call methods on GuiHandler if the user chose the GUI or call methods on CLI states if the user chose the CLI
     * @param message message stored in the MessageQueue
     */
    private void analyze(String message){
        if(message.startsWith("setup")){
            String setup = message.replace("setup ","");
            scene.updateBoard(setup);

        }else if(message.startsWith("match")){
            String match = message.replace("match ","");
            scene.handleMatchId(match);

        }else if(message.startsWith("turn")){
            String turnMessage = message.replace("turn ","");
            scene.handleTurnMessage(turnMessage);

        } else if(message.startsWith("timer")){
            String time = message.replace("timer ","");
            scene.handleTimer(time);

        }else if(message.startsWith("connectedPlayers")) {
            String playerList = message.replace("connectedPlayers ","");
            scene.handleConnectedPlayers(playerList);

        }else if(message.startsWith("alert")) {
            String alert = message.replace("alert ", "");
            scene.handleAlert(alert);

        }else if(message.equals("Connected, Welcome!")) {
            scene.handleClientConnected(message);
        }else if(message.startsWith("choseWindow")) {
            String windows = message.replace("choseWindow ", "");
            scene.setPatternCards(windows);

        }else if(message.startsWith("gamestate")) {
            String gameState = message.replace("gamestate ", "");
            scene.handleGameState(gameState);
        }else if(message.startsWith("score ")) {
            String score = message.replace("score ", "");
            scene.handleScore(score);
        }
    }

    /**
     * Setter method, store the queue in order to get messages from it.
     * @param queue The MessageQueue object storing messages coming from the server.
     */
    public void setQueue(MessageQueue queue) {
        this.queue=queue;
    }
}
