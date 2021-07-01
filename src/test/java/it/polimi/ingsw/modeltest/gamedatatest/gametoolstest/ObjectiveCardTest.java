package it.polimi.ingsw.modeltest.gamedatatest.gametoolstest;

import it.polimi.ingsw.model.gamedata.Colour;
import it.polimi.ingsw.model.gamedata.gametools.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjectiveCardTest {

    @Test
    public void testCardOne(){
        ObjectiveCard tester;
        CardContainer container = new CardContainer();
        List<ObjectiveCard> objectiveCards;
        WindowPatternCard windowPatternCard = new WindowPatternCard(50,10,"noName");
        Dice db = new Dice(Colour.BLUE);
        Dice dy = new Dice(Colour.YELLOW);
        Dice dr = new Dice(Colour.RED);
        Dice dp = new Dice(Colour.PURPLE);
        Dice dg = new Dice(Colour.GREEN);

        objectiveCards = container.pullOutPublic();
        int i = 0, j = 1, k = 2;
        boolean x = true;
        while(x) {
            container = new CardContainer();
            objectiveCards = container.pullOutPublic();
            if ((objectiveCards.get(i).getID() == 1)) {
                x = false;
            }
            if ((objectiveCards.get(j).getID() == 1)) {
                i = j;
                x = false;
            }
            if ((objectiveCards.get(k).getID() == 1)) {
                i = k;
                x = false;
            }
        }
        tester = objectiveCards.get(i);

        windowPatternCard.placeDice(db,0,0);
        windowPatternCard.placeDice(dy,0,1);
        windowPatternCard.placeDice(dr,0,2);
        windowPatternCard.placeDice(dp,0,3);
        windowPatternCard.placeDice(dg,0,4);

        assertEquals(6,tester.finalpoints(windowPatternCard));

        int s1;
        s1 = windowPatternCard.getMatr().size();
        for(i = 1; i < s1; i++){
            windowPatternCard.placeDice(db,i,0);
            windowPatternCard.placeDice(dy,i,1);
            windowPatternCard.placeDice(dr,i,2);
            windowPatternCard.placeDice(dp,i,3);
            windowPatternCard.placeDice(dg,i,4);
            assertEquals((i+1)*6,tester.finalpoints(windowPatternCard));
        }

        windowPatternCard = new WindowPatternCard(50,10,"noName");

        windowPatternCard.placeDice(db,0,0);
        windowPatternCard.placeDice(dy,0,1);
        windowPatternCard.placeDice(dr,0,2);
        windowPatternCard.placeDice(dp,0,3);
        windowPatternCard.placeDice(dg,0,4);

        assertEquals(6,tester.finalpoints(windowPatternCard));
        windowPatternCard.placeDice(db,1,0);
        windowPatternCard.placeDice(db,1,1);
        windowPatternCard.placeDice(dr,1,2);
        windowPatternCard.placeDice(dp,1,3);
        windowPatternCard.placeDice(dg,1,4);

        assertEquals(6,tester.finalpoints((windowPatternCard)));
        windowPatternCard.placeDice(db,2,0);
        windowPatternCard.placeDice(dy,2,1);
        windowPatternCard.placeDice(dr,2,2);
        windowPatternCard.placeDice(dp,2,3);
        windowPatternCard.placeDice(dg,2,4);

        assertEquals(12,tester.finalpoints((windowPatternCard)));
    }

}

