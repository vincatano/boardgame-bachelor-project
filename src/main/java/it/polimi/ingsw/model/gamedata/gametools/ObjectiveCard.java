package it.polimi.ingsw.model.gamedata.gametools;

import it.polimi.ingsw.model.gamedata.Rules;


/**
 * This class represents the objective cards
 */
public class ObjectiveCard extends Card {
    private int points;
    private String type;
    private Rules rules;


    /**
     * The classic constructor
     */
    public ObjectiveCard(){
        super();
        this.points = 0;
        this.type = "";
        this.rules = new Rules();
    }

    /**
     * Getter method for the type (private of public card)
     * @return The type of the card
     */
    public String getType(){
        return this.type;
    }

    /**
     * Getter method for the rules
     * @return The rules of the card
     */
    public Rules getRules(){
        return this.rules;
    }

    /**
     * Setter method for the type (private of public card)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Setter method for the points
     */
    protected void setPoints(int points) {
        this.points = points;
    }

    /**
     * This method calculates, depending on the rules of the card, the final points using the WindowPatter card passed
     * @param w The WindowPattern card of which calculates the points
     * @return The points earned applying the rules on the WindowPatter card
     */
    public int finalpoints(WindowPatternCard w){
        int result = 0;
        for (String x: this.rules.getRules()) {
            result = result + ((this.rules.verify(x,w))*points);
        }
        return result;
    }


}
