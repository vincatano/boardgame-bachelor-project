package it.polimi.ingsw.turntest;

import it.polimi.ingsw.controller.Match;
import it.polimi.ingsw.controller.Manager;
import it.polimi.ingsw.model.gamedata.*;
import it.polimi.ingsw.model.gamedata.gametools.*;
import it.polimi.ingsw.model.gamelogic.Round;
import it.polimi.ingsw.model.gamelogic.turn.ChooseDice1;
import it.polimi.ingsw.model.gamelogic.turn.Turn;
import it.polimi.ingsw.model.gamelogic.turn.TurnState;
import it.polimi.ingsw.model.gamelogic.turn.moveexceptions.WrongMoveException;
import it.polimi.ingsw.view.virtualview.VirtualViewObserver;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.turntest.TurnTest.lastName;
import static it.polimi.ingsw.turntest.TurnTest.pullOutThatCard;
import static it.polimi.ingsw.turntest.TurnTest.setThatCard;
import static org.junit.jupiter.api.Assertions.*;

public class ToolCard5Test {
    long timerCard = 0;
    long timerMove = 0;
    @Test
    public void testingAllowedMoves() {
        ToolCard tester = pullOutThatCard(5);

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


        while (players.get(0).getToolCards().get(0).getID() != 5 && players.get(0).getToolCards().get(1).getID() != 5 && players.get(0).getToolCards().get(2).getID() != 5) {
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

        Round round = new Round(players, 2, table,match, timerMove);

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

        table.getRoundTrack().setDiceOnRoundTrack(1,table.getDraftPool().getDraftPool());

        table.getDraftPool().addNewDices(table.getDiceFromBag());

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

        List<List<Dice>> tmp = new ArrayList<>();
        for(int i = 0; i < table.getRoundTrack().getRoundTrack().size(); i++){
            tmp.add(new ArrayList<>());
        }

        for (int i = 0; i < table.getRoundTrack().getRoundTrack().size()-1;i++){
            for(int j = 0; j < table.getRoundTrack().getRoundTrack().get(i).size()-1;i++){
                Dice d = new Dice(table.getRoundTrack().getRoundTrack().get(i).get(j).getColour());
                d.setNumber(table.getRoundTrack().getRoundTrack().get(i).get(j).getNumber());
                tmp.get(i).add(j,d);
            }
        }

        TurnState turnstate = new ChooseDice1(turn,table.getDraftPool().chooseDice(2),new Pos(2,0));

        turn.setState(turnstate);

        String state = "ChooseDice1";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(tester);
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        state = "SelectingRoundTrackDice";
        assertEquals(state,lastName(turn.getState().toString(),state.length()+1));

        Dice d = new Dice(p1.getRoundTrack().getDice(new Pos(0,0)).getColour());
        d.setNumber(p1.getRoundTrack().getDice(new Pos(0,0)).getNumber());

        try {
            turn.receiveMove(p1.getRoundTrack().getDice(new Pos(0,0)),new Pos(0,0));
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }


        for(int i = 0; i < p1.getDraftPool().getDraftPool().size(); i++){
                assertTrue(p1.getDraftPool().getDraftPool().get(i).areEquals(table.getDraftPool().getDraftPool().get(i)));
        }

        assertTrue(p1.getDraftPool().chooseDice(2).areEquals(d));

        for(int i = 0; i < p1.getRoundTrack().getRoundTrack().size(); i++){
            for(int j = 0; j < p1.getRoundTrack().getRoundTrack().get(i).size(); j++){
                if(i != 0 && j != 0)
                assertTrue(p1.getRoundTrack().getRoundTrack().get(i).get(j).areEquals(tmp.get(i).get(j)));
            }
        }

        assertTrue(p1.getDraftPool().getDraftPool().get(2).areEquals(d));


        state = "ChooseDice2";
        assertEquals(state,lastName(turn.getState().toString(),state.length()+1));

        p1.getDraftPool().chooseDice(2).setNumber(5);

            try {
                turn.receiveMove(new Pos(1, 4));
            } catch (WrongMoveException e) {
                e.printStackTrace();
            }

        state = "EndTurn";

        assertEquals(state, lastName(turn.getState().toString(),state.length()+1));
    }

    @Test
    public void testingFirstSelectionWrong() {
        ToolCard tester = pullOutThatCard(5);

        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("one");
        Player p2 = new Player("two");
        Player p3 = new Player("three");
        Player p4 = new Player("four");
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        Table table = setThatCard(5,players);

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

        Round round = new Round(players, 2, table,match, timerMove);

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


        Dice d4 = new Dice(Colour.YELLOW);
        p1.getWindowPatternCard().placeDice(d4, 1, 1);

        table.getRoundTrack().setDiceOnRoundTrack(1, table.getDraftPool().getDraftPool());

        Turn turn = new Turn(p1, round,  true, table);

        TurnState turnstate = new ChooseDice1(turn,d4,new Pos(1,1));

        turn.setState(turnstate);

        String state = "ChooseDice1";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(tester);
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        state = "SelectingRoundTrackDice";
        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        Dice d = new Dice(p1.getRoundTrack().getDice(new Pos(0, 0)).getColour());
        d.setNumber(p1.getRoundTrack().getDice(new Pos(0, 0)).getNumber() + 1);

        assertThrows(WrongMoveException.class,()-> turn.receiveMove(d,new Pos(0,0)));

        assertFalse(d.areEquals(table.getRoundTrack().getDice(new Pos(0,0))));

    }

    @Test
    public void testingAutomatedOperationListIsRight() {
        ToolCard tester = pullOutThatCard(5);

        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("one");
        Player p2 = new Player("two");
        Player p3 = new Player("three");
        Player p4 = new Player("four");
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        Table table = setThatCard(5,players);

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

        Round round = new Round(players, 2, table,match, timerMove);

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

        Dice d4 = new Dice(Colour.YELLOW);
        p1.getWindowPatternCard().placeDice(d4, 1, 1);

        table.getRoundTrack().setDiceOnRoundTrack(1, table.getDraftPool().getDraftPool());

        Turn turn = new Turn(p1, round,  true, table);

        TurnState turnstate = new ChooseDice1(turn,d4,new Pos(1,1));

        turn.setState(turnstate);

        String state = "ChooseDice1";

        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        try {
            turn.receiveMove(tester);
        } catch (WrongMoveException e) {
            e.printStackTrace();
        }

        state = "SelectingRoundTrackDice";
        assertEquals(state, lastName(turn.getState().toString(), state.length()+1));

        ToolCard tmp = pullOutThatCard(5);

        for(int i = 0; i < tmp.getAutomatedoperationlist().size(); i++){
            assertEquals(tmp.getAutomatedoperationlist().get(i),turn.getToolCard().getAutomatedoperationlist().get(i));
        }

    }

    @Test
    public void testingNotAllowedInFirstRound() {
        ToolCard tester = pullOutThatCard(5);

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

            while (players.get(0).getToolCards().get(0).getID() != 5 && players.get(0).getToolCards().get(1).getID() != 5 && players.get(0).getToolCards().get(2).getID() != 5) {
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

        p1.setPublicObjects(publicObjects);


        Turn turn = new Turn(p1, round,  true, table);

        turn.startTurn();

        assertEquals("StartTurn", lastName(turn.getState().toString(), 10));

        assertThrows(WrongMoveException.class,()->{turn.receiveMove(tester);});
    }

}
