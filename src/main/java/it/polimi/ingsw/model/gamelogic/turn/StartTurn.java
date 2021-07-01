package it.polimi.ingsw.model.gamelogic.turn;

import it.polimi.ingsw.model.gamedata.Player;
import it.polimi.ingsw.model.gamedata.Pos;
import it.polimi.ingsw.model.gamedata.gametools.Dice;
import it.polimi.ingsw.model.gamedata.gametools.ToolCard;
import it.polimi.ingsw.model.gamelogic.checker.InspectorContext;
import it.polimi.ingsw.model.gamelogic.turn.moveexceptions.WrongMoveException;

import java.util.ArrayList;

/**
 * Class defining the concrete state StartTurn, in this state the player can choose a ToolCard, a Dice, or Pass move.
 * If he choose a ToolCard then the next state would be dynamically loaded and the state "ToolBeforeDice" would be
 * set as CheckPoint state. If he choose a Dice the next state would be "ChooseDice1". Finally if he choose to pass
 * the next state would be "EndTurn".
 */
public class StartTurn implements TurnState {
    private static final int TOOLCARD_2 = 2;
    private static final int TOOLCARD_3 = 3;
    private static final int TOOLCARD_4 = 4;
    private static final int TOOLCARD_7 = 7;
    private static final int TOOLCARD_12 = 12;
    private Turn turn;
    private ArrayList<Integer> toolList = new ArrayList<>();
    private InspectorContext inspectorContext;

    /**
     * Classic constructor
     * @param turn Store the Turn object in order to call methods on it, like changing the concrete state of the turn calling setState().
     */
    public StartTurn(Turn turn) {
        this.turn = turn;
        this.inspectorContext = turn.getInspectorContext();
        this.toolList.add(TOOLCARD_2);
        this.toolList.add(TOOLCARD_3);
        this.toolList.add(TOOLCARD_4);
        this.toolList.add(TOOLCARD_7);
        this.toolList.add(TOOLCARD_12);
    }

    //GETTING MOVE METHODS

    /**
     * {@inheritDoc}
     * The ToolCards that the player can choose in this state are: 2,3,4,12,7 (can be used only in the second turn of a given round).
     * In his implementation the move is valid i.f.f. : the toolCard chosen by the player is one of those that can be used in this state,
     * the method check() on the inspector Context returns true value (the toolCard actually is one of those which are on the table),
     * finally if the toolCard is the number 7 then it's not the firstBracket.
     * If the move is valid then we change state, we throw the exception otherwise.
     */
    @Override
    public void receiveMove(ToolCard toolCard) throws WrongMoveException {
        int cardId = toolCard.getID();
        Player currentPlayer = turn.getPlayer();
        if(toolList.contains(cardId) && (inspectorContext.check(toolCard, currentPlayer.getToolCards(),currentPlayer)) &&
                ((cardId != TOOLCARD_7) || (!turn.isFirstBracket()))) {
            //call this method to state that the player has done at least 1 successful move
            currentPlayer.moveDone();
            //call the modifier method to update the cost of the toolcard and the favtokens of the player
            turn.getModifier().updateToolCardPrice(toolCard,currentPlayer);
            //setting the toolCard used in this turn, setting the list of states for the dynamic state machine, setting the list of operations for the AutomatedOperation State
            turn.setToolCardInfo(toolCard);
            //setting the check point i need to return to, after the user do the moves of the toolCard
            turn.setCheckPointState(new ToolBeforeDice(turn));
            //need to set dynamic current state
            turn.setDynamicState(new Dice(), new Pos(),new Dice(), new Pos());
        } else {
            throw new WrongMoveException("Mossa sbagliata: non è possibile scegliere la carta strumento "+ toolCard.getName() +".");
        }
    }

    /**
     * {@inheritDoc}
     * In this implementation the method check() on the inspectorContext is being called to actually understand whether the move is
     * valid or not. If it is valid then we change state, we throw the exception otherwise.
     */
    @Override
    public void receiveMove(Dice dice, Pos pos) throws WrongMoveException {
        if(inspectorContext.check(dice,pos,turn.getPlayer().getDraftPool())) {
            //call this method to state that the player has done at least 1 successful move
            turn.getPlayer().moveDone();
            turn.setState(new ChooseDice1(turn, dice, pos));
        } else {
            throw new WrongMoveException("Mossa sbagliata: non è possibile scegliere questo dado. Selezionare un dado della Riserva.");
        }
    }

    @Override
    public void receiveMove(String pass) {
        //call this method to state that the player has done at least 1 successful move
        turn.getPlayer().moveDone();
        turn.setState(new EndTurn(turn));
    }
}
