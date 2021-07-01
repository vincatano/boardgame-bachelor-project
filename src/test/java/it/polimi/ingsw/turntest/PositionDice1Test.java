package it.polimi.ingsw.turntest;

import it.polimi.ingsw.controller.Match;
import it.polimi.ingsw.controller.Manager;
import it.polimi.ingsw.model.gamedata.Player;
import it.polimi.ingsw.model.gamedata.Pos;
import it.polimi.ingsw.model.gamedata.PublicObjects;
import it.polimi.ingsw.model.gamedata.Table;
import it.polimi.ingsw.model.gamedata.gametools.Dice;
import it.polimi.ingsw.model.gamedata.gametools.WindowPatternCard;
import it.polimi.ingsw.model.gamelogic.Round;
import it.polimi.ingsw.model.gamelogic.turn.StartTurn;
import it.polimi.ingsw.model.gamelogic.turn.Turn;
import it.polimi.ingsw.model.gamelogic.turn.moveexceptions.WrongMoveException;
import it.polimi.ingsw.view.virtualview.VirtualViewObserver;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PositionDice1Test {
    long timerCard = 0;
    long timerMove = 0;
    @Test
    public void testGoingEndTurn(){
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("one");
        Player p2 = new Player("two");
        Player p3 = new Player("three");
        Player p4 = new Player("four");
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

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
        WindowPatternCard windowPatternCard4 = new WindowPatternCard(50,10,"noName");

        p1.setMyWindow(windowPatternCard1);
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

        Turn tester = new Turn(p1,round,true,table);

        StartTurn startTurn = new StartTurn(tester);

        tester.setState(startTurn);

        Dice d = table.getDraftPool().chooseDice(4);

        Pos pos = new Pos(4,5);

        try {
            tester.receiveMove(d,pos);
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        pos.setX(0);
        pos.setY(2);
        try {
            tester.receiveMove(pos);
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        assertTrue(p1.getWindowPatternCard().getCell(new Pos(0,2)).isOccupied());


        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 0)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 1)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 3)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 4)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 0)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 1)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 2)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 3)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 4)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(2, 0)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(2, 1)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(2, 2)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(2, 3)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(2, 4)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 0)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 1)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 2)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 3)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 4)).isOccupied());



    }

    @Test
    public void testWrongMove() {
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("one");
        Player p2 = new Player("two");
        Player p3 = new Player("three");
        Player p4 = new Player("four");
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

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

        Round round = new Round(players, 1, table,match, timerMove);

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

        Turn tester = new Turn(p1, round,  true, table);

        StartTurn startTurn = new StartTurn(tester);

        tester.setState(startTurn);

        Dice d = table.getDraftPool().chooseDice(4);

        Pos pos = new Pos(4, 5);

        try {
            tester.receiveMove(d, pos);
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        pos.setX(1);
        pos.setY(2);

        assertThrows(WrongMoveException.class,()-> tester.receiveMove(pos));

    }

    @Test
    public void testWrongMoveNotFirstRound() {
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("one");
        Player p2 = new Player("two");
        Player p3 = new Player("three");
        Player p4 = new Player("four");
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

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

        Round round = new Round(players, 4, table,match, timerMove);


        WindowPatternCard windowPatternCard1 = new WindowPatternCard(50,10,"noName");
        WindowPatternCard windowPatternCard2 = new WindowPatternCard(50,10,"noName");
        WindowPatternCard windowPatternCard3 = new WindowPatternCard(50,10,"noName");
        WindowPatternCard windowPatternCard4 = new WindowPatternCard(50,10,"noName");

        p1.setMyWindow(windowPatternCard1);
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

        Turn tester = new Turn(p1, round, true, table);

        StartTurn startTurn = new StartTurn(tester);

        tester.setState(startTurn);

        Dice d = table.getDraftPool().chooseDice(4);

        Pos pos = new Pos(4, 5);

        try {
            tester.receiveMove(d, pos);
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        pos.setX(1);
        pos.setY(2);

        assertThrows(WrongMoveException.class,()->{tester.receiveMove(pos);});

    }
}
