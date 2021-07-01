package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.view.SceneInterface;

import java.util.List;

public class ChooseWindowState implements SceneInterface {

    private CliHandler cliHandler;
    private Printer printer;
    private String timer;
    private List<String> players;

    ChooseWindowState(Printer printer, CliHandler cliHandler, String timer, List<String> players) {
        this.timer=timer;
        this.printer=printer;
        this.cliHandler=cliHandler;
        this.players=players;
    }

    @Override
    public void handleMatchId(String idMatch) {
        cliHandler.setIdMatch(Integer.valueOf(idMatch));
    }

    @Override
    public void setPatternCards(String patternCards) {
        printer.printChooseCardRoom(patternCards,timer);
    }

    @Override
    public void updateBoard(String setup) {
        cliHandler.setState(new MatchState(printer,cliHandler,setup,players));
    }
}
