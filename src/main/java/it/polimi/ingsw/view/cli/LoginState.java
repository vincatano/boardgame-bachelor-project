package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.view.SceneInterface;

import java.util.Arrays;
import java.util.List;

public class LoginState implements SceneInterface {
    private Printer printer;
    private CliHandler cliHandler;

    LoginState(Printer printer,CliHandler cliHandler) {
        this.printer=printer;
        this.cliHandler=cliHandler;
    }

    @Override
    public void handleAlert(String alert) {
        printer.printError(alert);
    }

    @Override
    public void handleClientConnected(String messageConnection) {
        printer.printConnection(messageConnection);
    }

    @Override
    public void handleConnectedPlayers(String playerlist) {
        List<String> players = Arrays.asList(playerlist.split(" "));
        cliHandler.setState(new MatchState(printer,cliHandler,players));
    }

    @Override
    public void handleTimer(String timer) {
        cliHandler.setState(new WaitingRoomState(printer,cliHandler,timer));
    }

    @Override
    public void handleMatchId(String idMatch) {
        cliHandler.setIdMatch(Integer.valueOf(idMatch));
    }

}
