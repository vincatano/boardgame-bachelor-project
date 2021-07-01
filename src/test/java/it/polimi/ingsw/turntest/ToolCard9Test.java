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

public class ToolCard9Test {

    long timerCard = 0;
    long timerMove = 0;
    @Test
    public void testAllowedMoves() {
        ToolCard tester = pullOutThatCard(9);

        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("one");
        Player p2 = new Player("two");
        Player p3 = new Player("three");
        Player p4 = new Player("four");
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        Table table = setThatCard(9,players);

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

        Match match = new Match(players, new Manager(0,0,0), 0, timerCard, timerMove);

        Round round = new Round(players, 2, table, match, timerMove);

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

        Dice d1 = new Dice(Colour.GREEN);
        d1.setNumber(5);
        Dice d2 = new Dice(Colour.YELLOW);
        d2.setNumber(4);
        Dice d3 = new Dice(Colour.PURPLE);
        d3.setNumber(4);
        Dice tmp = p1.getDraftPool().chooseDice(2);

        p1.getWindowPatternCard().placeDice(d1,2,3);
        p1.getWindowPatternCard().placeDice(d2,1,3);
        p1.getWindowPatternCard().placeDice(d3,2,4);


        Turn turn = new Turn(p1, round,  true, table);


        turn.startTurn();

        String state = "StartTurn";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(p1.getDraftPool().chooseDice(2),new Pos(2,0));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        state = "ChooseDice1";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(tester);
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        state = "MovingDraftDice";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(new Pos(3,1));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }


        state = "EndTurn";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        assertNotSame(tmp, p1.getDraftPool().chooseDice(2));

    }

    @Test
    public void testAllowedMovesWithRestrictions(){
        ToolCard tester = pullOutThatCard(9);

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
        table.initialize();
        table.setPublicObjects();

        while (players.get(0).getToolCards().get(0).getID() != 9 && players.get(0).getToolCards().get(1).getID() != 9 && players.get(0).getToolCards().get(2).getID() != 9) {
            table = new Table(players);
            table.initialize();
            table.setPublicObjects();
        }

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

        Match match = new Match(players, new Manager(0,0,0), 0, timerCard, timerMove);

        Round round = new Round(players, 2, table, match, timerMove);

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

        Dice d1 = new Dice(Colour.GREEN);
        d1.setNumber(5);
        Dice d2 = new Dice(Colour.YELLOW);
        d2.setNumber(4);
        Dice d3 = new Dice(Colour.PURPLE);
        d3.setNumber(4);
        Dice tmp = p1.getDraftPool().chooseDice(2);

        p1.getWindowPatternCard().placeDice(d1,2,3);
        p1.getWindowPatternCard().placeDice(d2,1,3);
        p1.getWindowPatternCard().placeDice(d3,2,4);

        p1.getDraftPool().chooseDice(2).setDice(new Dice(Colour.YELLOW));

        p1.getWindowPatternCard().addRestrictions('Y',3,1);


        Turn turn = new Turn(p1, round,  true, table);


        turn.startTurn();

        String state = "StartTurn";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(p1.getDraftPool().chooseDice(2),new Pos(2,0));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        state = "ChooseDice1";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(tester);
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        state = "MovingDraftDice";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(new Pos(3,1));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }


        state = "EndTurn";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        assertNotSame(tmp, p1.getDraftPool().chooseDice(2));

    }

    @Test
    public void testWrongRestriction(){
        ToolCard tester = pullOutThatCard(9);

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
        table.initialize();
        table.setPublicObjects();

        while (players.get(0).getToolCards().get(0).getID() != 9 && players.get(0).getToolCards().get(1).getID() != 9 && players.get(0).getToolCards().get(2).getID() != 9) {
            table = new Table(players);
            table.initialize();
            table.setPublicObjects();
        }

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

        Match match = new Match(players, new Manager(0,0,0), 0, timerCard, timerMove);

        Round round = new Round(players, 2, table, match, timerMove);

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

        Dice d1 = new Dice(Colour.GREEN);
        d1.setNumber(5);
        Dice d2 = new Dice(Colour.YELLOW);
        d2.setNumber(4);
        Dice d3 = new Dice(Colour.PURPLE);
        d3.setNumber(4);
        Dice tmp = p1.getDraftPool().chooseDice(2);

        p1.getWindowPatternCard().placeDice(d1,2,3);
        p1.getWindowPatternCard().placeDice(d2,1,3);
        p1.getWindowPatternCard().placeDice(d3,2,4);

        p1.getDraftPool().chooseDice(2).setDice(new Dice(Colour.YELLOW));

        p1.getWindowPatternCard().addRestrictions('B',3,1);


        Turn turn = new Turn(p1, round,  true, table);


        turn.startTurn();

        String state = "StartTurn";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(p1.getDraftPool().chooseDice(2),new Pos(2,0));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        state = "ChooseDice1";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(tester);
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        state = "MovingDraftDice";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        assertThrows(WrongMoveException.class,()-> turn.receiveMove(new Pos(3,1)));

    }

    @Test
    public void testWrongNumberRestriction(){
        ToolCard tester = pullOutThatCard(9);

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
        table.initialize();
        table.setPublicObjects();

        while (players.get(0).getToolCards().get(0).getID() != 9 && players.get(0).getToolCards().get(1).getID() != 9 && players.get(0).getToolCards().get(2).getID() != 9) {
            table = new Table(players);
            table.initialize();
            table.setPublicObjects();
        }

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

        Match match = new Match(players, new Manager(0,0,0), 0, timerCard, timerMove);

        Round round = new Round(players, 2, table, match, timerMove);

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

        Dice d1 = new Dice(Colour.GREEN);
        d1.setNumber(5);
        Dice d2 = new Dice(Colour.YELLOW);
        d2.setNumber(4);
        Dice d3 = new Dice(Colour.PURPLE);
        d3.setNumber(4);
        Dice tmp = p1.getDraftPool().chooseDice(2);

        p1.getWindowPatternCard().placeDice(d1,2,3);
        p1.getWindowPatternCard().placeDice(d2,1,3);
        p1.getWindowPatternCard().placeDice(d3,2,4);

        p1.getDraftPool().chooseDice(2).setDice(new Dice(Colour.YELLOW));
        p1.getDraftPool().chooseDice(2).setNumber(2);

        p1.getWindowPatternCard().addRestrictions('3',3,1);


        Turn turn = new Turn(p1, round,  true, table);


        turn.startTurn();

        String state = "StartTurn";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(p1.getDraftPool().chooseDice(2),new Pos(2,0));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        state = "ChooseDice1";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(tester);
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        state = "MovingDraftDice";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        assertThrows(WrongMoveException.class,()-> turn.receiveMove(new Pos(3,1)));

    }
}
