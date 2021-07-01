package it.polimi.ingsw.modeltest.gamelogictest;

import it.polimi.ingsw.model.gamedata.Player;
import it.polimi.ingsw.model.gamedata.Pos;
import it.polimi.ingsw.model.gamedata.Table;
import it.polimi.ingsw.model.gamedata.gametools.*;
import it.polimi.ingsw.model.gamelogic.checker.InspectorContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class InspectorContextTest {

    @Test
    public void testCheckTools(){
        InspectorContext tester = new InspectorContext();
        Player p1 = new Player("nobody");
        WindowPatternCard windowPatternCard = new WindowPatternCard(50,10,"noName");
        p1.setMyWindow(windowPatternCard);
        List<Player> players = new ArrayList<>();
        players.add(p1);

        Table table = new Table(players);
        table.initialize();

        ToolCard toolcard;
        List<ToolCard> toolCardArrayList;
        toolCardArrayList = table.getToolCards();

        toolcard = toolCardArrayList.get(2);


        assertTrue(tester.check(toolcard,toolCardArrayList,p1));
    }

    @Test
    public void testCheckPlaceDice(){
        InspectorContext tester = new InspectorContext();
        DiceBag diceBag = new DiceBag();
        diceBag.setNumPlayers(4);
        DraftPool draftPool = new DraftPool();
        draftPool.addNewDices(diceBag.pullOut());

        Dice d = draftPool.chooseDice(5);

        Pos pos = new Pos(5,0);

        tester.check(d,pos,draftPool);
    }

}
