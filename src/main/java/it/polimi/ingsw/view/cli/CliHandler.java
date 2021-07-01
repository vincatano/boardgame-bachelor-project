package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.InputComposer;
import it.polimi.ingsw.view.AbstractView;
import it.polimi.ingsw.view.SceneInterface;

/**
 * {@inheritDoc}
 * This class is used to handle the state of match client side using the cli
 */
public class CliHandler extends AbstractView {
    private Client client;
    private Printer printer;
    private InputComposer composer;
    private GameData gameData;
    private boolean specialToolCard;

    public CliHandler(Client client){
        this.client= client;
        this.printer = new Printer();
        this.scene = new LoginState(printer,this);
        this.composer = new InputComposer(client);
        this.gameData = new GameData();
        this.specialToolCard = false;
    }

    /**
     * Take input from name and type of connection from user and try to connect
     */
    public void initialize() {
        while (!client.connected()) {
            client.setName(printer.getName());
            client.setKindConnection(printer.getConnection());
            client.connect();
        }
        gameData.setName(client.getName());
    }

    /**
     * receive input from command line and sends it to the server
     */
    public void receiveCommand(){
        while(client.connected()) {
            String cmd =composer.sanitize(printer.getCommand(),gameData);
            if(cmd.contains("--")&&!specialToolCard){
                cmd="";
            }
            client.sendCommand(cmd);
        }
    }

    /**
     *
     * @param newState set new state of game
     */
    public void setState(SceneInterface newState) {
        this.scene =newState;
    }

    /**
     * print welcome message
     */
    public void welcome() {
        printer.welcome();
    }

    /**
     *
     * @param num num to set to client
     */
    void setIdMatch(Integer num) {
        client.setNumMatch(num);
    }

    /**
     *
     * @param roundTrack updated value of round track
     */
    void setRoundtrack(String roundTrack){
        gameData.setRoundTrack(roundTrack);
    }

    /**
     *
     * @param draftPool update value of draft pool
     */
    void setDraftPool(String draftPool){
        gameData.setDraftPool(draftPool);
    }

    /**
     *
     * @param restrictions update value of dice on window pattern card
     */
    void setRestrictions(String restrictions){
        gameData.setRestrictions(restrictions);
    }

    /**
     *
     * @param specialToolCard true if is selected tool card 11 or 1
     */
    void setSpecialToolCard(boolean specialToolCard) {
        this.specialToolCard = specialToolCard;
    }

}
