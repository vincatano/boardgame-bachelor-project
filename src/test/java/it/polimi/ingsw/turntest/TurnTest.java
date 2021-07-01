package it.polimi.ingsw.turntest;

import it.polimi.ingsw.controller.Match;
import it.polimi.ingsw.controller.Manager;
import it.polimi.ingsw.model.gamedata.Player;
import it.polimi.ingsw.model.gamedata.PublicObjects;
import it.polimi.ingsw.model.gamedata.Table;
import it.polimi.ingsw.model.gamedata.gametools.CardContainer;
import it.polimi.ingsw.model.gamedata.gametools.ToolCard;
import it.polimi.ingsw.model.gamedata.gametools.WindowPatternCard;
import it.polimi.ingsw.model.gamelogic.Round;
import it.polimi.ingsw.model.gamelogic.checker.InspectorContextTool;
import it.polimi.ingsw.model.gamelogic.turn.Turn;
import it.polimi.ingsw.view.virtualview.VirtualViewObserver;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TurnTest {

    @Test
    public void testStartTurn(){
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("one");
        Player p2 = new Player("two");
        Player p3 = new Player("three");
        Player p4 = new Player("four");
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        long timerCard = 0;
        long timerMove = 0;

        Table table = new Table(players);

        VirtualViewObserver virtualViewObserver = new VirtualViewObserver() {
            @Override
            public void update() {

            }

            @Override
            public void updateStateTurn(String whoIsTurn, long timeSleep) {

            }

            @Override
            public void wrongMove(String s) {

            }

            @Override
            public void chooseWindow(List<WindowPatternCard> windows) {

            }

            @Override
            public void timerChoose(long timerWindows) {

            }

            @Override
            public void notifyState(String state) {

            }

            @Override
            public void notifyScore(String s) {

            }

            @Override
            public void reconnectingMessage() {

            }
        };

        p1.addObserver(virtualViewObserver);
        p2.addObserver(virtualViewObserver);
        p3.addObserver(virtualViewObserver);
        p4.addObserver(virtualViewObserver);

        Match match = new Match(players,new Manager(0,0,0),0, timerCard, timerMove);

        Round round = new Round(players,1,table,match, timerMove);

        WindowPatternCard windowPatternCard1 = new WindowPatternCard(50,10,"noName");
        WindowPatternCard windowPatternCard2 = new WindowPatternCard(50,10,"noName");
        WindowPatternCard windowPatternCard3 = new WindowPatternCard(50,10,"noName");
        WindowPatternCard windowPatternCard4 = new WindowPatternCard(50,10,"noName");       p1.setMyWindow(windowPatternCard1);
       p2.setMyWindow(windowPatternCard2);
       p3.setMyWindow(windowPatternCard3);
       p4.setMyWindow(windowPatternCard4);

        table.getDiceBag().setNumPlayers(4);
        table.getDraftPool().addNewDices(table.getDiceFromBag());

        PublicObjects publicObjects = new PublicObjects();
        publicObjects.setDraftPool(table.getDraftPool());
        publicObjects.setObjectiveCards(table.getObjCard());
        publicObjects.setToolCards(table.getToolCards());
        publicObjects.setRoundTrack(table.getRoundTrack());
        List<Player> playerList = new ArrayList<>();
        playerList.add(p2);
        playerList.add(p3);
        playerList.add(p4);
        publicObjects.setOthersPlayers(playerList);

        p1.setPublicObjects(publicObjects);

        InspectorContextTool inspectorContextTool = new InspectorContextTool(p1.getWindowPatternCard(),table.getDraftPool(),table.getRoundTrack());
        Turn tester = new Turn(p1,round,true,table);

        tester.startTurn();

        assertEquals("StartTurn",lastName(tester.getState().getClass().toString(),10));
    }

    public static String lastName(String type,int x){
        return type.substring((type.lastIndexOf(".")+1),(type.lastIndexOf(".")+x));
    }

    public static ToolCard pullOutThatCard(int num){
        CardContainer container = new CardContainer();
        List<ToolCard> toolCardArrayList = container.pullOutTools();
        while (toolCardArrayList.get(0).getID() != num && toolCardArrayList.get(1).getID() != num && toolCardArrayList.get(2).getID() != num) {
            container = new CardContainer();
            toolCardArrayList = container.pullOutTools();
        }
        ToolCard tester = null;
        if (toolCardArrayList.get(0).getID() == num)
            tester = toolCardArrayList.get(0);

        if (toolCardArrayList.get(1).getID() == num)
            tester = toolCardArrayList.get(1);

        if (toolCardArrayList.get(2).getID() == num)
            tester = toolCardArrayList.get(2);

        return tester;
    }

    public static Table setThatCard(int num, List<Player> players){
        Table table = new Table(players);
        table.initialize();
        table.setPublicObjects();

        while (players.get(0).getToolCards().get(0).getID() != num && players.get(0).getToolCards().get(1).getID() != num && players.get(0).getToolCards().get(2).getID() != num) {
            table = new Table(players);
            table.initialize();
            table.setPublicObjects();
        }

        return table;
    }
}
