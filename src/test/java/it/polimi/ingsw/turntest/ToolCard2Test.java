package it.polimi.ingsw.turntest;

import it.polimi.ingsw.controller.Match;
import it.polimi.ingsw.controller.Manager;
import it.polimi.ingsw.model.gamedata.*;
import it.polimi.ingsw.model.gamedata.gametools.CardContainer;
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

public class ToolCard2Test {

    long timerCard = 0;
    long timerMove = 0;
    @Test
    public void testingAllowedMoves(){
        ToolCard tester = new ToolCard();
        CardContainer container = new CardContainer();
        List<ToolCard> toolCardArrayList = container.pullOutTools();

        while(toolCardArrayList.get(0).getID()!=2 && toolCardArrayList.get(1).getID()!= 2 && toolCardArrayList.get(2).getID()!=2 ){
            container = new CardContainer();
            toolCardArrayList = container.pullOutTools();
        }
        if(toolCardArrayList.get(0).getID()==2)
            tester = toolCardArrayList.get(0);

        if(toolCardArrayList.get(1).getID()==2)
            tester = toolCardArrayList.get(1);

        if(toolCardArrayList.get(2).getID()==2)
            tester = toolCardArrayList.get(2);

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

        while(players.get(0).getToolCards().get(0).getID() != 2 && players.get(0).getToolCards().get(1).getID() != 2 && players.get(0).getToolCards().get(2).getID() != 2){
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

        Match match = new Match(players,new Manager(0,0,0),0, timerCard, timerMove);

        Round round = new Round(players,2,table,match, timerMove);

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

        Turn turn = new Turn(p1,round,true,table);

        Dice d1 = new Dice(Colour.GREEN);
        Dice d2 = new Dice(Colour.RED);
        Dice d3 = new Dice(Colour.YELLOW);
        if(d3.getNumber()==6)
            d2.setNumber(1);
        else if(d3.getNumber()==1)
            d2.setNumber(2);
        else
            d2.setNumber(d3.getNumber()+1);

        p1.getWindowPatternCard().placeDice(d1,1,0);
        p1.getWindowPatternCard().placeDice(d2,2,1);
        p1.getWindowPatternCard().placeDice(d3,3,2);

        p1.getWindowPatternCard().addRestrictions('B',2,2);

        turn.startTurn();

        assertEquals("StartTurn", lastName(turn.getState().toString(),10));

        try {
            turn.receiveMove(tester);
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        assertEquals("SelectingWindowDice", lastName(turn.getState().toString(),"SelectingWindowDice".length()+1));

        try {
            turn.receiveMove(d2,new Pos(2,1));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        assertEquals("MovingWindowDice", lastName(turn.getState().toString(),17));

        try {
            turn.receiveMove(new Pos(2, 2));
        } catch (WrongMoveException e)  {
            e.printStackTrace();
        }

        assertEquals("ToolBeforeDice", lastName(turn.getState().toString(),"ToolBeforeDice".length()+1));


        assertTrue(p1.getWindowPatternCard().getCell(new Pos(2,2)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(2,1)).isOccupied());


        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 0)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 1)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 2)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 3)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 4)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 1)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 2)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 3)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 4)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(2, 0)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(2, 3)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(2, 4)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 0)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 1)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 3)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 4)).isOccupied());


        assertTrue(p1.getWindowPatternCard().getCell(new Pos(1, 0)).isOccupied());
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
    public void testingRightMovesWithRestrictions() {
        ToolCard tester = pullOutThatCard(2);

        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("one");
        Player p2 = new Player("two");
        Player p3 = new Player("three");
        Player p4 = new Player("four");
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        Table table = setThatCard(2,players);

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

        p1.setMyWindow(new WindowPatternCard(50,10,"noName"));
        p2.setMyWindow(new WindowPatternCard(50,10,"noName"));
        p3.setMyWindow(new WindowPatternCard(50,10,"noName"));
        p4.setMyWindow(new WindowPatternCard(50,10,"noName"));

        p1.getWindowPatternCard().addRestrictions('B', 2, 2);

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
        Dice d2 = new Dice(Colour.RED);
        d2.setNumber(2);
        Dice d3 = new Dice(Colour.YELLOW);
        d3.setNumber(3);

        p1.getWindowPatternCard().placeDice(d1, 1, 0);
        p1.getWindowPatternCard().placeDice(d2, 2, 1);
        p1.getWindowPatternCard().placeDice(d3, 3, 2);

        table.resetSelection();

        turn.startTurn();

        String state = "StartTurn";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(tester);
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        state = "SelectingWindowDice";
        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(d3, new Pos(3, 2));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        state = "MovingWindowDice";
        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(new Pos(2, 2));
        } catch (WrongMoveException e)  {
            e.printStackTrace();
        }

        state = "ToolBeforeDice";
        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));


        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 0)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 1)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 2)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 3)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 4)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 1)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 2)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 3)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 4)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(2, 0)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(2, 3)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(2, 4)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 0)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 1)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 2)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 3)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 4)).isOccupied());


        assertTrue(p1.getWindowPatternCard().getCell(new Pos(1, 0)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(2, 1)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(2, 2)).isOccupied());



        try {
            turn.receiveMove("pass");
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }


        state = "EndTurn";

        assertEquals(state, lastName(turn.getState().toString(),state.length()+1));

    }


    @Test
    public void testingWrongSelection() {
        ToolCard tester = pullOutThatCard(2);

        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("one");
        Player p2 = new Player("two");
        Player p3 = new Player("three");
        Player p4 = new Player("four");
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        Table table = setThatCard(2,players);

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
        Dice d2 = new Dice(Colour.RED);
        Dice d3 = new Dice(Colour.YELLOW);
        d3.setNumber(3);

        p1.getWindowPatternCard().placeDice(d1, 1, 0);
        p1.getWindowPatternCard().placeDice(d2, 2, 1);
        p1.getWindowPatternCard().placeDice(d3, 3, 2);

        p1.getWindowPatternCard().addRestrictions('5',2,2);

        turn.startTurn();

        assertEquals("StartTurn", lastName(turn.getState().toString(), 10));

        try {
            turn.receiveMove(tester);
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        assertEquals("SelectingWindowDice", lastName(turn.getState().toString(), 20));

        assertThrows(WrongMoveException.class,()->{turn.receiveMove(d3, new Pos(1, 1));});


        assertTrue(p1.getWindowPatternCard().getCell(new Pos(1,0)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(2,1)).isOccupied());
        assertTrue(p1.getWindowPatternCard().getCell(new Pos(3,2)).isOccupied());


        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 0)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 1)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 2)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 3)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(0, 4)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 1)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 2)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 3)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(1, 4)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(2, 0)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(2, 2)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(2, 3)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(2, 4)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 0)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 1)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 3)).isOccupied());
        assertFalse(p1.getWindowPatternCard().getCell(new Pos(3, 4)).isOccupied());

    }

}
