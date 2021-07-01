package it.polimi.ingsw.modeltest.gamedatatest.gametoolstest;

import it.polimi.ingsw.model.gamedata.Colour;
import it.polimi.ingsw.model.gamedata.gametools.Cell;
import it.polimi.ingsw.model.gamedata.gametools.Dice;
import it.polimi.ingsw.model.gamedata.gametools.WindowPatternCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.modeltest.gamelogictest.InspectorPlaceTest.getWindowPatternCard;
import static org.junit.jupiter.api.Assertions.*;

class WindowPatternCardTest {


     @Test
     public void testMatrixLength() {
         WindowPatternCard tester = new WindowPatternCard(0, 0, "No name");
         assertEquals(4, tester.getMatr().size());
         assertEquals(5, tester.getMatr().get(0).size());
     }

     @Test
     public void testOccupied() {
         WindowPatternCard tester = new WindowPatternCard(0, 0, "No name");
         int x = 0, y = 0;
         Dice d = new Dice();
         assertTrue(tester.placeDice(d, x, y));
         assertTrue(tester.getMatr().get(x).get(y).isOccupied());
     }

     @Test
     public void testFull() {
         Dice d = new Dice();
         WindowPatternCard tester = new WindowPatternCard(0, 0, "No name");
         for (int i = 0; i < 4; i++) {
             for (int j = 0; j < 5; j++) {
                 tester.placeDice(d, i, j);
             }
         }
         for (int i = 0; i < 4; i++) {
             for (int j = 0; j < 5; j++) {
                 assertFalse(tester.placeDice(d, i, j));
             }
         }
     }

     @Test
     public void testFirstCard() {
         WindowPatternCard tester;
         tester = this.pullOutCard(1);

         assertEquals("Kaleidoscopic Dream", tester.getName());

         List<List<Cell>> arrayLists = tester.getMatr();

         verify(arrayLists,Colour.YELLOW,0,0,0);
         verify(arrayLists,Colour.BLUE,0,0,1);
         verify(arrayLists,Colour.WHITE,0,0,2);
         verify(arrayLists,Colour.WHITE,0,0,3);
         verify(arrayLists,Colour.WHITE,1,0,4);

         verify(arrayLists,Colour.GREEN,0,1,0);
         verify(arrayLists,Colour.WHITE,0,1,1);
         verify(arrayLists,Colour.WHITE,5,1,2);
         verify(arrayLists,Colour.WHITE,0,1,3);
         verify(arrayLists,Colour.WHITE,4,1,4);

         verify(arrayLists,Colour.WHITE,3,2,0);
         verify(arrayLists,Colour.WHITE,0,2,1);
         verify(arrayLists,Colour.RED,0,2,2);
         verify(arrayLists,Colour.WHITE,0,2,3);
         verify(arrayLists,Colour.GREEN,0,2,4);

         verify(arrayLists,Colour.WHITE,2,3,0);
         verify(arrayLists,Colour.WHITE,0,3,1);
         verify(arrayLists,Colour.WHITE,0,3,2);
         verify(arrayLists,Colour.BLUE,0,3,3);
         verify(arrayLists,Colour.YELLOW,0,3,4);


     }

     @Test
     public void testSecondCard() {
         WindowPatternCard tester;
         tester = this.pullOutCard(2);

         assertEquals("Virtus", tester.getName());

         List<List<Cell>> arrayLists = tester.getMatr();

         verify(arrayLists,Colour.WHITE,4,0,0);
         verify(arrayLists,Colour.WHITE,0,0,1);
         verify(arrayLists,Colour.WHITE,2,0,2);
         verify(arrayLists,Colour.WHITE,5,0,3);
         verify(arrayLists,Colour.GREEN,0,0,4);

         verify(arrayLists,Colour.WHITE,0,1,0);
         verify(arrayLists,Colour.WHITE,0,1,1);
         verify(arrayLists,Colour.WHITE,6,1,2);
         verify(arrayLists,Colour.GREEN,0,1,3);
         verify(arrayLists,Colour.WHITE,2,1,4);

         verify(arrayLists,Colour.WHITE,0,2,0);
         verify(arrayLists,Colour.WHITE,3,2,1);
         verify(arrayLists,Colour.GREEN,0,2,2);
         verify(arrayLists,Colour.WHITE,4,2,3);
         verify(arrayLists,Colour.WHITE,0,2,4);

         verify(arrayLists,Colour.WHITE,5,3,0);
         verify(arrayLists,Colour.GREEN,0,3,1);
         verify(arrayLists,Colour.WHITE,1,3,2);
         verify(arrayLists,Colour.WHITE,0,3,3);
         verify(arrayLists,Colour.WHITE,0,3,4);

     }


     @Test
     public void testThirdCard() {
         WindowPatternCard tester;
         tester = this.pullOutCard(3);


         assertEquals("Aurorae Magnificus", tester.getName());

         List<List<Cell>> arrayLists = tester.getMatr();

         verify(arrayLists,Colour.WHITE,5,0,0);
         verify(arrayLists,Colour.GREEN,0,0,1);
         verify(arrayLists,Colour.BLUE,0,0,2);
         verify(arrayLists,Colour.PURPLE,0,0,3);
         verify(arrayLists,Colour.WHITE,2,0,4);

         verify(arrayLists,Colour.PURPLE,0,1,0);
         verify(arrayLists,Colour.WHITE,0,1,1);
         verify(arrayLists,Colour.WHITE,0,1,2);
         verify(arrayLists,Colour.WHITE,0,1,3);
         verify(arrayLists,Colour.YELLOW,0,1,4);

         verify(arrayLists,Colour.YELLOW,0,2,0);
         verify(arrayLists,Colour.WHITE,0,2,1);
         verify(arrayLists,Colour.WHITE,6,2,2);
         verify(arrayLists,Colour.WHITE,0,2,3);
         verify(arrayLists,Colour.PURPLE,0,2,4);

         verify(arrayLists,Colour.WHITE,1,3,0);
         verify(arrayLists,Colour.WHITE,0,3,1);
         verify(arrayLists,Colour.WHITE,0,3,2);
         verify(arrayLists,Colour.GREEN,0,3,3);
         verify(arrayLists,Colour.WHITE,4,3,4);

     }


     @Test
     public void testFourthCard() {
         WindowPatternCard tester;
         tester = this.pullOutCard(4);

         assertEquals("Via Lux", tester.getName());

         List<List<Cell>> arrayLists = tester.getMatr();

         verify(arrayLists,Colour.YELLOW,0,0,0);
         verify(arrayLists,Colour.WHITE,0,0,1);
         verify(arrayLists,Colour.WHITE,6,0,2);
         verify(arrayLists,Colour.WHITE,0,0,3);
         verify(arrayLists,Colour.WHITE,0,0,4);

         verify(arrayLists,Colour.WHITE,0,1,0);
         verify(arrayLists,Colour.WHITE,1,1,1);
         verify(arrayLists,Colour.WHITE,5,1,2);
         verify(arrayLists,Colour.WHITE,0,1,3);
         verify(arrayLists,Colour.WHITE,2,1,4);

         verify(arrayLists,Colour.WHITE,3,2,0);
         verify(arrayLists,Colour.YELLOW,0,2,1);
         verify(arrayLists,Colour.RED,0,2,2);
         verify(arrayLists,Colour.PURPLE,0,2,3);
         verify(arrayLists,Colour.WHITE,0,2,4);

         verify(arrayLists,Colour.WHITE,0,3,0);
         verify(arrayLists,Colour.WHITE,0,3,1);
         verify(arrayLists,Colour.WHITE,4,3,2);
         verify(arrayLists,Colour.WHITE,3,3,3);
         verify(arrayLists,Colour.RED,0,3,4);

     }

     @Test
     public void testFifthCard() {
         WindowPatternCard tester;
         tester = this.pullOutCard(5);

         assertEquals("Sun Catcher", tester.getName());

         List<List<Cell>> arrayLists = tester.getMatr();

         verify(arrayLists,Colour.WHITE,0,0,0);
         verify(arrayLists,Colour.BLUE,0,0,1);
         verify(arrayLists,Colour.WHITE,2,0,2);
         verify(arrayLists,Colour.WHITE,0,0,3);
         verify(arrayLists,Colour.YELLOW,0,0,4);

         verify(arrayLists,Colour.WHITE,0,1,0);
         verify(arrayLists,Colour.WHITE,4,1,1);
         verify(arrayLists,Colour.WHITE,0,1,2);
         verify(arrayLists,Colour.RED,0,1,3);
         verify(arrayLists,Colour.WHITE,0,1,4);

         verify(arrayLists,Colour.WHITE,0,2,0);
         verify(arrayLists,Colour.WHITE,0,2,1);
         verify(arrayLists,Colour.WHITE,5,2,2);
         verify(arrayLists,Colour.YELLOW,0,2,3);
         verify(arrayLists,Colour.WHITE,0,2,4);

         verify(arrayLists,Colour.GREEN,0,3,0);
         verify(arrayLists,Colour.WHITE,3,3,1);
         verify(arrayLists,Colour.WHITE,0,3,2);
         verify(arrayLists,Colour.WHITE,0,3,3);
         verify(arrayLists,Colour.PURPLE,0,3,4);

     }

     @Test
     public void testSixthCard() {
         WindowPatternCard tester;
         tester = this.pullOutCard(6);

         assertEquals("Bellesguard", tester.getName());

         List<List<Cell>> arrayLists = tester.getMatr();

         verify(arrayLists,Colour.BLUE,0,0,0);
         verify(arrayLists,Colour.WHITE,6,0,1);
         verify(arrayLists,Colour.WHITE,0,0,2);
         verify(arrayLists,Colour.WHITE,0,0,3);
         verify(arrayLists,Colour.YELLOW,0,0,4);

         verify(arrayLists,Colour.WHITE,0,1,0);
         verify(arrayLists,Colour.WHITE,3,1,1);
         verify(arrayLists,Colour.BLUE,0,1,2);
         verify(arrayLists,Colour.WHITE,0,1,3);
         verify(arrayLists,Colour.WHITE,0,1,4);

         verify(arrayLists,Colour.WHITE,0,2,0);
         verify(arrayLists,Colour.WHITE,5,2,1);
         verify(arrayLists,Colour.WHITE,6,2,2);
         verify(arrayLists,Colour.WHITE,2,2,3);
         verify(arrayLists,Colour.WHITE,0,2,4);

         verify(arrayLists,Colour.WHITE,0,3,0);
         verify(arrayLists,Colour.WHITE,4,3,1);
         verify(arrayLists,Colour.WHITE,0,3,2);
         verify(arrayLists,Colour.WHITE,1,3,3);
         verify(arrayLists,Colour.GREEN,0,3,4);

     }

     @Test
     public void testSeventhCard() {
         WindowPatternCard tester;
         tester = this.pullOutCard(7);

         assertEquals("Firmitas", tester.getName());

         List<List<Cell>> arrayLists = tester.getMatr();

         verify(arrayLists,Colour.PURPLE,0,0,0);
         verify(arrayLists,Colour.WHITE,6,0,1);
         verify(arrayLists,Colour.WHITE,0,0,2);
         verify(arrayLists,Colour.WHITE,0,0,3);
         verify(arrayLists,Colour.WHITE,3,0,4);

         verify(arrayLists,Colour.WHITE,5,1,0);
         verify(arrayLists,Colour.PURPLE,0,1,1);
         verify(arrayLists,Colour.WHITE,3,1,2);
         verify(arrayLists,Colour.WHITE,0,1,3);
         verify(arrayLists,Colour.WHITE,0,1,4);

         verify(arrayLists,Colour.WHITE,0,2,0);
         verify(arrayLists,Colour.WHITE,2,2,1);
         verify(arrayLists,Colour.PURPLE,0,2,2);
         verify(arrayLists,Colour.WHITE,1,2,3);
         verify(arrayLists,Colour.WHITE,0,2,4);

         verify(arrayLists,Colour.WHITE,0,3,0);
         verify(arrayLists,Colour.WHITE,1,3,1);
         verify(arrayLists,Colour.WHITE,5,3,2);
         verify(arrayLists,Colour.PURPLE,0,3,3);
         verify(arrayLists,Colour.WHITE,4,3,4);

     }

     @Test
     public void testEightCard() {
         WindowPatternCard tester;
         tester = this.pullOutCard(8);

         List<List<Cell>> arrayLists = tester.getMatr();

         verify(arrayLists,Colour.WHITE,2,0,0);
         verify(arrayLists,Colour.WHITE,0,0,1);
         verify(arrayLists,Colour.WHITE,5,0,2);
         verify(arrayLists,Colour.WHITE,0,0,3);
         verify(arrayLists,Colour.WHITE,1,0,4);

         verify(arrayLists,Colour.YELLOW,0,1,0);
         verify(arrayLists,Colour.WHITE,6,1,1);
         verify(arrayLists,Colour.PURPLE,0,1,2);
         verify(arrayLists,Colour.WHITE,2,1,3);
         verify(arrayLists,Colour.RED,0,1,4);

         verify(arrayLists,Colour.WHITE,0,2,0);
         verify(arrayLists,Colour.BLUE,0,2,1);
         verify(arrayLists,Colour.WHITE,4,2,2);
         verify(arrayLists,Colour.GREEN,0,2,3);
         verify(arrayLists,Colour.WHITE,0,2,4);

         verify(arrayLists,Colour.WHITE,0,3,0);
         verify(arrayLists,Colour.WHITE,3,3,1);
         verify(arrayLists,Colour.WHITE,0,3,2);
         verify(arrayLists,Colour.WHITE,5,3,3);
         verify(arrayLists,Colour.WHITE,0,3,4);

     }

     @Test
     public void testNinthCard() {
         WindowPatternCard tester;
         tester = this.pullOutCard(9);

         assertEquals("Aurora Sagradis", tester.getName());

         List<List<Cell>> arrayLists = tester.getMatr();

         verify(arrayLists,Colour.RED,0,0,0);
         verify(arrayLists,Colour.WHITE,0,0,1);
         verify(arrayLists,Colour.BLUE,0,0,2);
         verify(arrayLists,Colour.WHITE,0,0,3);
         verify(arrayLists,Colour.YELLOW,0,0,4);

         verify(arrayLists,Colour.WHITE,4,1,0);
         verify(arrayLists,Colour.PURPLE,0,1,1);
         verify(arrayLists,Colour.WHITE,3,1,2);
         verify(arrayLists,Colour.GREEN,0,1,3);
         verify(arrayLists,Colour.WHITE,2,1,4);

         verify(arrayLists,Colour.WHITE,0,2,0);
         verify(arrayLists,Colour.WHITE,1,2,1);
         verify(arrayLists,Colour.WHITE,0,2,2);
         verify(arrayLists,Colour.WHITE,5,2,3);
         verify(arrayLists,Colour.WHITE,0,2,4);

         verify(arrayLists,Colour.WHITE,0,3,0);
         verify(arrayLists,Colour.WHITE,0,3,1);
         verify(arrayLists,Colour.WHITE,6,3,2);
         verify(arrayLists,Colour.WHITE,0,3,3);
         verify(arrayLists,Colour.WHITE,0,3,4);

     }

     @Test
     public void testTenthCard() {
         WindowPatternCard tester;
         tester = this.pullOutCard(10);

         assertEquals("Industria", tester.getName());

         List<List<Cell>> arrayLists = tester.getMatr();

         verify(arrayLists,Colour.WHITE,1,0,0);
         verify(arrayLists,Colour.RED,0,0,1);
         verify(arrayLists,Colour.WHITE,3,0,2);
         verify(arrayLists,Colour.WHITE,0,0,3);
         verify(arrayLists,Colour.WHITE,6,0,4);

         verify(arrayLists,Colour.WHITE,5,1,0);
         verify(arrayLists,Colour.WHITE,4,1,1);
         verify(arrayLists,Colour.RED,0,1,2);
         verify(arrayLists,Colour.WHITE,2,1,3);
         verify(arrayLists,Colour.WHITE,0,1,4);

         verify(arrayLists,Colour.WHITE,0,2,0);
         verify(arrayLists,Colour.WHITE,0,2,1);
         verify(arrayLists,Colour.WHITE,5,2,2);
         verify(arrayLists,Colour.RED,0,2,3);
         verify(arrayLists,Colour.WHITE,1,2,4);

         verify(arrayLists,Colour.WHITE,0,3,0);
         verify(arrayLists,Colour.WHITE,0,3,1);
         verify(arrayLists,Colour.WHITE,0,3,2);
         verify(arrayLists,Colour.WHITE,3,3,3);
         verify(arrayLists,Colour.RED,0,3,4);

     }

     @Test
     public void testEleventhCard() {
        WindowPatternCard tester;
         tester = this.pullOutCard(11);

         assertEquals("Shadow Thief", tester.getName());

         List<List<Cell>> arrayLists = tester.getMatr();

         verify(arrayLists,Colour.WHITE,6,0,0);
         verify(arrayLists,Colour.PURPLE,0,0,1);
         verify(arrayLists,Colour.WHITE,0,0,2);
         verify(arrayLists,Colour.WHITE,0,0,3);
         verify(arrayLists,Colour.WHITE,5,0,4);

         verify(arrayLists,Colour.WHITE,5,1,0);
         verify(arrayLists,Colour.WHITE,0,1,1);
         verify(arrayLists,Colour.PURPLE,0,1,2);
         verify(arrayLists,Colour.WHITE,0,1,3);
         verify(arrayLists,Colour.WHITE,0,1,4);

         verify(arrayLists,Colour.RED,0,2,0);
         verify(arrayLists,Colour.WHITE,6,2,1);
         verify(arrayLists,Colour.WHITE,0,2,2);
         verify(arrayLists,Colour.PURPLE,0,2,3);
         verify(arrayLists,Colour.WHITE,0,2,4);

         verify(arrayLists,Colour.YELLOW,0,3,0);
         verify(arrayLists,Colour.RED,0,3,1);
         verify(arrayLists,Colour.WHITE,5,3,2);
         verify(arrayLists,Colour.WHITE,4,3,3);
         verify(arrayLists,Colour.WHITE,3,3,4);

     }

     @Test
     public void testTwelthCard() {
         WindowPatternCard tester;
         tester = this.pullOutCard(12);

         assertEquals("Batllo", tester.getName());

         List<List<Cell>> arrayLists = tester.getMatr();

         verify(arrayLists,Colour.WHITE,0,0,0);
         verify(arrayLists,Colour.WHITE,0,0,1);
         verify(arrayLists,Colour.WHITE,6,0,2);
         verify(arrayLists,Colour.WHITE,0,0,3);
         verify(arrayLists,Colour.WHITE,0,0,4);

         verify(arrayLists,Colour.WHITE,0,1,0);
         verify(arrayLists,Colour.WHITE,5,1,1);
         verify(arrayLists,Colour.BLUE,0,1,2);
         verify(arrayLists,Colour.WHITE,4,1,3);
         verify(arrayLists,Colour.WHITE,0,1,4);

         verify(arrayLists,Colour.WHITE,3,2,0);
         verify(arrayLists,Colour.GREEN,0,2,1);
         verify(arrayLists,Colour.YELLOW,0,2,2);
         verify(arrayLists,Colour.PURPLE,0,2,3);
         verify(arrayLists,Colour.WHITE,2,2,4);

         verify(arrayLists,Colour.WHITE,1,3,0);
         verify(arrayLists,Colour.WHITE,4,3,1);
         verify(arrayLists,Colour.RED,0,3,2);
         verify(arrayLists,Colour.WHITE,5,3,3);
         verify(arrayLists,Colour.WHITE,3,3,4);

     }

     @Test
     public void testThirteenthCard() {
         WindowPatternCard tester;
         tester = this.pullOutCard(13);

         assertEquals("Gravitas", tester.getName());

         List<List<Cell>> arrayLists = tester.getMatr();

         verify(arrayLists,Colour.WHITE,1,0,0);
         verify(arrayLists,Colour.WHITE,0,0,1);
         verify(arrayLists,Colour.WHITE,3,0,2);
         verify(arrayLists,Colour.BLUE,0,0,3);
         verify(arrayLists,Colour.WHITE,0,0,4);

         verify(arrayLists,Colour.WHITE,0,1,0);
         verify(arrayLists,Colour.WHITE,2,1,1);
         verify(arrayLists,Colour.BLUE,0,1,2);
         verify(arrayLists,Colour.WHITE,0,1,3);
         verify(arrayLists,Colour.WHITE,0,1,4);

         verify(arrayLists,Colour.WHITE,6,2,0);
         verify(arrayLists,Colour.BLUE,0,2,1);
         verify(arrayLists,Colour.WHITE,0,2,2);
         verify(arrayLists,Colour.WHITE,4,2,3);
         verify(arrayLists,Colour.WHITE,0,2,4);

         verify(arrayLists,Colour.BLUE,0,3,0);
         verify(arrayLists,Colour.WHITE,5,3,1);
         verify(arrayLists,Colour.WHITE,2,3,2);
         verify(arrayLists,Colour.WHITE,0,3,3);
         verify(arrayLists,Colour.WHITE,1,3,4);

     }

     @Test
     public void testFourteenthCard() {
         WindowPatternCard tester;
         tester = this.pullOutCard(14);

         assertEquals("Fractal Drops", tester.getName());

         List<List<Cell>> arrayLists = tester.getMatr();

         verify(arrayLists,Colour.WHITE,0,0,0);
         verify(arrayLists,Colour.WHITE,4,0,1);
         verify(arrayLists,Colour.WHITE,0,0,2);
         verify(arrayLists,Colour.YELLOW,0,0,3);
         verify(arrayLists,Colour.WHITE,6,0,4);

         verify(arrayLists,Colour.RED,0,1,0);
         verify(arrayLists,Colour.WHITE,0,1,1);
         verify(arrayLists,Colour.WHITE,2,1,2);
         verify(arrayLists,Colour.WHITE,0,1,3);
         verify(arrayLists,Colour.WHITE,0,1,4);

         verify(arrayLists,Colour.WHITE,0,2,0);
         verify(arrayLists,Colour.WHITE,0,2,1);
         verify(arrayLists,Colour.RED,0,2,2);
         verify(arrayLists,Colour.PURPLE,0,2,3);
         verify(arrayLists,Colour.WHITE,1,2,4);

         verify(arrayLists,Colour.BLUE,0,3,0);
         verify(arrayLists,Colour.YELLOW,0,3,1);
         verify(arrayLists,Colour.WHITE,0,3,2);
         verify(arrayLists,Colour.WHITE,0,3,3);
         verify(arrayLists,Colour.WHITE,0,3,4);


     }

     @Test
     public void testFifthteenCard() {
         WindowPatternCard tester;
         tester = this.pullOutCard(15);

         assertEquals("Lux Astram", tester.getName());

         List<List<Cell>> arrayLists = tester.getMatr();

         verify(arrayLists,Colour.WHITE,0,0,0);
         verify(arrayLists,Colour.WHITE,1,0,1);
         verify(arrayLists,Colour.GREEN,0,0,2);
         verify(arrayLists,Colour.PURPLE,0,0,3);
         verify(arrayLists,Colour.WHITE,4,0,4);

         verify(arrayLists,Colour.WHITE,6,1,0);
         verify(arrayLists,Colour.PURPLE,0,1,1);
         verify(arrayLists,Colour.WHITE,2,1,2);
         verify(arrayLists,Colour.WHITE,5,1,3);
         verify(arrayLists,Colour.GREEN,0,1,4);

         verify(arrayLists,Colour.WHITE,1,2,0);
         verify(arrayLists,Colour.GREEN,0,2,1);
         verify(arrayLists,Colour.WHITE,5,2,2);
         verify(arrayLists,Colour.WHITE,3,2,3);
         verify(arrayLists,Colour.PURPLE,0,2,4);

         verify(arrayLists,Colour.WHITE,0,3,0);
         verify(arrayLists,Colour.WHITE,0,3,1);
         verify(arrayLists,Colour.WHITE,0,3,2);
         verify(arrayLists,Colour.WHITE,0,3,3);
         verify(arrayLists,Colour.WHITE,0,3,4);


     }

     @Test
     public void testSixteenthCard() {
         WindowPatternCard tester;
         tester = this.pullOutCard(16);

         assertEquals("Chromatic Splendor", tester.getName());

         List<List<Cell>> arrayLists = tester.getMatr();

         verify(arrayLists,Colour.WHITE,0,0,0);
         verify(arrayLists,Colour.WHITE,0,0,1);
         verify(arrayLists,Colour.GREEN,0,0,2);
         verify(arrayLists,Colour.WHITE,0,0,3);
         verify(arrayLists,Colour.WHITE,0,0,4);

         verify(arrayLists,Colour.WHITE,2,1,0);
         verify(arrayLists,Colour.YELLOW,0,1,1);
         verify(arrayLists,Colour.WHITE,5,1,2);
         verify(arrayLists,Colour.BLUE,0,1,3);
         verify(arrayLists,Colour.WHITE,1,1,4);

         verify(arrayLists,Colour.WHITE,0,2,0);
         verify(arrayLists,Colour.RED,0,2,1);
         verify(arrayLists,Colour.WHITE,3,2,2);
         verify(arrayLists,Colour.PURPLE,0,2,3);
         verify(arrayLists,Colour.WHITE,0,2,4);

         verify(arrayLists,Colour.WHITE,1,3,0);
         verify(arrayLists,Colour.WHITE,0,3,1);
         verify(arrayLists,Colour.WHITE,6,3,2);
         verify(arrayLists,Colour.WHITE,0,3,3);
         verify(arrayLists,Colour.WHITE,4,3,4);


     }

     @Test
     public void testSeventeenthCard() {
         WindowPatternCard tester;
         tester = this.pullOutCard(17);

         assertEquals("Firelight", tester.getName());

         List<List<Cell>> arrayLists = tester.getMatr();

         verify(arrayLists,Colour.WHITE,3,0,0);
         verify(arrayLists,Colour.WHITE,4,0,1);
         verify(arrayLists,Colour.WHITE,1,0,2);
         verify(arrayLists,Colour.WHITE,5,0,3);
         verify(arrayLists,Colour.WHITE,0,0,4);

         verify(arrayLists,Colour.WHITE,0,1,0);
         verify(arrayLists,Colour.WHITE,6,1,1);
         verify(arrayLists,Colour.WHITE,2,1,2);
         verify(arrayLists,Colour.WHITE,0,1,3);
         verify(arrayLists,Colour.YELLOW,0,1,4);

         verify(arrayLists,Colour.WHITE,0,2,0);
         verify(arrayLists,Colour.WHITE,0,2,1);
         verify(arrayLists,Colour.WHITE,0,2,2);
         verify(arrayLists,Colour.YELLOW,0,2,3);
         verify(arrayLists,Colour.RED,0,2,4);

         verify(arrayLists,Colour.WHITE,5,3,0);
         verify(arrayLists,Colour.WHITE,0,3,1);
         verify(arrayLists,Colour.YELLOW,0,3,2);
         verify(arrayLists,Colour.RED,0,3,3);
         verify(arrayLists,Colour.WHITE,6,3,4);


     }

     @Test
     public void testEighteenthCard() {
         WindowPatternCard tester;
         tester = this.pullOutCard(18);

         assertEquals("Luz Celestial", tester.getName());

         List<List<Cell>> arrayLists = tester.getMatr();

         verify(arrayLists,Colour.WHITE,0,0,0);
         verify(arrayLists,Colour.WHITE,0,0,1);
         verify(arrayLists,Colour.RED,0,0,2);
         verify(arrayLists,Colour.WHITE,5,0,3);
         verify(arrayLists,Colour.WHITE,0,0,4);

         verify(arrayLists,Colour.PURPLE,0,1,0);
         verify(arrayLists,Colour.WHITE,4,1,1);
         verify(arrayLists,Colour.WHITE,0,1,2);
         verify(arrayLists,Colour.GREEN,0,1,3);
         verify(arrayLists,Colour.WHITE,3,1,4);

         verify(arrayLists,Colour.WHITE,6,2,0);
         verify(arrayLists,Colour.WHITE,0,2,1);
         verify(arrayLists,Colour.WHITE,0,2,2);
         verify(arrayLists,Colour.BLUE,0,2,3);
         verify(arrayLists,Colour.WHITE,0,2,4);

         verify(arrayLists,Colour.WHITE,0,3,0);
         verify(arrayLists,Colour.YELLOW,0,3,1);
         verify(arrayLists,Colour.WHITE,2,3,2);
         verify(arrayLists,Colour.WHITE,0,3,3);
         verify(arrayLists,Colour.WHITE,0,3,4);


     }

     @Test
     public void testNineteenthCard() {
         WindowPatternCard tester;
         tester = this.pullOutCard(19);

         assertEquals("Water of Life", tester.getName());

         List<List<Cell>> arrayLists = tester.getMatr();

         verify(arrayLists,Colour.WHITE,6,0,0);
         verify(arrayLists,Colour.BLUE,0,0,1);
         verify(arrayLists,Colour.WHITE,0,0,2);
         verify(arrayLists,Colour.WHITE,0,0,3);
         verify(arrayLists,Colour.WHITE,1,0,4);

         verify(arrayLists,Colour.WHITE,0,1,0);
         verify(arrayLists,Colour.WHITE,5,1,1);
         verify(arrayLists,Colour.BLUE,0,1,2);
         verify(arrayLists,Colour.WHITE,0,1,3);
         verify(arrayLists,Colour.WHITE,0,1,4);

         verify(arrayLists,Colour.WHITE,4,2,0);
         verify(arrayLists,Colour.RED,0,2,1);
         verify(arrayLists,Colour.WHITE,2,2,2);
         verify(arrayLists,Colour.BLUE,0,2,3);
         verify(arrayLists,Colour.WHITE,0,2,4);

         verify(arrayLists,Colour.GREEN,0,3,0);
         verify(arrayLists,Colour.WHITE,6,3,1);
         verify(arrayLists,Colour.YELLOW,0,3,2);
         verify(arrayLists,Colour.WHITE,3,3,3);
         verify(arrayLists,Colour.PURPLE,0,3,4);


     }

     @Test
     public void testTwenteenthCard() {
         WindowPatternCard tester;
         tester = this.pullOutCard(20);

         assertEquals("Ripples of Light", tester.getName());

         List<List<Cell>> arrayLists = tester.getMatr();

         verify(arrayLists,Colour.WHITE,0,0,0);
         verify(arrayLists,Colour.WHITE,0,0,1);
         verify(arrayLists,Colour.WHITE,0,0,2);
         verify(arrayLists,Colour.RED,0,0,3);
         verify(arrayLists,Colour.WHITE,5,0,4);

         verify(arrayLists,Colour.WHITE,0,1,0);
         verify(arrayLists,Colour.WHITE,0,1,1);
         verify(arrayLists,Colour.PURPLE,0,1,2);
         verify(arrayLists,Colour.WHITE,4,1,3);
         verify(arrayLists,Colour.BLUE,0,1,4);

         verify(arrayLists,Colour.WHITE,0,2,0);
         verify(arrayLists,Colour.BLUE,0,2,1);
         verify(arrayLists,Colour.WHITE,3,2,2);
         verify(arrayLists,Colour.YELLOW,0,2,3);
         verify(arrayLists,Colour.WHITE,6,2,4);

         verify(arrayLists,Colour.YELLOW,0,3,0);
         verify(arrayLists,Colour.WHITE,2,3,1);
         verify(arrayLists,Colour.GREEN,0,3,2);
         verify(arrayLists,Colour.WHITE,1,3,3);
         verify(arrayLists,Colour.RED,0,3,4);


     }

     @Test
     public void testTwentyfirstCard() {
         WindowPatternCard tester;
         tester = this.pullOutCard(21);

         assertEquals("Lux Mundi", tester.getName());

         List<List<Cell>> arrayLists = tester.getMatr();

         verify(arrayLists,Colour.WHITE,0,0,0);
         verify(arrayLists,Colour.WHITE,0,0,1);
         verify(arrayLists,Colour.WHITE,1,0,2);
         verify(arrayLists,Colour.WHITE,0,0,3);
         verify(arrayLists,Colour.WHITE,0,0,4);

         verify(arrayLists,Colour.WHITE,1,1,0);
         verify(arrayLists,Colour.GREEN,0,1,1);
         verify(arrayLists,Colour.WHITE,3,1,2);
         verify(arrayLists,Colour.BLUE,0,1,3);
         verify(arrayLists,Colour.WHITE,2,1,4);

         verify(arrayLists,Colour.BLUE,0,2,0);
         verify(arrayLists,Colour.WHITE,5,2,1);
         verify(arrayLists,Colour.WHITE,4,2,2);
         verify(arrayLists,Colour.WHITE,6,2,3);
         verify(arrayLists,Colour.GREEN,0,2,4);

         verify(arrayLists,Colour.WHITE,0,3,0);
         verify(arrayLists,Colour.BLUE,0,3,1);
         verify(arrayLists,Colour.WHITE,5,3,2);
         verify(arrayLists,Colour.GREEN,0,3,3);
         verify(arrayLists,Colour.WHITE,0,3,4);


     }

     @Test
     public void testTwentysecondCard() {
         WindowPatternCard tester;
         tester = this.pullOutCard(22);

         assertEquals("Comitas", tester.getName());

         List<List<Cell>> arrayLists = tester.getMatr();

         verify(arrayLists,Colour.YELLOW,0,0,0);
         verify(arrayLists,Colour.WHITE,0,0,1);
         verify(arrayLists,Colour.WHITE,2,0,2);
         verify(arrayLists,Colour.WHITE,0,0,3);
         verify(arrayLists,Colour.WHITE,6,0,4);

         verify(arrayLists,Colour.WHITE,0,1,0);
         verify(arrayLists,Colour.WHITE,4,1,1);
         verify(arrayLists,Colour.WHITE,0,1,2);
         verify(arrayLists,Colour.WHITE,5,1,3);
         verify(arrayLists,Colour.YELLOW,0,1,4);

         verify(arrayLists,Colour.WHITE,0,2,0);
         verify(arrayLists,Colour.WHITE,0,2,1);
         verify(arrayLists,Colour.WHITE,0,2,2);
         verify(arrayLists,Colour.YELLOW,0,2,3);
         verify(arrayLists,Colour.WHITE,5,2,4);

         verify(arrayLists,Colour.WHITE,1,3,0);
         verify(arrayLists,Colour.WHITE,2,3,1);
         verify(arrayLists,Colour.YELLOW,0,3,2);
         verify(arrayLists,Colour.WHITE,3,3,3);
         verify(arrayLists,Colour.WHITE,0,3,4);


     }

     @Test
     public void testTwentythirdCard() {
         WindowPatternCard tester;
         tester = this.pullOutCard(23);

         assertEquals("Sun's Glory", tester.getName());

         List<List<Cell>> arrayLists = tester.getMatr();

         verify(arrayLists,Colour.WHITE,1,0,0);
         verify(arrayLists,Colour.PURPLE,0,0,1);
         verify(arrayLists,Colour.YELLOW,0,0,2);
         verify(arrayLists,Colour.WHITE,0,0,3);
         verify(arrayLists,Colour.WHITE,4,0,4);

         verify(arrayLists,Colour.PURPLE,0,1,0);
         verify(arrayLists,Colour.YELLOW,0,1,1);
         verify(arrayLists,Colour.WHITE,0,1,2);
         verify(arrayLists,Colour.WHITE,0,1,3);
         verify(arrayLists,Colour.WHITE,6,1,4);

         verify(arrayLists,Colour.YELLOW,0,2,0);
         verify(arrayLists,Colour.WHITE,0,2,1);
         verify(arrayLists,Colour.WHITE,0,2,2);
         verify(arrayLists,Colour.WHITE,5,2,3);
         verify(arrayLists,Colour.WHITE,3,2,4);

         verify(arrayLists,Colour.WHITE,0,3,0);
         verify(arrayLists,Colour.WHITE,5,3,1);
         verify(arrayLists,Colour.WHITE,4,3,2);
         verify(arrayLists,Colour.WHITE,2,3,3);
         verify(arrayLists,Colour.WHITE,1,3,4);


     }

     @Test
     public void testTwentyfourthCard() {
         WindowPatternCard tester;
         tester = this.pullOutCard(24);

         assertEquals("Fulgor del Cielo", tester.getName());

         List<List<Cell>> arrayLists = tester.getMatr();

         verify(arrayLists,Colour.WHITE,0,0,0);
         verify(arrayLists,Colour.BLUE,0,0,1);
         verify(arrayLists,Colour.RED,0,0,2);
         verify(arrayLists,Colour.WHITE,0,0,3);
         verify(arrayLists,Colour.WHITE,0,0,4);

         verify(arrayLists,Colour.WHITE,0,1,0);
         verify(arrayLists,Colour.WHITE,4,1,1);
         verify(arrayLists,Colour.WHITE,5,1,2);
         verify(arrayLists,Colour.WHITE,0,1,3);
         verify(arrayLists,Colour.BLUE,0,1,4);

         verify(arrayLists,Colour.BLUE,0,2,0);
         verify(arrayLists,Colour.WHITE,2,2,1);
         verify(arrayLists,Colour.WHITE,0,2,2);
         verify(arrayLists,Colour.RED,0,2,3);
         verify(arrayLists,Colour.WHITE,5,2,4);

         verify(arrayLists,Colour.WHITE,6,3,0);
         verify(arrayLists,Colour.RED,0,3,1);
         verify(arrayLists,Colour.WHITE,3,3,2);
         verify(arrayLists,Colour.WHITE,1,3,3);
         verify(arrayLists,Colour.WHITE,0,3,4);


     }

     private void verify(List<List<Cell>> matr, Colour colour, int number, int x, int y){
         assertEquals(colour, matr.get(x).get(y).getProperty().getColour());
         assertEquals(number, matr.get(x).get(y).getProperty().getNumber());
     }

     private WindowPatternCard pullOutCard(int value){
         return getWindowPatternCard(value);
     }
 }

