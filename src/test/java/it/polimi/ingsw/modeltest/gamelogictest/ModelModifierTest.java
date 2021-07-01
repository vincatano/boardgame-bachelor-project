package it.polimi.ingsw.modeltest.gamelogictest;

import it.polimi.ingsw.model.gamedata.Colour;
import it.polimi.ingsw.model.gamedata.Player;
import it.polimi.ingsw.model.gamedata.Pos;
import it.polimi.ingsw.model.gamedata.Table;
import it.polimi.ingsw.model.gamedata.gametools.*;
import it.polimi.ingsw.model.gamelogic.ModelModifier;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ModelModifierTest {

    @Test
    public void testLaunchDice(){
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
        ModelModifier tester = new ModelModifier(table, p1);
        Dice d = new Dice(Colour.GREEN);
        ArrayList<Dice> dices = new ArrayList<>();

        table.getDiceBag().setNumPlayers(4);
        table.getDraftPool().addNewDices(table.getDiceFromBag());
        int x = table.getDraftPool().getNumOfDices();
        dices.addAll(table.getDraftPool().getDraftPool());

        Pos pos = new Pos(5,0);
        tester.launchDice(d,pos,null,null);

        assertEquals(x,table.getDraftPool().getNumOfDices());

        assertEquals(d.getNumber(),table.getDraftPool().chooseDice(5).getNumber());

        assertNotEquals(d,table.getDraftPool().chooseDice(5));

        for(int i = 0; i < table.getDraftPool().getNumOfDices();i++){
            if(table.getDraftPool().chooseDice(i) != d)
                assertEquals(table.getDraftPool().chooseDice(i),dices.get(i));
        }
    }

    @Test
    public void testResetDraftPool() {
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
        ModelModifier tester = new ModelModifier(table, p1);
        table.getDiceBag().setNumPlayers(4);
        table.getDraftPool().addNewDices(table.getDiceFromBag());

        int x = table.getDraftPool().getNumOfDices();

        tester.resetDraftPool(null,null,null,null);

        assertEquals(x,table.getDraftPool().getNumOfDices());
    }

    @Test
    public void testSpinDice(){
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
        ModelModifier tester = new ModelModifier(table, p1);
        table.getDiceBag().setNumPlayers(4);
        table.getDraftPool().addNewDices(table.getDiceFromBag());
        Dice d = table.getDraftPool().chooseDice(0);
        Pos pos = new Pos(0,0);

        d.setNumber(1);
        tester.spinDice(d,pos,null,null);
        assertEquals(6,table.getDraftPool().chooseDice(0).getNumber());

        d.setNumber(2);
        tester.spinDice(d,pos,null,null);
        assertEquals(5,table.getDraftPool().chooseDice(0).getNumber());

        d.setNumber(3);
        tester.spinDice(d,pos,null,null);
        assertEquals(4,table.getDraftPool().chooseDice(0).getNumber());

        d.setNumber(4);
        tester.spinDice(d,pos,null,null);
        assertEquals(3,table.getDraftPool().chooseDice(0).getNumber());

        d.setNumber(5);
        tester.spinDice(d,pos,null,null);
        assertEquals(2,table.getDraftPool().chooseDice(0).getNumber());

        d.setNumber(6);
        tester.spinDice(d,pos,null,null);
        assertEquals(1,table.getDraftPool().chooseDice(0).getNumber());
    }

    @Test
    public void testNewDice(){
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
        ModelModifier tester = new ModelModifier(table, p1);
        table.getDiceBag().setNumPlayers(4);
        table.getDraftPool().addNewDices(table.getDiceFromBag());
        Pos pos = new Pos(0,0);

        int x = table.getDraftPool().getNumOfDices();
        int b = table.getDiceBag().remainingDices();

        tester.newDice(table.getDraftPool().chooseDice(0),pos,null,null);

        assertEquals(x,table.getDraftPool().getNumOfDices());

        assertEquals(b,table.getDiceBag().remainingDices());
    }

    @Test
    public void testPositionDiceFromDraft(){
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("one");
        Player p2 = new Player("two");
        Player p3 = new Player("three");
        Player p4 = new Player("four");
        WindowPatternCard windowPatternCard1 = new WindowPatternCard(50,10,"noName");
        WindowPatternCard windowPatternCard2 = new WindowPatternCard(50,10,"noName");
        WindowPatternCard windowPatternCard3 = new WindowPatternCard(50,10,"noName");
        WindowPatternCard windowPatternCard4 = new WindowPatternCard(50,10,"noName");       p1.setMyWindow(windowPatternCard1);
       p2.setMyWindow(windowPatternCard2);
       p3.setMyWindow(windowPatternCard3);
       p4.setMyWindow(windowPatternCard4);
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        Table table = new Table(players);
        table.getDiceBag().setNumPlayers(4);
        table.getDraftPool().addNewDices(table.getDiceFromBag());


        ModelModifier tester = new ModelModifier(table, p1);


        Pos posdice = new Pos(8,0);

        Pos poscard = new Pos(3,4);

        int x = table.getDraftPool().getNumOfDices();

        tester.positionDiceFromDraft(null,posdice,poscard);

        assertEquals(x-1,table.getDraftPool().getNumOfDices());

        assertTrue(p1.getWindowPatternCard().getCell(poscard).isOccupied());
    }

    @Test
    public void testPositionDiceFromWindow(){
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("one");
        Player p2 = new Player("two");
        Player p3 = new Player("three");
        Player p4 = new Player("four");

        ArrayList<WindowPatternCard> windows = new ArrayList<>();
        WindowPatternCard windowPatternCard = new WindowPatternCard(50,10,"noName");
        windows.add(windowPatternCard);


        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        Table table = new Table(players);

        table.getDiceBag().setNumPlayers(4);

        table.getDraftPool().addNewDices(table.getDiceFromBag());

        Dice d = new Dice();

        Pos posdice = new Pos(2,1);

        Pos poscard = new Pos(1,0);

        p1.setMyWindow(windows.get(0));

        p1.getWindowPatternCard().placeDice(d, posdice.getX(),posdice.getY());

        ModelModifier tester = new ModelModifier(table, p1);

        int cont = 0;

        for (List<Cell> x: p1.getWindowPatternCard().getMatr()){
            for (Cell cell: x){
                if(cell.isOccupied())
                    cont++;
            }
        }

        tester.positionDiceFromWindow(d,posdice,poscard);

        int cont2 = 0;

        for (List<Cell> x: p1.getWindowPatternCard().getMatr()){
            for (Cell cell: x){
                if(cell.isOccupied())
                    cont2++;
            }
        }


        assertEquals(cont,cont2);

        assertFalse(p1.getWindowPatternCard().getCell(posdice).isOccupied());

        assertTrue(p1.getWindowPatternCard().getCell(poscard).isOccupied());

    }

    @Test
    public void testChangeDiceValue(){
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("one");
        Player p2 = new Player("two");
        Player p3 = new Player("three");
        Player p4 = new Player("four");
       WindowPatternCard windowPatternCard1 = new WindowPatternCard(50,10,"noName");
       WindowPatternCard windowPatternCard2 = new WindowPatternCard(50,10,"noName");
       WindowPatternCard windowPatternCard3 = new WindowPatternCard(50,10,"noName");
       WindowPatternCard windowPatternCard4 = new WindowPatternCard(50,10,"noName");
       p1.setMyWindow(windowPatternCard1);
       p2.setMyWindow(windowPatternCard2);
       p3.setMyWindow(windowPatternCard3);
       p4.setMyWindow(windowPatternCard4);
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        Table table = new Table(players);
        ModelModifier tester = new ModelModifier(table, p1);
        table.getDiceBag().setNumPlayers(4);
        table.getDraftPool().addNewDices(table.getDiceFromBag());
        Dice d1 = new Dice();
        Dice d2 = new Dice();

        d1.setNumber(1);
        d2.setNumber(5);

        Pos posdice = new Pos(2,3);

        tester.changeDiceValue(d1,posdice,d2);

        assertEquals(5,d1.getNumber());

        assertEquals(5,table.getDraftPool().chooseDice(posdice.getX()).getNumber());

    }


}
