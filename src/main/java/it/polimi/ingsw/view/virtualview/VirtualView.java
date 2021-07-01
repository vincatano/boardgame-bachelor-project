package it.polimi.ingsw.view.virtualview;

import it.polimi.ingsw.controller.ClientBox;
import it.polimi.ingsw.model.gamedata.Player;
import it.polimi.ingsw.model.gamedata.gametools.WindowPatternCard;

import java.util.List;

public class VirtualView extends VirtualViewObserver {
    private static final String TURN="turn ";
    private static final String TIMER="timer ";
    private static final String STATEMOVE="gamestate ";

    public VirtualView(ClientBox s, Player player) {
    this.clientBox=s;
    this.player=player;
    }

    @Override
    public void update() {
        try {
            String updateMove;
            VirtualViewParser parser = new VirtualViewParser(player);
            updateMove = parser.startParsing();
            clientBox.update(updateMove);
        }catch (RuntimeException ignored){
        }
    }

    @Override
    public void updateStateTurn(String whoIsTurn,long timeSleep) {
        timeSleep = timeSleep / 1000;
        clientBox.updateTurn(TIMER + String.valueOf(timeSleep));
        clientBox.updateTurn(TURN + "E' il turno di : " + whoIsTurn);

    }

    @Override
    public void wrongMove(String s) {
        clientBox.wrongMove(s);
    }

    @Override
    public void chooseWindow(List<WindowPatternCard> windows) {
        VirtualViewParser parser = new VirtualViewParser(player);
        String wind = parser.extractedWindows(windows);
        clientBox.chooseWindow(wind);
    }

    @Override
    public void timerChoose(long timerWindows) {
        clientBox.setTimer(timerWindows);
    }

    @Override
    public void notifyState(String state) {
        clientBox.update(STATEMOVE + state);
    }

    @Override
    public void notifyScore(String s) {
        clientBox.update(s);
    }

    @Override
    public void reconnectingMessage() {
        clientBox.updateTurn(TIMER + "0");
        clientBox.updateTurn(TURN + "Aspetta un attimo, ti sto riconnettendo...");
    }

}
