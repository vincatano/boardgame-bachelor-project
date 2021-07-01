package it.polimi.ingsw.modeltest.gamedatatest;

import it.polimi.ingsw.model.gamedata.Player;
import it.polimi.ingsw.model.gamedata.Table;
import it.polimi.ingsw.model.gamedata.gametools.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TableTest {

    @Test
    public void testConstructorAndPublicObjects(){
        Player p1 = new Player("Gionny");
        Player p2 = new Player("Luca");
        Player p3 = new Player("Andrea");
        Player p4 = new Player("Giuseppe");

        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        Table tester = new Table(players);

        tester.initialize();

        tester.setPublicObjects();

        assertTrue(tester.getDraftPool().getDraftPool().isEmpty());

        assertFalse(tester.getObjCard().isEmpty());

        assertFalse(tester.getToolCards().isEmpty());

        assertEquals(90,tester.getDiceBag().remainingDices());

        assertTrue(tester.getRoundTrack().getRoundTrack().isEmpty());

        for (Player p: players){
            assertEquals(tester.getDraftPool(),p.getDraftPool());
            assertEquals(tester.getRoundTrack(),p.getRoundTrack());
            assertEquals(tester.getToolCards(),p.getToolCards());

        }
    }

    @Test
    public void testinGetters(){
        Player p1 = new Player("Gionny");
        Player p2 = new Player("Luca");
        Player p3 = new Player("Andrea");
        Player p4 = new Player("Giuseppe");

        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        Table tester = new Table(players);
        tester.initialize();


        assertNotNull(tester.getToolCards());
        assertNotNull(tester.getObjCard());
        assertNotNull(tester.getRoundTrack().getRoundTrack());
        assertNotNull(tester.getDiceBag());
        assertNotNull(tester.getDraftPool().getDraftPool());

    }

    @Test
    public void testDraftPool(){
        Player p1 = new Player("Gionny");
        Player p2 = new Player("Luca");
        Player p3 = new Player("Andrea");
        Player p4 = new Player("Giuseppe");

        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        Table tester = new Table(players);
        tester.initialize();
        tester.setPublicObjects();


        tester.fillDraftPool();

        for(int i = 0; i < tester.getDraftPool().getNumOfDices();i++){
            assertTrue(tester.getDraftPool().getDraftPool().get(i).areEquals(p1.getDraftPool().getDraftPool().get(i)));
            assertTrue(tester.getDraftPool().getDraftPool().get(i).areEquals(p2.getDraftPool().getDraftPool().get(i)));
            assertTrue(tester.getDraftPool().getDraftPool().get(i).areEquals(p3.getDraftPool().getDraftPool().get(i)));
            assertTrue(tester.getDraftPool().getDraftPool().get(i).areEquals(p4.getDraftPool().getDraftPool().get(i)));
        }
    }

    @Test
    public void testRoundTrack(){

        Player p1 = new Player("Gionny");
        Player p2 = new Player("Luca");
        Player p3 = new Player("Andrea");
        Player p4 = new Player("Giuseppe");

        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        Table tester = new Table(players);

        tester.initialize();

        tester.setPublicObjects();

        tester.fillDraftPool();

        tester.setLastDices(1);

        tester.fillDraftPool();

        tester.setLastDices(2);

        for(int i = 0; i < tester.getRoundTrack().getRoundTrack().size();i++){
            if(tester.getRoundTrack().getRoundTrack().get(i)!=null){
                for(int j = 0; j < tester.getRoundTrack().getRoundTrack().get(i).size(); j++){
                    assertTrue(tester.getRoundTrack().getRoundTrack().get(i).get(j).areEquals(p1.getRoundTrack().getRoundTrack().get(i).get(j)));
                }
            }
        }

    }

    @Test
    public void testSettingWindowandResetSelection(){


        Player p1 = new Player("Gionny");
        Player p2 = new Player("Luca");
        Player p3 = new Player("Andrea");
        Player p4 = new Player("Giuseppe");

        List<Player> players = new ArrayList<>();
        WindowPatternCard windowPatternCard1 = new WindowPatternCard(50,10,"noName");
        WindowPatternCard windowPatternCard2 = new WindowPatternCard(50,10,"noName");
        WindowPatternCard windowPatternCard3 = new WindowPatternCard(50,10,"noName");
        WindowPatternCard windowPatternCard4 = new WindowPatternCard(50,10,"noName");
        players.add(p1);
        p1.setMyWindow(windowPatternCard1);
        players.add(p2);
        p2.setMyWindow(windowPatternCard2);
        players.add(p3);
        p3.setMyWindow(windowPatternCard3);
        players.add(p4);
        p4.setMyWindow(windowPatternCard4);

        Table tester = new Table(players);

        tester.initialize();

        tester.setPublicObjects();

        tester.fillDraftPool();

        tester.getRoundTrack().setDiceOnRoundTrack(1,tester.getDraftPool().getDraftPool());

        tester.fillDraftPool();

        Dice d1 = new Dice();
        Dice d2 = new Dice();
        Dice d3 = new Dice();
        Dice d4 = new Dice();
        p1.getWindowPatternCard().placeDice(d1,0,0);
        p2.getWindowPatternCard().placeDice(d2,0,0);
        p3.getWindowPatternCard().placeDice(d3,0,0);
        p4.getWindowPatternCard().placeDice(d4,0,0);

        tester.resetSelection();

        for (Dice d: tester.getRoundTrack().getDiceOnRoundtrack(1)) {
            assertFalse(d.isSelected());
        }
        for (List<Cell> cells: p1.getWindowPatternCard().getMatr()) {
            for(Cell c: cells){
                if(c.isOccupied())
                    assertFalse(c.getDice().isSelected());
            }
        }
        for(Dice d: tester.getAllDraft()){
            assertFalse(d.isSelected());
        }
    }
}
