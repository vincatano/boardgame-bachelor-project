package it.polimi.ingsw.model.gamelogic.checker;

import it.polimi.ingsw.model.gamedata.Player;
import it.polimi.ingsw.model.gamedata.Pos;
import it.polimi.ingsw.model.gamedata.gametools.Dice;
import it.polimi.ingsw.model.gamedata.gametools.DraftPool;
import it.polimi.ingsw.model.gamedata.gametools.ToolCard;

import java.util.List;

/**
 * This class check if the chosen object is chosen from the tools available on the table.
 */
public class InspectorContext {


    /**
     * This method check if the chosen dice si pulled out from dice bag.
     * @param d Chosen dice.
     * @param pos Position of chosen dice, x is the index where draft pool will check.
     * @param draft Draft pool on the table.
     * @return True if draft pool contains that dice in that position.
     */
    public boolean check(Dice d, Pos pos, DraftPool draft){
        return draft.findDice(d,pos.getX());
    }

    /**
     * This method check if the group of tool cards in the table contains the chosen tool card.
     * @param tool Chosen tool card.
     * @param allTool Group of tool card on the table.
     * @param p Player who choose card
     * @return True if the chosen tool card is in the group and if player has enough favour token.
     */
    public boolean check (ToolCard tool, List<ToolCard> allTool, Player p){
        for (ToolCard t : allTool){
            if (t.getID()==tool.getID()&&p.canUseTool(t.isUsed())){
                return true;
            }
        }
        return false;
    }

}
