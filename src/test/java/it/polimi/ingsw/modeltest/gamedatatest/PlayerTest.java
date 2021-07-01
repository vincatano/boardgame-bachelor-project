package it.polimi.ingsw.modeltest.gamedatatest;


import it.polimi.ingsw.model.gamedata.Player;
import it.polimi.ingsw.model.gamedata.gametools.WindowPatternCard;
import it.polimi.ingsw.view.virtualview.VirtualViewObserver;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Observable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerTest {

    @Test
    public void testFavorTokens(){
        Player tester = new Player("vincenzo");
        VirtualViewObserver observer = new VirtualViewObserver() {
            @Override
            public void update() {

            }

            @Override
            public void updateStateTurn(String whoIsTurn, long timeSleep) {

            }

            @Override
            public void wrongMove(String s) {

            }

            @Override
            public void chooseWindow(List<WindowPatternCard> windows) {

            }

            @Override
            public void timerChoose(long timerWindows) {

            }

            @Override
            public void notifyState(String state) {

            }

            @Override
            public void notifyScore(String s) {

            }

            @Override
            public void reconnectingMessage() {

            }

            public void update(Observable o, Object arg) {

            }
        };
        tester.addObserver(observer);
        WindowPatternCard windowPatternCard = new WindowPatternCard(50,10,"noName");
        windowPatternCard.setDifficulty(5);
        tester.setMyWindow(windowPatternCard);

        tester.setActivity(true);
        assertTrue(tester.isActive());

        tester.setActivity(false);
        assertFalse(tester.isActive());

        assertTrue(tester.canUseTool(true));

        assertTrue(tester.canUseTool(false));

        int tokens = tester.getMyFavTokens();
        tester.useToken(true);
        assertEquals(tokens-2,tester.getMyFavTokens());

        tester.useToken(false);
        assertEquals(tokens-3,tester.getMyFavTokens());

        assertTrue(tester.canUseTool(true));
        tester.useToken(false);
        assertTrue(tester.canUseTool(false));

    }

}