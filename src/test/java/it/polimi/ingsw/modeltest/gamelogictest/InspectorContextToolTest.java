package it.polimi.ingsw.modeltest.gamelogictest;

import it.polimi.ingsw.model.gamedata.Pos;
import it.polimi.ingsw.model.gamedata.gametools.*;
import it.polimi.ingsw.model.gamelogic.checker.InspectorContextTool;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InspectorContextToolTest {

    @Test
    public void testColour_And_Number(){
        DiceBag diceBag = new DiceBag();
        DraftPool draftPool = new DraftPool();
        draftPool.addNewDices(diceBag.pullOut(4));

        Pos newpos = new Pos(2,1);
        Pos oldpos = new Pos(2,1);

        Dice oldice = draftPool.chooseDice(3);

        Dice newdice = new Dice(oldice.getColour());

        InspectorContextTool tester = new InspectorContextTool(new WindowPatternCard(50,10,"noName"),draftPool,new RoundTrack());

        assertTrue(tester.checkColourDice(oldice,oldpos,newdice,newpos));

        if(oldice.getNumber() == 6)
            newdice.setNumber(oldice.getNumber()-1);
        else if(oldice.getNumber() == 1)
            newdice.setNumber(oldice.getNumber()+1);
        else
            newdice.setNumber(oldice.getNumber()-1);

        assertTrue(tester.checkNumDice(oldice,oldpos,newdice,newpos));

        newdice.setNumber(oldice.getNumber()+2);

        assertFalse(tester.checkNumDice(oldice,oldpos,newdice,newpos));

    }
}
