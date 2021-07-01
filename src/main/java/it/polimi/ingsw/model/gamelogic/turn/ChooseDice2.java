package it.polimi.ingsw.model.gamelogic.turn;

import it.polimi.ingsw.model.gamedata.Pos;
import it.polimi.ingsw.model.gamedata.gametools.Dice;
import it.polimi.ingsw.model.gamelogic.checker.InspectorPlace;
import it.polimi.ingsw.model.gamelogic.turn.moveexceptions.WrongMoveException;

/**
 * Class defining the concrete state ChooseDice2,in this state a player can choose a Position, or Pass move.
 * If he choose a Position the next state would be "EndTurn". Finally if he choose to pass the next state would also be "EndTurn".
 */
public class ChooseDice2 implements TurnState {
    private Turn turn;
    private Pos posDiceChosen;
    private Dice chosenDice;
    private InspectorPlace inspectorPlace;

    /**
     * Classic constructor.
     * @param turn Store the Turn object in order to call methods on it, like changing the concrete state of the turn calling setState().
     * @param chosenDice The Dice the player has just selected.
     * @param posDiceChosen The Position of the Dice the player has just selected (chosenDice).
     * @param toolDice The dice has chosen through the toolCard.
     * @param toolPos The position of the the Dice the player has chosen through the toolCard (toolDice).
     */
    public ChooseDice2(Turn turn, Dice chosenDice,Pos posDiceChosen, Dice toolDice, Pos toolPos) {
        this.turn = turn;
        this.chosenDice = chosenDice;
        this.posDiceChosen = posDiceChosen;
        this.inspectorPlace = turn.getInspectorPlace();
    }

    //GETTING MOVE METHODS

    /**
     * {@inheritDoc}
     * In this implementation the method check() on the inspectorPlace is being called to actually understand whether the move is
     * valid or not. If it is valid then we change state, we throw the exception otherwise.
     */
    @Override
    public void receiveMove(Pos pos) throws WrongMoveException{
        if(inspectorPlace.check(chosenDice,pos,turn.getPlayer().getWindowPatternCard())) {
            turn.getModifier().positionDiceFromDraft(chosenDice,posDiceChosen,pos);
            turn.setState(new EndTurn(turn));
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
