package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.view.SceneInterface;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WaitingRoomState implements SceneInterface {
    private static final String MESSAGE = "Sei in coda. Aspetto altri giocatori...     Timer: ";
    private Printer printer;
    private CliHandler cliHandler;
    private String tmpTimer;
    private List<String> players;
    private ScheduledExecutorService exec;
    private boolean started;

    WaitingRoomState(Printer printer, CliHandler cliHandler, String timer) {
        this.printer = printer;
        this.cliHandler = cliHandler;
        this.tmpTimer = timer;
        this.started=false;
    }

    @Override
    public void handleTimer(String timer) {
        exec.shutdown();
        cliHandler.setState(new ChooseWindowState(printer, cliHandler, timer, players));
    }

    @Override
    public void handleConnectedPlayers(String playerlist) {
        players = Arrays.asList(playerlist.split(" "));
        printer.printWaitingRoom(tmpTimer, players, MESSAGE);
        exec = Executors.newSingleThreadScheduledExecutor();
        if (players.size() > 1 && !started) {
            startTimer();
            started=true;
        }
        if (players.size() == 1) {
            exec.shutdown();
            started=false;
            printer.printWaitingRoom(tmpTimer, players, MESSAGE);
        }
    }

    private void startTimer() {
        exec.scheduleWithFixedDelay(() -> {
            if (Integer.parseInt(tmpTimer) == 1) {
                exec.shutdown();
                return;
            }
            int i = Integer.parseInt(tmpTimer) - 1;
            tmpTimer = String.valueOf(i);
        }, 0, 1, TimeUnit.SECONDS);
    }
}
