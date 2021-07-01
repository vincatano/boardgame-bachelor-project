package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.gamedata.Colour;
import it.polimi.ingsw.model.gamedata.Pos;
import it.polimi.ingsw.model.gamedata.gametools.Dice;
import it.polimi.ingsw.model.gamedata.gametools.ToolCard;
import it.polimi.ingsw.model.gamelogic.Round;

import static java.lang.System.out;

/**
 * This class will process the input from user, transforming it to objects model
 */
class Proc {
    private Round round;
    private String move;

    Proc(Round round, String move) {
        this.round = round;
        this.move= move;
    }

    /**
     * According to the communication protocol, this method verify the kind of move and will create a clone of the chosen
     * dice,toolcard or position, after that will send it to the round which verify its validity.
     */
    void process() {
        try {
            if (move.startsWith("D;")) {
                String res = move.replace("D;", "");
                String[] diceToken = res.split(",");
                Dice dice = new Dice(Colour.isIn(diceToken[1].charAt(0)));
                dice.setNumber(Integer.parseInt(diceToken[0]));
                Pos pos = new Pos(Integer.parseInt(diceToken[2]), Integer.parseInt(diceToken[3]));
                round.setTurn(dice, pos);
            } else if (move.startsWith("P")) {
                String res = move.replace("P;", "");
                String[] posToken = res.split(",");
                Pos pos = new Pos(Integer.parseInt(posToken[0]), Integer.parseInt(posToken[1]));
                round.setTurn(pos);
            } else if (move.startsWith("T;")) {
                String res = move.replace("T;", "");
                ToolCard t = new ToolCard().exactToolCard(Integer.parseInt(res));
                round.setTurn(t);
            } else if (move.equals("pass")) {
                round.setTurn("pass");
            }
        }catch (RuntimeException e){
            out.println("Bad Format input");
        }
    }
}
