package it.polimi.ingsw.viewtest;

import it.polimi.ingsw.model.gamedata.Colour;
import it.polimi.ingsw.model.gamedata.Player;
import it.polimi.ingsw.model.gamedata.Pos;
import it.polimi.ingsw.model.gamedata.Table;
import it.polimi.ingsw.model.gamedata.gametools.CardContainer;
import it.polimi.ingsw.model.gamedata.gametools.Dice;
import it.polimi.ingsw.model.gamedata.gametools.WindowPatternCard;
import it.polimi.ingsw.view.virtualview.VirtualViewParser;
import it.polimi.ingsw.view.cli.Printer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;
import static org.junit.jupiter.api.Assertions.*;

public class VirtualViewParserTest {

    @Test
    public void testParsingPlayers(){

        Player p1 = new Player("Luca");
        Player p2 = new Player("Giovanni");
        Player p3 = new Player("Andrea");
        Player p4 = new Player("Vincenzo");


        CardContainer container = new CardContainer();
        List<WindowPatternCard> windowPatternCards = container.pullOutPattern(4);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        p1.setMyWindow(windowPatternCards.get(0));
        p2.setMyWindow(windowPatternCards.get(1));
        p3.setMyWindow(windowPatternCards.get(2));
        p4.setMyWindow(windowPatternCards.get(3));


        Table table = new Table(players);
        table.initialize();
        table.setPublicObjects();


        VirtualViewParser tester = new VirtualViewParser(p1);

        String parsed = tester.startParsing();

        parsed = parsed.substring(parsed.indexOf("gamePlayers ")+12,parsed.length()-1);
        List<String> pv = Arrays.asList(parsed.split(";"));

        List<String> splitted = Arrays.asList(pv.get(0).split(","));

        assertEquals(4,splitted.size());
        assertTrue(!splitted.get(0).equals(splitted.get(1)) && !splitted.get(0).equals(splitted.get(2)) &&
                !splitted.get(0).equals(splitted.get(3)));
        assertTrue(!splitted.get(1).equals(splitted.get(2)) && !splitted.get(1).equals(splitted.get(3)));
        assertTrue(!splitted.get(2).equals(splitted.get(3)));

        for (String name: splitted) {
            assertTrue(name.equals(p1.getUsername()) || name.equals(p1.getPublicObjects().getPlayers().get(0).getUsername())
                    || name.equals(p1.getPublicObjects().getPlayers().get(1).getUsername())
                    || name.equals(p1.getPublicObjects().getPlayers().get(2).getUsername()));
        }

    }

    @Test
    public void testParsingRestrictions(){

        Player p1 = new Player("Luca");

        Player p2 = new Player("Giovanni");

        Player p3 = new Player("Andrea");

        Player p4 = new Player("Vincenzo");

        CardContainer container = new CardContainer();
        List<WindowPatternCard> windowPatternCards = container.pullOutPattern(4);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        p1.setMyWindow(windowPatternCards.get(0));
        p2.setMyWindow(windowPatternCards.get(1));
        p3.setMyWindow(windowPatternCards.get(2));
        p4.setMyWindow(windowPatternCards.get(3));

        Table table = new Table(players);

        table.initialize();

        table.setPublicObjects();

        VirtualViewParser tester = new VirtualViewParser(p1);

        String parsed = tester.startParsing();

        parsed = parsed.substring(parsed.indexOf("restrictions Luca,")+18,parsed.length());

        int k = 0;
        Pos pos = new Pos();
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 5; j++, k = k + 5){
                pos.setX(i);
                pos.setY(j);

                assertEquals(p1.getWindowPatternCard().getCell(pos).getProperty().getColour().toString().charAt(0), (parsed.charAt(k)));

                assertEquals(p1.getWindowPatternCard().getCell(pos).getProperty().getNumber(),(Character.getNumericValue(parsed.charAt((k+1)))));
            }
        }
    }

    @Test
    void testParsingDraftPool(){

        Player p1 = new Player("Luca");
        Player p2 = new Player("Giovanni");
        Player p3 = new Player("Andrea");
        Player p4 = new Player("Vincenzo");

        CardContainer container = new CardContainer();
        List<WindowPatternCard> windowPatternCards = container.pullOutPattern(4);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        p1.setMyWindow(windowPatternCards.get(0));
        p2.setMyWindow(windowPatternCards.get(1));
        p3.setMyWindow(windowPatternCards.get(2));
        p4.setMyWindow(windowPatternCards.get(3));

        Table table = new Table(players);
        table.initialize();
        table.setPublicObjects();
        table.fillDraftPool();


        p1.getWindowPatternCard().placeDice(table.getDraftPool().chooseDice(0),0,0);
        p1.getWindowPatternCard().placeDice(table.getDraftPool().chooseDice(1),0,2);
        p1.getWindowPatternCard().placeDice(table.getDraftPool().chooseDice(2),1,1);
        p1.getWindowPatternCard().placeDice(table.getDraftPool().chooseDice(3),2,3);
        p1.getWindowPatternCard().placeDice(table.getDraftPool().chooseDice(4),3,2);

        VirtualViewParser tester = new VirtualViewParser(p1);

        String parsed = tester.parseDraftPool();

        parsed = parsed.substring(parsed.indexOf("draftpool ")+10,parsed.length());

        for(int i = 0, k = 0; i < table.getDraftPool().getNumOfDices(); i++, k = k+3){
            assertEquals(table.getDraftPool().chooseDice(i).getColour().toString().charAt(0), (parsed.charAt(k)));

            assertEquals(table.getDraftPool().chooseDice(i).getNumber(), (Character.getNumericValue(parsed.charAt((k+1)))));
        }

    }

    @Test
    void testParsingWindowDices(){

        Player p1 = new Player("Luca");

        Player p2 = new Player("Giovanni");

        Player p3 = new Player("Andrea");

        Player p4 = new Player("Vincenzo");

        CardContainer container = new CardContainer();
        List<WindowPatternCard> windowPatternCards = container.pullOutPattern(4);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        p1.setMyWindow(windowPatternCards.get(0));
        p2.setMyWindow(windowPatternCards.get(1));
        p3.setMyWindow(windowPatternCards.get(2));
        p4.setMyWindow(windowPatternCards.get(3));

        Table table = new Table(players);

        table.initialize();

        table.setPublicObjects();

        table.fillDraftPool();

        p1.getWindowPatternCard().placeDice(table.getDraftPool().chooseDice(0),0,0);
        p1.getWindowPatternCard().placeDice(table.getDraftPool().chooseDice(1),0,2);
        p1.getWindowPatternCard().placeDice(table.getDraftPool().chooseDice(2),1,1);
        p1.getWindowPatternCard().placeDice(table.getDraftPool().chooseDice(3),2,3);
        p1.getWindowPatternCard().placeDice(table.getDraftPool().chooseDice(4),3,2);



        p2.getWindowPatternCard().placeDice(table.getDraftPool().chooseDice(5),0,0);
        p2.getWindowPatternCard().placeDice(table.getDraftPool().chooseDice(6),0,2);
        p2.getWindowPatternCard().placeDice(table.getDraftPool().chooseDice(7),1,1);
        p2.getWindowPatternCard().placeDice(table.getDraftPool().chooseDice(8),2,3);

        VirtualViewParser tester = new VirtualViewParser(p1);

        String parsed = tester.startParsing();

        parsed = parsed.substring(parsed.indexOf("dices Luca,")+11,parsed.length());

        for(int i = 0, k = 0; i < 5 ; i++, k = k+5){

            assertEquals(table.getDraftPool().chooseDice(i).getColour().toString().charAt(0), (parsed.charAt(k)));

            assertEquals(table.getDraftPool().chooseDice(i).getNumber(), (Character.getNumericValue(parsed.charAt((k+1)))));
        }

    }
}
