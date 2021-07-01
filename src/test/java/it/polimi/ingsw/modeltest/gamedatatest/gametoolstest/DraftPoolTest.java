package it.polimi.ingsw.modeltest.gamedatatest.gametoolstest;

import it.polimi.ingsw.model.gamedata.Colour;
import it.polimi.ingsw.model.gamedata.Property;
import it.polimi.ingsw.model.gamedata.gametools.Dice;
import it.polimi.ingsw.model.gamedata.gametools.DiceBag;
import it.polimi.ingsw.model.gamedata.gametools.DraftPool;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DraftPoolTest{


    @Test
    public void testRemoveDice(){
        DraftPool tester = new DraftPool(4);
        Dice y = new Dice();
        int j;
        List<Dice> x = new ArrayList<Dice>(9);
        for(int i = 0; i < 9; i++){
            x.add(y);
        }
        tester.addNewDices(x);
        j = tester.getNumOfDices();
        tester.removeDice(1);
        assertEquals(tester.getNumOfDices(), (j - 1));
    }

    @Test
    public void testNeverEmpty(){
        int i;
        DraftPool tester = new DraftPool();
        Dice y = new Dice();
        List<Dice> x = new ArrayList<Dice>(9);
        for(i = 0; i < 9; i++){
            x.add(y);
        }
        tester.addNewDices(x);
        for(i = 0; tester.getNumOfDices() > 1;){
            tester.removeDice(i);
        }
        assertEquals(1,tester.getNumOfDices());
    }

    @Test
    public void testAddDices(){
        DraftPool tester = new DraftPool();
        DiceBag test = new DiceBag();
        tester.addNewDices(test.pullOut());
    }

    @Test
    public void testFindDice(){
        DraftPool tester = new DraftPool();
        Property prop = new Property(Colour.RED,true);
        Dice d;
        for(int i = 0; i < 5; i++) {
            d = new Dice();
            d.setColour(prop.getColour().toString());
            d.setNumber(i+1);
            assertTrue(tester.getDraftPool().add(d));
        }
        prop = new Property(Colour.YELLOW,true);
        prop.setNumber(5);
        d = new Dice();
        d.setColour(prop.getColour().toString());
        assertFalse(tester.findDice(d));
        assertTrue(tester.getDraftPool().add(d));
        assertTrue(tester.findDice(d));
    }

    @Test
    public void testFindInPosition(){
        DraftPool tester = new DraftPool();
        Property prop = new Property(Colour.RED,true);
        Dice d;
        prop.setNumber(5);
        for(int i = 0; i < 6; i++) {
            d = new Dice();
            assertTrue(tester.getDraftPool().add(d));
        }
        d = new Dice();
        d.setColour(prop.getColour().toString());
        tester.getDraftPool().add(d);
        prop = new Property(Colour.GREEN,true);
        assertTrue(tester.findDice(d,tester.getNumOfDices()-1));
    }

    @Test
    public void testRemoveAndFindDice(){
        DraftPool tester = new DraftPool();
        Dice d1 = new Dice(Colour.BLUE);
        tester.getDraftPool().add(d1);
        Dice d;
        for(int i = 0; i < 6; i++) {
            d = new Dice();
            assertTrue(tester.getDraftPool().add(d));
        }
        assertTrue(tester.findDice(d1));
        int numdices = tester.getNumOfDices();
        tester.removeDice(tester.getNumOfDices()-1);
        assertEquals(numdices-1, tester.getNumOfDices());
        tester.findDice(d1);
    }




}
