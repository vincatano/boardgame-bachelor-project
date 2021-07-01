package it.polimi.ingsw.modeltest.gamelogictest;

import it.polimi.ingsw.model.gamedata.Player;
import it.polimi.ingsw.model.gamelogic.PlayersContainer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayerContainerTest {

    @Test
    public void testIterator1() {
        List<Player> p = new ArrayList<Player>();
        p.add(new Player("1"));
        p.add(new Player("2"));
        p.add(new Player("3"));
        p.add(new Player("4"));
        PlayersContainer tester = new PlayersContainer(p);
        Iterator<Player> i = tester.getIterator();
        Player pl = i.next();
        assertEquals("1", pl.getUsername());
        pl = i.next();
        assertEquals("2", pl.getUsername());
        pl = i.next();
        assertEquals("3", pl.getUsername());
        pl = i.next();
        assertEquals("4", pl.getUsername());
        pl = i.next();
        assertEquals("4", pl.getUsername());
        pl = i.next();
        assertEquals("3", pl.getUsername());
        pl = i.next();
        assertEquals("2", pl.getUsername());
        pl = i.next();
        assertEquals("1", pl.getUsername());
        assertFalse(i.hasNext());

    }

    @Test
    public void testIterator2() {
        List<Player> p = new ArrayList<Player>();
        p.add(new Player("1"));
        PlayersContainer tester = new PlayersContainer(p);
        Iterator<Player> i = tester.getIterator();
        assertTrue(i.hasNext());
        i.next();
        assertTrue(i.hasNext());
        i.next();
        assertFalse(i.hasNext());
    }
    @Test
    public void testActivity1() {
        List<Player> p = new ArrayList<Player>();
        p.add(new Player("1"));
        p.add(new Player("2"));
        p.add(new Player("3"));
        p.add(new Player("4"));
        PlayersContainer tester = new PlayersContainer(p);
        assertTrue(tester.checkActivity());
        p.get(0).setActivity(false);
        assertTrue(tester.checkActivity());
        p.get(1).setActivity(false);
        assertTrue(tester.checkActivity());
        p.get(2).setActivity(false);
        assertFalse(tester.checkActivity());
    }
}


