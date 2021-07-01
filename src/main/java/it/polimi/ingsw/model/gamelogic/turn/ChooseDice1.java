package it.polimi.ingsw.model.gamelogic.turn;

import it.polimi.ingsw.model.gamedata.Player;
import it.polimi.ingsw.model.gamedata.Pos;
import it.polimi.ingsw.model.gamedata.gametools.Dice;
import it.polimi.ingsw.model.gamedata.gametools.ToolCard;
import it.polimi.ingsw.model.gamelogic.checker.InspectorContext;
import it.polimi.ingsw.model.gamelogic.checker.InspectorPlace;
import it.polimi.ingsw.model.gamelogic.turn.moveexceptions.WrongMoveException;

import java.util.ArrayList;

/**
 * Class defining the concrete state ChooseDice1,in this state a player can chose a ToolCard, a Position, or Pass move.
 * If he choose a ToolCard then the next state would be dynamically loaded and the state "EndTurn" would be
 * set as CheckPoint state. If he choose a Position the next state would be "PositionDice1". Finally if he choose to pass
 * the next state would be "EndTurn".
 */
public class ChooseDice1 implements TurnState {
    private static final int TOOLCARD_1 = 1;
    private static final int TOOLCARD_5 = 5;
    private static final int TOOLCARD_6 = 6;
    private static final int TOOLCARD_9 = 9;
    private static final int TOOLCARD_10 = 10;
    private static final int TOOLCARD_11 = 11;
    private Turn turn;
    private Dice chosenDice;
    private Pos posDiceChosen;
    private ArrayList<Integer> toolList = new ArrayList<>();
    private InspectorContext inspectorContext;
    private InspectorPlace inspectorPlace;

    /**
     * Classic constructor.
     * @param turn Store the Turn object in order to call methods on it, like changing the concrete state of the turn calling setState().
     * @param chosenDice The Dice the player has just selected.
     * @param posDiceChosen The Position of the Dice the player has just selected (chosenDice).
     */
    public ChooseDice1(Turn turn, Dice chosenDice, Pos posDiceChosen) {
        this.turn = turn;
        this.chosenDice = chosenDice;
        this.posDiceChosen = posDiceChosen;
        this.inspectorContext = turn.getInspectorContext();
        this.inspectorPlace = turn.getInspectorPlace();
        this.toolList.add(TOOLCARD_1);
        this.toolList.add(TOOLCARD_5);
        this.toolList.add(TOOLCARD_6);
        this.toolList.add(TOOLCARD_9);
        this.toolList.add(TOOLCARD_10);
        this.toolList.add(TOOLCARD_11);
    }

    /**
     * {@inheritDoc}
     * The ToolCards that the player can choose in this state are: 1,5,6,9,10,11
     * In his implementation the move is valid i.f.f. : the toolCard chosen by the player is one of those that can be used in this state and
     * the method check() on the inspectorContext returns true value (the toolCard actually is one of those which are on the table).
     * If the move is valid then we change state, we throw the exception otherwise.
     */
    //GETTING MOVE METHODS
    @Override
    public void receiveMove(ToolCard toolCard) throws WrongMoveException {
        Player currentPlayer = turn.getPlayer();
        if(toolList.contains(toolCard.getID()) && (inspectorContext.check(toolCard, currentPlayer.getToolCards(),currentPlayer))) {
            //call the modifier method to update the cost of the toolcard and the favtokens of the player
            turn.getModifier().updateToolCardPrice(toolCard,currentPlayer);
            //setting the toolCard used in this turn, setting the list of states for the dynamic state machine, setting the list of operations for the AutomatedOperation State
            turn.setToolCardInfo(toolCard);
            //setting the check point i need to return after the user do the moves of the toolCard
            turn.setCheckPointState(new EndTurn(turn));
            //need to set dynamic current state
            turn.setDynamicState(chosenDice,posDiceChosen, new Dice(), new Pos());
        } else {
            turn.resetSelectionDice();
            throw new WrongMoveException("Mossa sbagliata: non è possibile scegliere la carta strumento "+ toolCard.getName() +".");
        }
        turn.resetSelectionDice();
    }

    /**
     * {@inheritDoc}
     * In this implementation the method check() on the inspectorPlace is being called to actually understand whether the move is
     * valid or not. If it is valid then we change state, we throw the exception otherwise.
     */
    @Override
    public void receiveMove(Pos pos) throws WrongMoveException {
        if(inspectorPlace.check(chosenDice,pos,turn.getPlayer().getWindowPatternCard())) {
            turn.getModifier().positionDiceFromDraft(chosenDice, posDiceChosen, pos);
            turn.setState(new PositionDice1(turn));
        } else {
            turn.resetSelectionDice();
            throw new WrongMoveException("Mossa sbagliata: selezionare una posizione della Vetrata che rispetti le regole di piazzamento.");
        }
        turn.resetSelectionDice();
    }

    @Override
    public void receiveMove(String pass) {
        turn.setState(new EndTurn(turn));
    }
}
