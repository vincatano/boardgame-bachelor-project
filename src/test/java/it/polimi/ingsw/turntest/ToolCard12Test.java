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

public class ToolCard12Test {

    long timerCard = 0;
    long timerMove = 0;
    @Test
    public void testAllowedMoves(){
        ToolCard tester = pullOutThatCard(12);

        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("one");
        Player p2 = new Player("two");
        Player p3 = new Player("three");
        Player p4 = new Player("four");
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        Table table = setThatCard(12, players);

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


        List<Dice> dices = new ArrayList<>();

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

        dices.add(d1);
        dices.add(d2);
        dices.add(d3);
        dices.add(d4);
        dices.add(d5);
        dices.add(d6);
        dices.add(d7);
        dices.add(d8);
        dices.add(d9);

        table.getRoundTrack().setDiceOnRoundTrack(1,dices);


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



        Dice d1d = new Dice(Colour.GREEN);
        d1d.setNumber(1);
        Dice d2d = new Dice(Colour.RED);
        d2d.setNumber(2);
        Dice d3d = new Dice(Colour.YELLOW);
        d3d.setNumber(3);
        Dice d4d = new Dice(Colour.RED);
        d4d.setNumber(4);
        Dice d5d = new Dice(Colour.PURPLE);
        d5d.setNumber(5);
        Dice d6d = new Dice(Colour.BLUE);
        d6d.setNumber(6);
        Dice d7d = new Dice(Colour.PURPLE);
        d7d.setNumber(1);
        Dice d8d = new Dice(Colour.YELLOW);
        d8d.setNumber(2);

        p1.getWindowPatternCard().placeDice(d1d,1,0);
        p1.getWindowPatternCard().placeDice(d2d,2,0);
        p1.getWindowPatternCard().placeDice(d3d,3,0);
        p1.getWindowPatternCard().placeDice(d4d,0,0);
        p1.getWindowPatternCard().placeDice(d5d,1,1);
        p1.getWindowPatternCard().placeDice(d6d,2,2);
        p1.getWindowPatternCard().placeDice(d7d,3,2);
        p1.getWindowPatternCard().placeDice(d8d,0,1);

        Dice tmp = new Dice(p1.getDraftPool().chooseDice(6).getColour());
        tmp.setNumber(p1.getDraftPool().chooseDice(6).getNumber());


        assertFalse(table.getRoundTrack().getDiceOnRoundtrack(1).isEmpty());


        Turn turn = new Turn(p1, round,  true, table);


        turn.startTurn();

        String state = "StartTurn";


        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(tester);
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        state = "SelectingRoundTrackDice";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(d3,new Pos(0,2));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        assertEquals('Y',table.getRoundTrack().getDice(new Pos(0,2)).getColour().toString().charAt(0));

        state = "SelectingWindowDice";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(turn.getPlayer().getWindowPatternCard().getDice(new Pos(0,1)),new Pos(0,1));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        state = "MovingWindowDice";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(new Pos(2,3));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        state = "SelectingWindowDice";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(turn.getPlayer().getWindowPatternCard().getDice(new Pos(3,0)),new Pos(3,0));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        state = "MovingWindowDice";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(new Pos(2,1));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        state = "ToolBeforeDice";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));



        assertFalse(turn.getPlayer().getWindowPatternCard().getCell(new Pos(3,0)).isOccupied());
        assertFalse(turn.getPlayer().getWindowPatternCard().getCell(new Pos(0,1)).isOccupied());

        assertTrue(turn.getPlayer().getWindowPatternCard().getCell(new Pos(0,0)).isOccupied());
        assertTrue(turn.getPlayer().getWindowPatternCard().getCell(new Pos(1,0)).isOccupied());
        assertTrue(turn.getPlayer().getWindowPatternCard().getCell(new Pos(1,1)).isOccupied());
        assertTrue(turn.getPlayer().getWindowPatternCard().getCell(new Pos(2,0)).isOccupied());
        assertTrue(turn.getPlayer().getWindowPatternCard().getCell(new Pos(2,1)).isOccupied());
        assertTrue(turn.getPlayer().getWindowPatternCard().getCell(new Pos(2,2)).isOccupied());
        assertTrue(turn.getPlayer().getWindowPatternCard().getCell(new Pos(3,2)).isOccupied());
    }

    @Test
    public void testAllowedMovesAfterPositioning_WithDiceJutsPlaced(){
        ToolCard tester = pullOutThatCard(12);

        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("one");
        Player p2 = new Player("two");
        Player p3 = new Player("three");
        Player p4 = new Player("four");
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        Table table = setThatCard(12, players);

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


        Dice d1p = new Dice(Colour.GREEN);
        d1p.setNumber(1);
        Dice d2p = new Dice(Colour.RED);
        d2p.setNumber(2);
        Dice d3p = new Dice(Colour.YELLOW);
        d3p.setNumber(3);
        Dice d4p = new Dice(Colour.RED);
        d4p.setNumber(4);
        Dice d5p = new Dice(Colour.PURPLE);
        d5p.setNumber(5);
        Dice d6p = new Dice(Colour.BLUE);
        d6p.setNumber(6);
        Dice d7p = new Dice(Colour.PURPLE);
        d7p.setNumber(1);
        Dice d8p = new Dice(Colour.YELLOW);
        d8p.setNumber(2);
        Dice d9p = new Dice(Colour.BLUE);
        d9p.setNumber(2);

        List<Dice> draft = new ArrayList<>();
        draft.add(d1p);
        draft.add(d2p);
        draft.add(d3p);
        draft.add(d4p);
        draft.add(d5p);
        draft.add(d6p);
        draft.add(d7p);
        draft.add(d8p);
        draft.add(d9p);

        table.getDraftPool().addNewDices(draft);


        List<Dice> dices = new ArrayList<>();

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

        dices.add(d1);
        dices.add(d2);
        dices.add(d3);
        dices.add(d4);
        dices.add(d5);
        dices.add(d6);
        dices.add(d7);
        dices.add(d8);
        dices.add(d9);

        table.getRoundTrack().setDiceOnRoundTrack(1,dices);


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



        Dice d1d = new Dice(Colour.GREEN);
        d1d.setNumber(1);
        Dice d2d = new Dice(Colour.RED);
        d2d.setNumber(2);
        Dice d3d = new Dice(Colour.YELLOW);
        d3d.setNumber(3);
        Dice d4d = new Dice(Colour.RED);
        d4d.setNumber(4);
        Dice d5d = new Dice(Colour.PURPLE);
        d5d.setNumber(5);
        Dice d6d = new Dice(Colour.BLUE);
        d6d.setNumber(6);
        Dice d7d = new Dice(Colour.PURPLE);
        d7d.setNumber(1);
        Dice d8d = new Dice(Colour.YELLOW);
        d8d.setNumber(2);

        p1.getWindowPatternCard().placeDice(d1d,1,0);
        p1.getWindowPatternCard().placeDice(d2d,2,0);
        p1.getWindowPatternCard().placeDice(d3d,3,0);
        p1.getWindowPatternCard().placeDice(d4d,0,0);
        p1.getWindowPatternCard().placeDice(d5d,1,1);
        p1.getWindowPatternCard().placeDice(d6d,2,2);
        p1.getWindowPatternCard().placeDice(d7d,3,2);
        p1.getWindowPatternCard().placeDice(d8d,0,1);



        Dice tmp = new Dice(p1.getDraftPool().chooseDice(6).getColour());
        tmp.setNumber(p1.getDraftPool().chooseDice(6).getNumber());


        assertFalse(table.getRoundTrack().getDiceOnRoundtrack(1).isEmpty());


        Turn turn = new Turn(p1, round,  true, table);


        turn.startTurn();

        String state = "StartTurn";


        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));


        try {
            turn.receiveMove(p1.getDraftPool().chooseDice(1),new Pos(1,5));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }


        state = "ChooseDice1";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(new Pos(2, 3));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }


        state = "PositionDice1";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(tester);
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        state = "SelectingRoundTrackDice";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(d4,new Pos(0,3));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        assertEquals('Y',table.getRoundTrack().getDice(new Pos(0,2)).getColour().toString().charAt(0));

        state = "SelectingWindowDice";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(turn.getPlayer().getWindowPatternCard().getDice(new Pos(2,0)),new Pos(2,0));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        state = "MovingWindowDice";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(new Pos(1,2));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        state = "SelectingWindowDice";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(turn.getPlayer().getWindowPatternCard().getDice(new Pos(2,3)),new Pos(2,3));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        state = "MovingWindowDice";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(new Pos(3,1));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        state = "EndTurn";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        assertFalse(turn.getPlayer().getWindowPatternCard().getCell(new Pos(2,3)).isOccupied());
        assertFalse(turn.getPlayer().getWindowPatternCard().getCell(new Pos(2,0)).isOccupied());

        assertTrue(turn.getPlayer().getWindowPatternCard().getCell(new Pos(0,0)).isOccupied());
        assertTrue(turn.getPlayer().getWindowPatternCard().getCell(new Pos(0,1)).isOccupied());
        assertTrue(turn.getPlayer().getWindowPatternCard().getCell(new Pos(1,0)).isOccupied());
        assertTrue(turn.getPlayer().getWindowPatternCard().getCell(new Pos(1,1)).isOccupied());
        assertTrue(turn.getPlayer().getWindowPatternCard().getCell(new Pos(1,2)).isOccupied());
        assertTrue(turn.getPlayer().getWindowPatternCard().getCell(new Pos(2,2)).isOccupied());
        assertTrue(turn.getPlayer().getWindowPatternCard().getCell(new Pos(3,0)).isOccupied());
        assertTrue(turn.getPlayer().getWindowPatternCard().getCell(new Pos(3,1)).isOccupied());
        assertTrue(turn.getPlayer().getWindowPatternCard().getCell(new Pos(3,2)).isOccupied());

    }

    @Test
    public void testMovingOnlyOneDice() {
        ToolCard tester = pullOutThatCard(12);

        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("one");
        Player p2 = new Player("two");
        Player p3 = new Player("three");
        Player p4 = new Player("four");
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        Table table = setThatCard(12, players);

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


        List<Dice> dices = new ArrayList<>();

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

        dices.add(d1);
        dices.add(d2);
        dices.add(d3);
        dices.add(d4);
        dices.add(d5);
        dices.add(d6);
        dices.add(d7);
        dices.add(d8);
        dices.add(d9);

        table.getRoundTrack().setDiceOnRoundTrack(1,dices);


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



        Dice d1d = new Dice(Colour.GREEN);
        d1d.setNumber(1);
        Dice d2d = new Dice(Colour.RED);
        d2d.setNumber(2);
        Dice d3d = new Dice(Colour.YELLOW);
        d3d.setNumber(3);
        Dice d4d = new Dice(Colour.RED);
        d4d.setNumber(4);
        Dice d5d = new Dice(Colour.PURPLE);
        d5d.setNumber(5);
        Dice d6d = new Dice(Colour.BLUE);
        d6d.setNumber(6);
        Dice d7d = new Dice(Colour.PURPLE);
        d7d.setNumber(1);
        Dice d8d = new Dice(Colour.YELLOW);
        d8d.setNumber(2);

        p1.getWindowPatternCard().placeDice(d1d,1,0);
        p1.getWindowPatternCard().placeDice(d2d,2,0);
        p1.getWindowPatternCard().placeDice(d3d,3,0);
        p1.getWindowPatternCard().placeDice(d4d,0,0);
        p1.getWindowPatternCard().placeDice(d5d,1,1);
        p1.getWindowPatternCard().placeDice(d6d,2,2);
        p1.getWindowPatternCard().placeDice(d7d,3,2);
        p1.getWindowPatternCard().placeDice(d8d,0,1);

        //p1.getWindowPatternCard().show();


        Dice tmp = new Dice(p1.getDraftPool().chooseDice(6).getColour());
        tmp.setNumber(p1.getDraftPool().chooseDice(6).getNumber());


        assertFalse(table.getRoundTrack().getDiceOnRoundtrack(1).isEmpty());


        Turn turn = new Turn(p1, round,  true, table);


        turn.startTurn();

        String state = "StartTurn";


        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        p1.getDraftPool().chooseDice(4).setNumber(4);

        try {
            turn.receiveMove(p1.getDraftPool().chooseDice(4),new Pos(4,5));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }


        state = "ChooseDice1";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        if(p1.getDraftPool().chooseDice(4).getColour().toString().charAt(0) == 'Y') {
            try {
                turn.receiveMove(new Pos(2, 1));
            } catch (WrongMoveException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                turn.receiveMove(new Pos(0,2));
            } catch (WrongMoveException e) {
                e.printStackTrace();
            }
        }


        state = "PositionDice1";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(tester);
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        state = "SelectingRoundTrackDice";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(d3,new Pos(0,2));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        assertEquals('Y',table.getRoundTrack().getDice(new Pos(0,2)).getColour().toString().charAt(0));

        state = "SelectingWindowDice";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(turn.getPlayer().getWindowPatternCard().getDice(new Pos(3,0)),new Pos(3,0));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        state = "MovingWindowDice";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(new Pos(2,3));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        state = "SelectingWindowDice";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove("pass");
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        state = "EndTurn";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        assertFalse(turn.getPlayer().getWindowPatternCard().getCell(new Pos(3,0)).isOccupied());


        assertTrue(turn.getPlayer().getWindowPatternCard().getCell(new Pos(0,0)).isOccupied());
        assertTrue(turn.getPlayer().getWindowPatternCard().getCell(new Pos(1,0)).isOccupied());
        assertTrue(turn.getPlayer().getWindowPatternCard().getCell(new Pos(1,1)).isOccupied());
        assertTrue(turn.getPlayer().getWindowPatternCard().getCell(new Pos(2,0)).isOccupied());
        assertTrue(turn.getPlayer().getWindowPatternCard().getCell(new Pos(2,2)).isOccupied());
        assertTrue(turn.getPlayer().getWindowPatternCard().getCell(new Pos(2,3)).isOccupied());
        assertTrue(turn.getPlayer().getWindowPatternCard().getCell(new Pos(3,2)).isOccupied());

    }

    @Test
    public void testInFirstRound() {
        ToolCard tester = pullOutThatCard(12);

        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("one");
        Player p2 = new Player("two");
        Player p3 = new Player("three");
        Player p4 = new Player("four");
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        Table table = setThatCard(12, players);

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

        Round round = new Round(players, 1, table, match, timerMove);

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

        p1.getWindowPatternCard().placeDice(d1, 1, 0);
        p1.getWindowPatternCard().placeDice(d2, 2, 0);
        p1.getWindowPatternCard().placeDice(d3, 3, 0);

        Dice tmp = new Dice(p1.getDraftPool().chooseDice(6).getColour());
        tmp.setNumber(p1.getDraftPool().chooseDice(6).getNumber());


        Turn turn = new Turn(p1, round,  true, table);


        turn.startTurn();

        String state = "StartTurn";

        assertEquals(state, lastName(turn.getState().toString(), state.length() + 1));


        try {
            turn.receiveMove(tester);
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }


        state = "SelectingRoundTrackDice";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

    }

}

