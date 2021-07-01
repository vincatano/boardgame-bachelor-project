package it.polimi.ingsw.turntest;

import it.polimi.ingsw.controller.Match;
import it.polimi.ingsw.controller.Manager;
import it.polimi.ingsw.model.gamedata.*;
import it.polimi.ingsw.model.gamedata.gametools.Dice;
import it.polimi.ingsw.model.gamedata.gametools.ToolCard;
import it.polimi.ingsw.model.gamedata.gametools.WindowPatternCard;
import it.polimi.ingsw.model.gamelogic.Round;
import it.polimi.ingsw.model.gamelogic.turn.Turn;
import it.polimi.ingsw.model.gamelogic.turn.moveexceptions.WrongMoveException;
import it.polimi.ingsw.view.virtualview.VirtualViewObserver;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.turntest.TurnTest.lastName;
import static it.polimi.ingsw.turntest.TurnTest.pullOutThatCard;
import static it.polimi.ingsw.turntest.TurnTest.setThatCard;
import static org.junit.jupiter.api.Assertions.*;

public class ToolCard4Test {
    long timerCard = 0;
    long timerMove = 0;
    @Test
    public void testingAllowedMoves() {
        ToolCard tester = pullOutThatCard(4);

        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("one");
        Player p2 = new Player("two");
        Player p3 = new Player("three");
        Player p4 = new Player("four");
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        Table table = setThatCard(4,players);

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

       WindowPatternCard windowPatternCard1 = new WindowPatternCard(50,10,"noname");
       WindowPatternCard windowPatternCard2 = new WindowPatternCard(50,10,"noname");
       WindowPatternCard windowPatternCard3 = new WindowPatternCard(50,10,"noname");
       WindowPatternCard windowPatternCard4 = new WindowPatternCard(50,10,"noname");

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

        Turn turn = new Turn(p1, round,  true, table);

        Dice d1 = new Dice(Colour.GREEN);
        d1.setNumber(1);
        Dice d2 = new Dice(Colour.RED);
        d2.setNumber(2);
        Dice d3 = new Dice(Colour.YELLOW);
        d3.setNumber(3);
        Dice d4 = new Dice(Colour.RED);
        d4.setNumber(4);
        Dice d5 = new Dice(Colour.PURPLE);
        d5.setNumber(5);
        Dice d6 = new Dice(Colour.BLUE);
        d6.setNumber(6);
        Dice d7 = new Dice(Colour.PURPLE);
        d7.setNumber(1);
        Dice d8 = new Dice(Colour.YELLOW);
        d8.setNumber(2);
        Dice d9 = new Dice(Colour.YELLOW);
        d9.setNumber(3);



        p1.getWindowPatternCard().placeDice(d9, 0, 0);
        p1.getWindowPatternCard().placeDice(d1, 1, 0);
        p1.getWindowPatternCard().placeDice(d4, 1, 1);
        p1.getWindowPatternCard().placeDice(d5, 1, 2);
        p1.getWindowPatternCard().placeDice(d2, 2, 1);
        p1.getWindowPatternCard().placeDice(d7, 2, 2);
        p1.getWindowPatternCard().placeDice(d8, 2, 3);
        p1.getWindowPatternCard().placeDice(d3, 3, 2);
        p1.getWindowPatternCard().placeDice(d6, 3, 3);

        turn.startTurn();

        assertEquals("StartTurn", lastName(turn.getState().toString(), 10));


        try {
            turn.receiveMove(tester);
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        assertEquals("SelectingMandatoryW", lastName(turn.getState().toString(), 20));

        try {
            turn.receiveMove(d5, new Pos(1, 2));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        assertEquals("MovingMandatoryW", lastName(turn.getState().toString(), 17));

        try {
            turn.receiveMove(new Pos(0, 1));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        assertEquals("SelectingMandatoryW", lastName(turn.getState().toString(), 20));

        try {
            turn.receiveMove(d6, new Pos(3, 3));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        assertEquals("MovingMandatoryW", lastName(turn.getState().toString(), 17));

        try {
            turn.receiveMove(new Pos(3, 1));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        assertEquals("ToolBeforeDice", lastName(turn.getState().toString(), "ToolBeforeDice".length()+1));


        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 2)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 3)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 4)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 3)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 4)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(2, 0)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(2, 4)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 0)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 4)).isOccupied());


        assertTrue(p1.getWindowPatternCard().getCell(new Pos(0, 1)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 2)).isOccupied());

        assertTrue(p1.getWindowPatternCard().getCell(new Pos(3, 1)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 3)).isOccupied());

        assertTrue(p1.getWindowPatternCard().getCell(new Pos(0, 0)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(1, 0)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(1, 1)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(2, 1)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(2, 2)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(3, 2)).isOccupied());



        try {
            turn.receiveMove("pass");
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }


        String state = "EndTurn";

        assertEquals(state, lastName(turn.getState().toString(),state.length()+1));

    }

    @Test
    public void testingWrongFirstSelection() {
        ToolCard tester = pullOutThatCard(4);

        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("one");
        Player p2 = new Player("two");
        Player p3 = new Player("three");
        Player p4 = new Player("four");
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        Table table = setThatCard(4,players);

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

        Turn turn = new Turn(p1, round,  true, table);

        Dice d1 = new Dice(Colour.GREEN);
        d1.setNumber(1);
        Dice d2 = new Dice(Colour.RED);
        d2.setNumber(2);
        Dice d3 = new Dice(Colour.YELLOW);
        d3.setNumber(3);
        Dice d4 = new Dice(Colour.RED);
        d4.setNumber(4);
        Dice d5 = new Dice(Colour.PURPLE);
        d5.setNumber(5);
        Dice d6 = new Dice(Colour.BLUE);
        d6.setNumber(6);
        Dice d7 = new Dice(Colour.PURPLE);
        d7.setNumber(1);
        Dice d8 = new Dice(Colour.YELLOW);
        d8.setNumber(2);
        Dice d9 = new Dice(Colour.YELLOW);
        d9.setNumber(3);


        p1.getWindowPatternCard().placeDice(d9, 0, 0);
        p1.getWindowPatternCard().placeDice(d1, 1, 0);
        p1.getWindowPatternCard().placeDice(d4, 1, 1);
        p1.getWindowPatternCard().placeDice(d5, 1, 2);
        p1.getWindowPatternCard().placeDice(d2, 2, 1);
        p1.getWindowPatternCard().placeDice(d7, 2, 2);
        p1.getWindowPatternCard().placeDice(d8, 2, 3);
        p1.getWindowPatternCard().placeDice(d3, 3, 2);
        p1.getWindowPatternCard().placeDice(d6, 3, 3);

        turn.startTurn();

        assertEquals("StartTurn", lastName(turn.getState().toString(), 10));

        try {
            turn.receiveMove(tester);
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        assertEquals("SelectingMandatoryW", lastName(turn.getState().toString(), 20));

        assertThrows(WrongMoveException.class,()-> turn.receiveMove(d8, new Pos(2, 2)));

        assertEquals("SelectingMandatoryW", lastName(turn.getState().toString(), 20));

        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 1)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 2)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 3)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 4)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 3)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 4)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(2, 0)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(2, 4)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 0)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 1)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 4)).isOccupied());


        assertTrue(p1.getWindowPatternCard().getCell(new Pos(0, 0)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(1, 0)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(1, 1)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(1, 2)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(2, 1)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(2, 2)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(3, 2)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(3, 3)).isOccupied());

    }

    @Test
    public void testingWrongPositioning() {
        ToolCard tester = pullOutThatCard(4);

        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("one");
        Player p2 = new Player("two");
        Player p3 = new Player("three");
        Player p4 = new Player("four");
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        Table table = setThatCard(4,players);

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

        Turn turn = new Turn(p1, round, true, table);

        Dice d1 = new Dice(Colour.GREEN);
        d1.setNumber(1);
        Dice d2 = new Dice(Colour.RED);
        d2.setNumber(2);
        Dice d3 = new Dice(Colour.YELLOW);
        d3.setNumber(3);
        Dice d4 = new Dice(Colour.RED);
        d4.setNumber(4);
        Dice d5 = new Dice(Colour.PURPLE);
        d5.setNumber(5);
        Dice d6 = new Dice(Colour.BLUE);
        d6.setNumber(6);
        Dice d7 = new Dice(Colour.PURPLE);
        d7.setNumber(1);
        Dice d8 = new Dice(Colour.YELLOW);
        d8.setNumber(2);
        Dice d9 = new Dice(Colour.YELLOW);
        d9.setNumber(3);


        p1.getWindowPatternCard().placeDice(d9, 0, 0);
        p1.getWindowPatternCard().placeDice(d1, 1, 0);
        p1.getWindowPatternCard().placeDice(d4, 1, 1);
        p1.getWindowPatternCard().placeDice(d5, 1, 2);
        p1.getWindowPatternCard().placeDice(d2, 2, 1);
        p1.getWindowPatternCard().placeDice(d7, 2, 2);
        p1.getWindowPatternCard().placeDice(d8, 2, 3);
        p1.getWindowPatternCard().placeDice(d3, 3, 2);
        p1.getWindowPatternCard().placeDice(d6, 3, 3);



        turn.startTurn();

        assertEquals("StartTurn", lastName(turn.getState().toString(), 10));

        try {
            turn.receiveMove(tester);
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        assertEquals("SelectingMandatoryW", lastName(turn.getState().toString(), 20));

        try {
            turn.receiveMove(d5, new Pos(1, 2));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        assertEquals("MovingMandatoryW", lastName(turn.getState().toString(), 17));

        assertThrows(WrongMoveException.class,()-> turn.receiveMove(new Pos(2, 2)));

        assertEquals("MovingMandatoryW", lastName(turn.getState().toString(), 17));

        assertTrue(p1.getWindowPatternCard().getCell(new Pos(0, 0)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(1, 0)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(1, 1)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(1, 2)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(2, 1)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(2, 2)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(2, 3)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(3, 2)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(3, 3)).isOccupied());

        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 1)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 2)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 3)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 4)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 3)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 4)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(2, 0)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(2, 4)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 0)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 1)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 4)).isOccupied());

    }

    @Test
    public void testingWrongSecondSelection() {
        ToolCard tester = pullOutThatCard(4);

        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("one");
        Player p2 = new Player("two");
        Player p3 = new Player("three");
        Player p4 = new Player("four");
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        Table table = setThatCard(4,players);

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

        Turn turn = new Turn(p1, round,  true, table);

        Dice d1 = new Dice(Colour.GREEN);
        d1.setNumber(1);
        Dice d2 = new Dice(Colour.RED);
        d2.setNumber(2);
        Dice d3 = new Dice(Colour.YELLOW);
        d3.setNumber(3);
        Dice d4 = new Dice(Colour.RED);
        d4.setNumber(4);
        Dice d5 = new Dice(Colour.PURPLE);
        d5.setNumber(5);
        Dice d6 = new Dice(Colour.BLUE);
        d6.setNumber(6);
        Dice d7 = new Dice(Colour.PURPLE);
        d7.setNumber(1);
        Dice d8 = new Dice(Colour.YELLOW);
        d8.setNumber(2);
        Dice d9 = new Dice(Colour.YELLOW);
        d9.setNumber(3);


        p1.getWindowPatternCard().placeDice(d9, 0, 0);
        p1.getWindowPatternCard().placeDice(d1, 1, 0);
        p1.getWindowPatternCard().placeDice(d4, 1, 1);
        p1.getWindowPatternCard().placeDice(d5, 1, 2);
        p1.getWindowPatternCard().placeDice(d2, 2, 1);
        p1.getWindowPatternCard().placeDice(d7, 2, 2);
        p1.getWindowPatternCard().placeDice(d8, 2, 3);
        p1.getWindowPatternCard().placeDice(d3, 3, 2);
        p1.getWindowPatternCard().placeDice(d6, 3, 3);



        turn.startTurn();

        assertEquals("StartTurn", lastName(turn.getState().toString(), 10));

        try {
            turn.receiveMove(tester);
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        assertEquals("SelectingMandatoryW", lastName(turn.getState().toString(), 20));

        try {
            turn.receiveMove(d6, new Pos(3, 3));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        assertEquals("MovingMandatoryW", lastName(turn.getState().toString(), 17));

        try {
            turn.receiveMove(new Pos(2, 0));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        assertEquals("SelectingMandatoryW", lastName(turn.getState().toString(), 20));

        assertThrows(WrongMoveException.class,()-> turn.receiveMove(d5,new Pos(2, 2)));

        assertEquals("SelectingMandatoryW", lastName(turn.getState().toString(), 20));

        assertTrue(p1.getWindowPatternCard().getCell(new Pos(0, 0)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(1, 0)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(1, 1)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(1, 2)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(2, 0)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(2, 1)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(2, 2)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(3, 2)).isOccupied());


        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 1)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 2)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 3)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 4)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 3)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 4)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(2, 4)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 0)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 1)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 3)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 4)).isOccupied());

    }

    @Test
    public void testingWrongSecondPlacement() {
        ToolCard tester = pullOutThatCard(4);

        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("one");
        Player p2 = new Player("two");
        Player p3 = new Player("three");
        Player p4 = new Player("four");
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        Table table = setThatCard(4,players);

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

        Turn turn = new Turn(p1, round,  true, table);

        Dice d1 = new Dice(Colour.GREEN);
        d1.setNumber(1);
        Dice d2 = new Dice(Colour.RED);
        d2.setNumber(2);
        Dice d3 = new Dice(Colour.YELLOW);
        d3.setNumber(3);
        Dice d4 = new Dice(Colour.RED);
        d4.setNumber(4);
        Dice d5 = new Dice(Colour.PURPLE);
        d5.setNumber(5);
        Dice d6 = new Dice(Colour.BLUE);
        d6.setNumber(6);
        Dice d7 = new Dice(Colour.PURPLE);
        d7.setNumber(1);
        Dice d8 = new Dice(Colour.YELLOW);
        d8.setNumber(2);
        Dice d9 = new Dice(Colour.YELLOW);
        d9.setNumber(3);



        p1.getWindowPatternCard().placeDice(d9, 0, 0);
        p1.getWindowPatternCard().placeDice(d1, 1, 0);
        p1.getWindowPatternCard().placeDice(d4, 1, 1);
        p1.getWindowPatternCard().placeDice(d5, 1, 2);
        p1.getWindowPatternCard().placeDice(d2, 2, 1);
        p1.getWindowPatternCard().placeDice(d7, 2, 2);
        p1.getWindowPatternCard().placeDice(d8, 2, 3);
        p1.getWindowPatternCard().placeDice(d3, 3, 2);
        p1.getWindowPatternCard().placeDice(d6, 3, 3);


        turn.startTurn();


        assertEquals("StartTurn", lastName(turn.getState().toString(), 10));

        try {
            turn.receiveMove(tester);
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        assertEquals("SelectingMandatoryW", lastName(turn.getState().toString(), 20));

        try {
            turn.receiveMove(d5, new Pos(1, 2));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        assertEquals("MovingMandatoryW", lastName(turn.getState().toString(), 17));

        try {
            turn.receiveMove(new Pos(0, 1));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        assertEquals("SelectingMandatoryW", lastName(turn.getState().toString(), 20));

        try {
            turn.receiveMove(d7, new Pos(2, 2));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        assertEquals("MovingMandatoryW", lastName(turn.getState().toString(), 17));

        assertThrows(WrongMoveException.class,()-> turn.receiveMove(d7,new Pos(2, 0)));

        assertEquals("MovingMandatoryW", lastName(turn.getState().toString(), 17));



        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 2)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 3)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 4)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 2)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 3)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 4)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(2, 0)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(2, 4)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 0)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 1)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 4)).isOccupied());


        assertTrue(p1.getWindowPatternCard().getCell(new Pos(0, 0)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(0, 1)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(1, 0)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(1, 1)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(2, 1)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(2, 2)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(3, 2)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(3, 3)).isOccupied());


    }
}