package it.polimi.ingsw.modeltest.gamedatatest;

import it.polimi.ingsw.model.gamedata.Colour;
import it.polimi.ingsw.model.gamedata.Property;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PropertyTest {

    @Test
    public void testRollDice(){
        Property tester = new Property(Colour.YELLOW, true);
        assertTrue(tester.getNumber()>0 && tester.getNumber()<7);

        for(int i = 0; i < 50; i++){
            tester.rollDice();
            assertTrue(tester.getNumber()>0 && tester.getNumber()<7);
        }
    }
}
