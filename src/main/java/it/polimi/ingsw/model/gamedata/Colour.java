package it.polimi.ingsw.model.gamedata;

/**
 * Enum used to represent the colours of the dices and restrictions
 */
public enum Colour {
    RED("R"),
    YELLOW("Y"),
    GREEN("G"),
    BLUE("B"),
    PURPLE("P"),
    WHITE("W");

    private String name;

    /**
     * Save the name of the colour
     * @param mystring The name of the colour
     */
    Colour(String mystring) {
        this.name = mystring;
    }

    /**
     * Find out if the character passed corresponds to a colour
     * @param c The character representing the colour
     * @return The Colour corresponding to the character passed
     */
    public static Colour isIn(char c) {
        switch (c){
            case 'R':
                return Colour.RED;
            case 'Y':
                return Colour.YELLOW;
            case 'B':
                return Colour.BLUE;
            case 'G':
                return Colour.GREEN;
            case 'P':
                return Colour.PURPLE;
            case 'W':
                return Colour.WHITE;
            default:
                return Colour.WHITE;
        }
    }

    /**
     * @return The string representing the name of the colour
     */
    @Override
    public String toString(){
        return name;
    }

    /**
     * Tells if the Colour passed is the same as this one
     * @param c The colour passed
     * @return If they're the same
     */
    public boolean areEquals(Colour c){
        return this.toString().equals(c.toString());
    }

}
