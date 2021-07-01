package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.view.SceneInterface;

import java.util.Arrays;
import java.util.List;

public class MatchState implements SceneInterface {
    private String setup;
    private CliHandler cliHandler;
    private Printer printer;
    private String timer;
    private List<String> players;
    private String turnState;

    MatchState(Printer printer, CliHandler cliHandler, String setup, List<String> players) {
        this.printer=printer;
        this.cliHandler=cliHandler;
        this.setup=setup;
        this.players=players;
        analyze();
    }

    MatchState(Printer printer, CliHandler cliHandler, List<String> playerlist) {
        this.printer=printer;
        this.cliHandler=cliHandler;
        this.players=playerlist;
    }

    private void printUpdate(){
        printer.printMatch(setup,timer,players,turnState);
    }

    @Override
    public void handleTimer(String timer) {
        this.timer=timer;
    }

    @Override
    public void updateBoard(String setup) {
            this.setup = setup;
            analyze();
            if (turnState != null) {
                printUpdate();
            }
    }

    private void analyze() {
        String[] messages = setup.split(";");

        for (String substring : messages){
            if(substring.startsWith("draftpool ")){
                cliHandler.setDraftPool(substring.replace("draftpool ",""));
            }else if(substring.startsWith("roundtrack ")){
                cliHandler.setRoundtrack(substring.replace("roundtrack ",""));
            }else if(substring.startsWith("dices ")){
                cliHandler.setRestrictions(substring.replace("dices ",""));
            }
        }
    }

    @Override
    public void handleConnectedPlayers(String playerlist) {
        players= Arrays.asList(playerlist.split(" "));
        printUpdate();

    }

    @Override
    public void handleTurnMessage(String turnMessage) {
        turnState=turnMessage;
        printUpdate();
    }

    @Override
    public void handleAlert(String alert) {
        printer.printError(alert);
    }

    @Override
    public void handleScore(String score) {
        cliHandler.setState(new ScoreState(score,printer,cliHandler));
    }

    @Override
    public void handleGameState(String gameState) {
        switch (gameState) {
            case "SelectingValue":
                printer.printChooseDice();
                cliHandler.setSpecialToolCard(true);
                break;
            case "IncDecValue":
                printer.printIncDecDice();
                cliHandler.setSpecialToolCard(true);
                break;
            default:
                cliHandler.setSpecialToolCard(false);
                break;
        }

    }
}
