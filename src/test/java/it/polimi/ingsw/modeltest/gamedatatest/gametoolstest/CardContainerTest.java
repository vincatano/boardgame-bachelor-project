package it.polimi.ingsw.modeltest.gamedatatest.gametoolstest;


import it.polimi.ingsw.model.gamedata.gametools.CardContainer;
import it.polimi.ingsw.model.gamedata.gametools.ObjectiveCard;
import it.polimi.ingsw.model.gamedata.gametools.ToolCard;
import it.polimi.ingsw.model.gamedata.gametools.WindowPatternCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardContainerTest {

    @Test
    public void testPrivatePullout(){
        CardContainer tester = new CardContainer();
        List<ObjectiveCard> test = null;
        test = tester.pullOutPrivate(2);
        assertEquals(2, test.size());
    }

    @Test
    public void testPublicPullout(){
        CardContainer tester = new CardContainer();
        List<ObjectiveCard> test = null;
        test = tester.pullOutPublic();
        assertEquals(3, test.size());
    }

    @Test
    public void testPublicRules() {
        CardContainer tester = new CardContainer();
        List<ObjectiveCard> test = new ArrayList<ObjectiveCard>();
        test = tester.pullOutPublic();
        for (ObjectiveCard x: test) {
            assertTrue(!(x.getRules().getRules().isEmpty()));
        }
    }

    @Test
    public void testPatternPullOut(){
        CardContainer tester = new CardContainer();
        List<WindowPatternCard> test;
        test = tester.pullOutPattern(2);
        assertEquals(8,test.size());

    }

    @Test
    public void testToolCardsPullOut(){
        CardContainer tester = new CardContainer();
        List<ToolCard> test = tester.pullOutTools();

        /*for (ToolCard toolcard : test){
            toolcard.show();
        }*/
        assertEquals(3,test.size());
    }

    @Test
    public void testTooManyPlayers(){
        CardContainer tester = new CardContainer();
        assertThrows(IndexOutOfBoundsException.class,()->{tester.pullOutPattern(6);});
        assertThrows(IndexOutOfBoundsException.class,()->{tester.pullOutPrivate(8);});
        assertThrows(IndexOutOfBoundsException.class,()->{tester.pullOutPattern(-4);});
        assertThrows(IndexOutOfBoundsException.class,()->{tester.pullOutPrivate(-2);});
    }
}
