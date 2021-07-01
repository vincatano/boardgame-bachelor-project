package it.polimi.ingsw.model.gamelogic.turn;

import it.polimi.ingsw.model.gamedata.Pos;
import it.polimi.ingsw.model.gamedata.gametools.Dice;
import it.polimi.ingsw.model.gamelogic.ModelModifier;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Class defining the concrete state AutomatedOperation, in this state the player can't do any move.
 * A set of automated operation are going to be done and only when there are no other operations to be done for a given toolCard,
 * the concrete state will dynamically change.
 */
public class AutomatedOperation implements TurnState {
    private Turn turn;
    private Dice chosenDice;
    private Pos posChosenDice;
    private Dice toolDice;
    private Pos toolPos;

    /**
     * Classic constructor.
     * @param turn Store the Turn object in order to call methods on it, like changing the concrete state of the turn calling setState().
     * @param chosenDice The Dice the player has just selected.
     * @param posChosenDice The Position of the Dice the player has just selected (chosenDice).
     * @param toolDice The dice has chosen through the toolCard.
     * @param toolPos The position of the the Dice the player has chosen through the toolCard (toolDice).
     */
    public AutomatedOperation(Turn turn, Dice chosenDice, Pos posChosenDice, Dice toolDice, Pos toolPos) {
        this.turn = turn;
        this.chosenDice = chosenDice;
        this.posChosenDice = posChosenDice;
        this.toolDice = toolDice;
        this.toolPos = toolPos;
    }

    /**
     * This method gets as parameter a list which is read in a given toolCard's xml file. Here all those methods are going to be
     * called in order to do some automatic operations that are needed for a certain toolCard. After all the automated operation have
     * been done the concrete state of the turn will be dynamically changed.
     * @param toolAutomatedOperationList A list of method names of the ModelModifier class which defines a set of automated operation
     *                                   that needs to be done for a given ToolCard
     */
    public void doAutomatedOperations(List<String> toolAutomatedOperationList) {
        ModelModifier modifier = turn.getModifier();

        for (String nextOperationName: toolAutomatedOperationList) {
            if(!nextOperationName.equals("operationEnded")) {
                try {
                    Class<?> cls = Class.forName("it.polimi.ingsw.model.gamelogic.ModelModifier");

                    Class[] parametersType = new Class[4];
                    parametersType[0] = Dice.class;
                    parametersType[1] = Pos.class;
                    parametersType[2] = Dice.class;
                    parametersType[3] = Pos.class;

                    Method method = cls.getMethod(nextOperationName, parametersType);

                    Object[] argumentList = new Object[4];
                    argumentList[0] = chosenDice;
                    argumentList[1] = posChosenDice;
                    argumentList[2] = toolDice;
                    argumentList[3] = toolPos;

                    method.invoke(modifier, argumentList);
                } catch (ReflectiveOperationException e) {
                    /*this exception should not be thrown*/
                }
            } else {
                turn.setDynamicState(chosenDice,posChosenDice, toolDice, toolPos);
            }
        }
    }

}
