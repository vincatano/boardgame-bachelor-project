package it.polimi.ingsw.modeltest.gamedatatest;

import it.polimi.ingsw.model.gamedata.Colour;
import it.polimi.ingsw.model.gamedata.Player;
import it.polimi.ingsw.model.gamedata.Rules;
import it.polimi.ingsw.model.gamedata.gametools.Dice;
import it.polimi.ingsw.model.gamedata.gametools.WindowPatternCard;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

public class RulesTest {

    @Test
    public void testCouple(){

        String rule = "1,2";
        WindowPatternCard windowPatternCard = new WindowPatternCard(50,10,"noName");
        Player player = new Player("vincenzo");
        player.setMyWindow(windowPatternCard);

        Dice d1 = new Dice();
        d1.setNumber(1);


        Dice d2 = new Dice();
        d2.setNumber(2);

        Dice d3 = new Dice();
        d3.setNumber(3);

        player.getWindowPatternCard().placeDice(d1,0,0);
        player.getWindowPatternCard().placeDice(d2,0,1);
        player.getWindowPatternCard().placeDice(d3,0,2);
        player.getWindowPatternCard().placeDice(d2,0,3);
        player.getWindowPatternCard().placeDice(d3,0,4);
        player.getWindowPatternCard().placeDice(d1,1,0);
        player.getWindowPatternCard().placeDice(d2,1,1);
        player.getWindowPatternCard().placeDice(d2,1,2);
        player.getWindowPatternCard().placeDice(d3,1,3);
        player.getWindowPatternCard().placeDice(d3,1,4);
        player.getWindowPatternCard().placeDice(d1,2,0);
        player.getWindowPatternCard().placeDice(d2,2,1);
        player.getWindowPatternCard().placeDice(d3,2,2);
        player.getWindowPatternCard().placeDice(d2,2,3);
        player.getWindowPatternCard().placeDice(d1,2,4);
        player.getWindowPatternCard().placeDice(d2,3,0);
        player.getWindowPatternCard().placeDice(d3,3,1);
        player.getWindowPatternCard().placeDice(d2,3,2);
        player.getWindowPatternCard().placeDice(d2,3,3);
        player.getWindowPatternCard().placeDice(d3,3,4);

        Rules test = new Rules();

        assertEquals(4,test.verify(rule,windowPatternCard));
    }

    @Test
    public void testFiveNumbers(){

        String rule = "1,2,3,4,5,6";
        WindowPatternCard windowPatternCard = new WindowPatternCard(50,10,"noName");
        Player player = new Player("vincenzo");
        player.setMyWindow(windowPatternCard);

        Dice d1 = new Dice();
        d1.setNumber(1);


        Dice d2 = new Dice();
        d2.setNumber(2);

        Dice d3 = new Dice();
        d3.setNumber(3);

        Dice d4 = new Dice();
        d4.setNumber(4);

        Dice d5 = new Dice();
        d5.setNumber(5);

        Dice d6 = new Dice();
        d6.setNumber(6);

        player.getWindowPatternCard().placeDice(d1,0,0);
        player.getWindowPatternCard().placeDice(d2,0,1);
        player.getWindowPatternCard().placeDice(d3,0,2);
        player.getWindowPatternCard().placeDice(d4,0,3);
        player.getWindowPatternCard().placeDice(d5,0,4);
        player.getWindowPatternCard().placeDice(d6,1,0);
        player.getWindowPatternCard().placeDice(d1,1,1);
        player.getWindowPatternCard().placeDice(d2,1,2);
        player.getWindowPatternCard().placeDice(d3,1,3);
        player.getWindowPatternCard().placeDice(d4,1,4);
        player.getWindowPatternCard().placeDice(d5,2,0);
        player.getWindowPatternCard().placeDice(d6,2,1);
        player.getWindowPatternCard().placeDice(d3,2,2);
        player.getWindowPatternCard().placeDice(d2,2,3);
        player.getWindowPatternCard().placeDice(d1,2,4);
        player.getWindowPatternCard().placeDice(d2,3,0);
        player.getWindowPatternCard().placeDice(d3,3,1);
        player.getWindowPatternCard().placeDice(d2,3,2);
        player.getWindowPatternCard().placeDice(d2,3,3);
        player.getWindowPatternCard().placeDice(d3,3,4);

        Rules test = new Rules();

        assertEquals(2,test.verify(rule,windowPatternCard));
    }

    @Test
    public void testFiveColours(){

        String rule = "Y,G,B,P,R";
        WindowPatternCard windowPatternCard = new WindowPatternCard(50,10,"noName");
        Player player = new Player("vincenzo");
        player.setMyWindow(windowPatternCard);

        Dice d1 = new Dice(Colour.RED);
        d1.setNumber(1);


        Dice d2 = new Dice(Colour.YELLOW);
        d2.setNumber(2);

        Dice d3 = new Dice(Colour.GREEN);
        d3.setNumber(3);

        Dice d4 = new Dice(Colour.BLUE);
        d4.setNumber(4);

        Dice d5 = new Dice(Colour.PURPLE);
        d5.setNumber(5);

        Dice d6 = new Dice(Colour.WHITE);
        d6.setNumber(6);

        player.getWindowPatternCard().placeDice(d1,0,0);
        player.getWindowPatternCard().placeDice(d2,0,1);
        player.getWindowPatternCard().placeDice(d3,0,2);
        player.getWindowPatternCard().placeDice(d4,0,3);
        player.getWindowPatternCard().placeDice(d5,0,4);
        player.getWindowPatternCard().placeDice(d6,1,0);
        player.getWindowPatternCard().placeDice(d1,1,1);
        player.getWindowPatternCard().placeDice(d2,1,2);
        player.getWindowPatternCard().placeDice(d3,1,3);
        player.getWindowPatternCard().placeDice(d4,1,4);
        player.getWindowPatternCard().placeDice(d5,2,0);
        player.getWindowPatternCard().placeDice(d6,2,1);
        player.getWindowPatternCard().placeDice(d3,2,2);
        player.getWindowPatternCard().placeDice(d2,2,3);
        player.getWindowPatternCard().placeDice(d1,2,4);
        player.getWindowPatternCard().placeDice(d2,3,0);
        player.getWindowPatternCard().placeDice(d3,3,1);
        player.getWindowPatternCard().placeDice(d2,3,2);
        player.getWindowPatternCard().placeDice(d2,3,3);
        player.getWindowPatternCard().placeDice(d3,3,4);

        Rules test = new Rules();

        assertEquals(2,test.verify(rule,windowPatternCard));
    }

    @Test
    public void testOneColour(){

        String rule = "Y";
        WindowPatternCard windowPatternCard = new WindowPatternCard(50,10,"noName");
        Player player = new Player("vincenzo");
        player.setMyWindow(windowPatternCard);

        Dice d1 = new Dice(Colour.RED);
        d1.setNumber(1);


        Dice d2 = new Dice(Colour.YELLOW);
        d2.setNumber(2);

        Dice d3 = new Dice(Colour.GREEN);
        d3.setNumber(3);

        Dice d4 = new Dice(Colour.BLUE);
        d4.setNumber(4);

        Dice d5 = new Dice(Colour.PURPLE);
        d5.setNumber(5);

        Dice d6 = new Dice(Colour.WHITE);
        d6.setNumber(6);

        player.getWindowPatternCard().placeDice(d1,0,0);
        player.getWindowPatternCard().placeDice(d2,0,1);
        player.getWindowPatternCard().placeDice(d3,0,2);
        player.getWindowPatternCard().placeDice(d4,0,3);
        player.getWindowPatternCard().placeDice(d5,0,4);
        player.getWindowPatternCard().placeDice(d6,1,0);
        player.getWindowPatternCard().placeDice(d1,1,1);
        player.getWindowPatternCard().placeDice(d2,1,2);
        player.getWindowPatternCard().placeDice(d3,1,3);
        player.getWindowPatternCard().placeDice(d4,1,4);
        player.getWindowPatternCard().placeDice(d5,2,0);
        player.getWindowPatternCard().placeDice(d6,2,1);
        player.getWindowPatternCard().placeDice(d3,2,2);
        player.getWindowPatternCard().placeDice(d2,2,3);
        player.getWindowPatternCard().placeDice(d1,2,4);
        player.getWindowPatternCard().placeDice(d2,3,0);
        player.getWindowPatternCard().placeDice(d3,3,1);
        player.getWindowPatternCard().placeDice(d2,3,2);
        player.getWindowPatternCard().placeDice(d2,3,3);
        player.getWindowPatternCard().placeDice(d3,3,4);

        Rules test = new Rules();

        assertEquals(12,test.verify(rule,windowPatternCard));
    }

    @Test
    public void testRowNoColor(){

        String rule = "NOCOLOR";
        String direction = "ROW";
        WindowPatternCard windowPatternCard = new WindowPatternCard(50,10,"noName");
        Player player = new Player("vincenzo");
        player.setMyWindow(windowPatternCard);

        Dice d1 = new Dice(Colour.RED);
        d1.setNumber(1);


        Dice d2 = new Dice(Colour.YELLOW);
        d2.setNumber(2);

        Dice d3 = new Dice(Colour.GREEN);
        d3.setNumber(3);

        Dice d4 = new Dice(Colour.BLUE);
        d4.setNumber(4);

        Dice d5 = new Dice(Colour.PURPLE);
        d5.setNumber(5);

        Dice d6 = new Dice(Colour.WHITE);
        d6.setNumber(6);

        player.getWindowPatternCard().placeDice(d1,0,0);
        player.getWindowPatternCard().placeDice(d2,0,1);
        player.getWindowPatternCard().placeDice(d3,0,2);
        player.getWindowPatternCard().placeDice(d4,0,3);
        player.getWindowPatternCard().placeDice(d5,0,4);
        player.getWindowPatternCard().placeDice(d6,1,0);
        player.getWindowPatternCard().placeDice(d1,1,1);
        player.getWindowPatternCard().placeDice(d2,1,2);
        player.getWindowPatternCard().placeDice(d3,1,3);
        player.getWindowPatternCard().placeDice(d4,1,4);
        player.getWindowPatternCard().placeDice(d5,2,0);
        player.getWindowPatternCard().placeDice(d6,2,1);
        player.getWindowPatternCard().placeDice(d3,2,2);
        player.getWindowPatternCard().placeDice(d2,2,3);
        player.getWindowPatternCard().placeDice(d1,2,4);
        player.getWindowPatternCard().placeDice(d2,3,0);
        player.getWindowPatternCard().placeDice(d3,3,1);
        player.getWindowPatternCard().placeDice(d2,3,2);
        player.getWindowPatternCard().placeDice(d2,3,3);
        player.getWindowPatternCard().placeDice(d3,3,4);

        Rules test = new Rules();
        test.verify(direction,windowPatternCard);

        assertEquals(3,test.verify(rule,windowPatternCard));
    }

    @Test
    public void testColumnNoColor(){

        String rule = "NOCOLOR";
        String direction = "COLUMN";
        WindowPatternCard windowPatternCard = new WindowPatternCard(50,10,"noName");
        Player player = new Player("vincenzo");
        player.setMyWindow(windowPatternCard);

        Dice d1 = new Dice(Colour.RED);
        d1.setNumber(1);


        Dice d2 = new Dice(Colour.YELLOW);
        d2.setNumber(2);

        Dice d3 = new Dice(Colour.GREEN);
        d3.setNumber(3);

        Dice d4 = new Dice(Colour.BLUE);
        d4.setNumber(4);

        Dice d5 = new Dice(Colour.PURPLE);
        d5.setNumber(5);

        Dice d6 = new Dice(Colour.WHITE);
        d6.setNumber(6);

        player.getWindowPatternCard().placeDice(d1,0,0);
        player.getWindowPatternCard().placeDice(d2,0,1);
        player.getWindowPatternCard().placeDice(d3,0,2);
        player.getWindowPatternCard().placeDice(d4,0,3);
        player.getWindowPatternCard().placeDice(d5,0,4);
        player.getWindowPatternCard().placeDice(d6,1,0);
        player.getWindowPatternCard().placeDice(d1,1,1);
        player.getWindowPatternCard().placeDice(d2,1,2);
        player.getWindowPatternCard().placeDice(d3,1,3);
        player.getWindowPatternCard().placeDice(d4,1,4);
        player.getWindowPatternCard().placeDice(d5,2,0);
        player.getWindowPatternCard().placeDice(d6,2,1);
        player.getWindowPatternCard().placeDice(d3,2,2);
        player.getWindowPatternCard().placeDice(d2,2,3);
        player.getWindowPatternCard().placeDice(d1,2,4);
        player.getWindowPatternCard().placeDice(d2,3,0);
        player.getWindowPatternCard().placeDice(d3,3,1);
        player.getWindowPatternCard().placeDice(d2,3,2);
        player.getWindowPatternCard().placeDice(d2,3,3);
        player.getWindowPatternCard().placeDice(d3,3,4);

        Rules test = new Rules();
        test.verify(direction,windowPatternCard);

        assertEquals(3,test.verify(rule,windowPatternCard));
    }

    @Test
    public void testRowNoNumber(){

        String rule = "NOVALUE";
        String direction = "ROW";
        WindowPatternCard windowPatternCard = new WindowPatternCard(50,10,"noName");
        Player player = new Player("vincenzo");
        player.setMyWindow(windowPatternCard);

        Dice d1 = new Dice(Colour.RED);
        d1.setNumber(1);


        Dice d2 = new Dice(Colour.YELLOW);
        d2.setNumber(2);

        Dice d3 = new Dice(Colour.GREEN);
        d3.setNumber(3);

        Dice d4 = new Dice(Colour.BLUE);
        d4.setNumber(4);

        Dice d5 = new Dice(Colour.PURPLE);
        d5.setNumber(5);

        Dice d6 = new Dice(Colour.WHITE);
        d6.setNumber(6);

        player.getWindowPatternCard().placeDice(d1,0,0);
        player.getWindowPatternCard().placeDice(d2,0,1);
        player.getWindowPatternCard().placeDice(d3,0,2);
        player.getWindowPatternCard().placeDice(d4,0,3);
        player.getWindowPatternCard().placeDice(d5,0,4);
        player.getWindowPatternCard().placeDice(d6,1,0);
        player.getWindowPatternCard().placeDice(d1,1,1);
        player.getWindowPatternCard().placeDice(d2,1,2);
        player.getWindowPatternCard().placeDice(d3,1,3);
        player.getWindowPatternCard().placeDice(d4,1,4);
        player.getWindowPatternCard().placeDice(d5,2,0);
        player.getWindowPatternCard().placeDice(d6,2,1);
        player.getWindowPatternCard().placeDice(d3,2,2);
        player.getWindowPatternCard().placeDice(d2,2,3);
        player.getWindowPatternCard().placeDice(d1,2,4);
        player.getWindowPatternCard().placeDice(d2,3,0);
        player.getWindowPatternCard().placeDice(d3,3,1);
        player.getWindowPatternCard().placeDice(d2,3,2);
        player.getWindowPatternCard().placeDice(d2,3,3);
        player.getWindowPatternCard().placeDice(d3,3,4);

        Rules test = new Rules();
        test.verify(direction,windowPatternCard);

        assertEquals(3,test.verify(rule,windowPatternCard));
    }

    @Test
    public void testColumnNoNumber(){

        String rule = "NOVALUE";
        String direction = "COLUMN";
        WindowPatternCard windowPatternCard = new WindowPatternCard(50,10,"noName");
        Player player = new Player("vincenzo");
        player.setMyWindow(windowPatternCard);

        Dice d1 = new Dice(Colour.RED);
        d1.setNumber(1);


        Dice d2 = new Dice(Colour.YELLOW);
        d2.setNumber(2);

        Dice d3 = new Dice(Colour.GREEN);
        d3.setNumber(3);

        Dice d4 = new Dice(Colour.BLUE);
        d4.setNumber(4);

        Dice d5 = new Dice(Colour.PURPLE);
        d5.setNumber(5);

        Dice d6 = new Dice(Colour.WHITE);
        d6.setNumber(6);

        player.getWindowPatternCard().placeDice(d1,0,0);
        player.getWindowPatternCard().placeDice(d2,0,1);
        player.getWindowPatternCard().placeDice(d3,0,2);
        player.getWindowPatternCard().placeDice(d4,0,3);
        player.getWindowPatternCard().placeDice(d5,0,4);
        player.getWindowPatternCard().placeDice(d6,1,0);
        player.getWindowPatternCard().placeDice(d1,1,1);
        player.getWindowPatternCard().placeDice(d2,1,2);
        player.getWindowPatternCard().placeDice(d3,1,3);
        player.getWindowPatternCard().placeDice(d4,1,4);
        player.getWindowPatternCard().placeDice(d5,2,0);
        player.getWindowPatternCard().placeDice(d6,2,1);
        player.getWindowPatternCard().placeDice(d3,2,2);
        player.getWindowPatternCard().placeDice(d2,2,3);
        player.getWindowPatternCard().placeDice(d1,2,4);
        player.getWindowPatternCard().placeDice(d2,3,0);
        player.getWindowPatternCard().placeDice(d3,3,1);
        player.getWindowPatternCard().placeDice(d2,3,2);
        player.getWindowPatternCard().placeDice(d2,3,3);
        player.getWindowPatternCard().placeDice(d3,3,4);

        Rules test = new Rules();
        test.verify(direction,windowPatternCard);

        assertEquals(3,test.verify(rule,windowPatternCard));
    }

    @Test
    public void testDiagonal(){

        String rule = "COLOR";
        String direction = "DIAGONAL";
        WindowPatternCard windowPatternCard = new WindowPatternCard(50,10,"noName");
        Player player = new Player("vincenzo");
        player.setMyWindow(windowPatternCard);

        Dice d1 = new Dice(Colour.RED);
        d1.setNumber(1);


        Dice d2 = new Dice(Colour.YELLOW);
        d2.setNumber(2);

        Dice d3 = new Dice(Colour.GREEN);
        d3.setNumber(3);

        Dice d4 = new Dice(Colour.BLUE);
        d4.setNumber(4);

        Dice d5 = new Dice(Colour.PURPLE);
        d5.setNumber(5);

        Dice d6 = new Dice(Colour.WHITE);
        d6.setNumber(6);

        player.getWindowPatternCard().placeDice(d1,0,0);
        player.getWindowPatternCard().placeDice(d2,0,1);
        player.getWindowPatternCard().placeDice(d3,0,2);
        player.getWindowPatternCard().placeDice(d4,0,3);
        player.getWindowPatternCard().placeDice(d5,0,4);
        player.getWindowPatternCard().placeDice(d6,1,0);
        player.getWindowPatternCard().placeDice(d1,1,1);
        player.getWindowPatternCard().placeDice(d2,1,2);
        player.getWindowPatternCard().placeDice(d3,1,3);
        player.getWindowPatternCard().placeDice(d4,1,4);
        player.getWindowPatternCard().placeDice(d5,2,0);
        player.getWindowPatternCard().placeDice(d6,2,1);
        player.getWindowPatternCard().placeDice(d3,2,2);
        player.getWindowPatternCard().placeDice(d2,2,3);
        player.getWindowPatternCard().placeDice(d1,2,4);
        player.getWindowPatternCard().placeDice(d2,3,0);
        player.getWindowPatternCard().placeDice(d3,3,1);
        player.getWindowPatternCard().placeDice(d2,3,2);
        player.getWindowPatternCard().placeDice(d2,3,3);
        player.getWindowPatternCard().placeDice(d3,3,4);

        Rules test = new Rules();
        test.verify(direction,windowPatternCard);

        assertEquals(14,test.verify(rule,windowPatternCard));
    }

    @Test
    public void testAllWithEmptyCard(){

        WindowPatternCard windowPatternCard = new WindowPatternCard(50,10,"noName");
        Player player = new Player("vincenzo");
        player.setMyWindow(windowPatternCard);
        Rules test = new Rules();

        String rule = "1,2";

        assertEquals(0,test.verify(rule,windowPatternCard));

        rule = "1,2,3,4,5,6";

        assertEquals(0,test.verify(rule,windowPatternCard));

        rule = "Y,G,B,P,R";

        assertEquals(0,test.verify(rule,windowPatternCard));

        rule = "Y";

        assertEquals(0,test.verify(rule,windowPatternCard));

        rule = "NOCOLOR";
        String direction = "ROW";
        test.verify(direction,windowPatternCard);

        assertEquals(0,test.verify(rule,windowPatternCard));

        rule = "NOCOLOR";
        direction = "COLUMN";
        test.verify(direction,windowPatternCard);
        assertEquals(0,test.verify(rule,windowPatternCard));

        rule = "NOVALUE";
        direction = "ROW";
        test.verify(direction,windowPatternCard);
        assertEquals(0,test.verify(rule,windowPatternCard));

        rule = "NOVALUE";
        direction = "COLUMN";
        test.verify(direction,windowPatternCard);
        assertEquals(0,test.verify(rule,windowPatternCard));

        rule = "COLOR";
        direction = "DIAGONAL";
        test.verify(direction,windowPatternCard);
        assertEquals(0,test.verify(rule,windowPatternCard));

    }

}
