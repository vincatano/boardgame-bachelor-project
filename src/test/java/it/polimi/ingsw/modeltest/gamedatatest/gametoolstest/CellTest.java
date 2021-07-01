package it.polimi.ingsw.modeltest.gamedatatest.gametoolstest;

import it.polimi.ingsw.model.gamedata.Pos;
import it.polimi.ingsw.model.gamedata.Property;
import it.polimi.ingsw.model.gamedata.gametools.Cell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CellTest {

    @Test
    public void testGetDice(){
        Cell tester = new Cell();
        assertNotNull(tester.getDice());
    }

    @Test
    public void testGetPosition(){
        Cell tester = new Cell();
        assertNotNull(tester.getPosition());
    }

    @Test
    public void testGetProperty(){
        Cell tester = new Cell();
        Property p = new Property();
        Pos pos = new Pos();
        assertTrue(tester.getProperty() != null);
    }

    @Test
    public void testisOccupied(){
        Cell tester = new Cell();
        assertFalse(tester.isOccupied());
    }

}
