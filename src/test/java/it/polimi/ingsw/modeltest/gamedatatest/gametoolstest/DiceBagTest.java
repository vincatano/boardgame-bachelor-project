package it.polimi.ingsw.modeltest.gamedatatest.gametoolstest;

import it.polimi.ingsw.model.gamedata.Colour;
import it.polimi.ingsw.model.gamedata.gametools.Dice;
import it.polimi.ingsw.model.gamedata.gametools.DiceBag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DiceBagTest {

    @Test
    public void testCreation(){
        DiceBag tester = new DiceBag();
        assertEquals( 90,tester.remainingDices());
    }

    @Test
    public void testEmptyBag(){
        DiceBag tester = new DiceBag();
        List<Dice> d = new ArrayList<>();
        tester.setNumPlayers(4);
        for(int i = 0; i < 10; i++){
            tester.pullOut();
        }
        assertEquals(0,tester.remainingDices());
    }

    @Test
    public void testExactNumber(){
        DiceBag tester = new DiceBag();
        List<Dice> tmp;
        int r = 0,g = 0,y = 0,b = 0,p = 0,w = 0;
        tester.setNumPlayers(4);
        for(int i = 0; i < 10; i++) {
            tmp = tester.pullOut();
            for (Dice x: tmp){
                if(x.getColour() == Colour.WHITE)
                    w++;
                else if(x.getColour() == Colour.BLUE)
                    b++;
                else if(x.getColour() == Colour.YELLOW)
                    y++;
                else if(x.getColour() == Colour.GREEN)
                    g++;
                else if(x.getColour() == Colour.RED)
                    r++;
                else if(x.getColour() == Colour.PURPLE)
                    p++;
            }
        }
        assertEquals(18, r);
        assertEquals(18, g);
        assertEquals(18, y);
        assertEquals(18, b);
        assertEquals(18, p);
        assertEquals(0, w);
    }

    @Test
    public void testAddDice(){
        DiceBag tester = new DiceBag();
        Dice dice = new Dice();
        int remaining = tester.remainingDices();
        boolean ended = false;
        tester.addDice(dice);
        assertEquals(remaining+1,tester.remainingDices());
        List<Dice> test = tester.pullOut(91);
        for (Dice x: test){
            if(x.areEquals(dice)){
                if(x == dice) {
                    assertTrue(true);
                    ended = true;
                }
            }
        }
        if(!ended)
            fail("Test failed");
    }

    @Test
    public void testPullOut(){
        DiceBag tester = new DiceBag();
        List<Dice> dices;
        tester.setNumPlayers(3);
        dices = tester.pullOut();
        assertEquals(7,dices.size());
    }

    @Test
    public void testNumPullOut(){
        DiceBag tester = new DiceBag();
        List<Dice> dices;
        dices = tester.pullOut(24);
        assertEquals(24,dices.size());
    }
}
