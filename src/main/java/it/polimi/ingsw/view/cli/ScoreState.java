package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.view.SceneInterface;

public class ScoreState implements SceneInterface {
    private Printer printer;
    private CliHandler cliHandler;

    ScoreState(String score, Printer printer, CliHandler cliHandler) {
        this.printer=printer;
        this.cliHandler=cliHandler;
        printer.printScore(score);
    }

    @Override
    public void handleTimer(String timer) {
        cliHandler.setState(new WaitingRoomState(printer,cliHandler,timer));
    }
}
