package it.polimi.ingsw.model.gamedata.gametools;

/**
 * An abstract class representing the card
 */
public abstract class  Card {
    private String name;
    private String description;
    private int idnumber;

    /**
     * Base constructor
     */
    Card(){
        this.name = "No name";
        this.description = "No description";
        this.idnumber = -1;
    }

    /**
     * Getter for the name of the card
     * @return The name of the card
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for the description of the card
     * @return The description of the card
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Getter for the id number of the card
     * @return The id number of the card
     */
    public int getID(){
        return this.idnumber;
    }

    /**
     * Setter for the name of the card
     * @return The name you want to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for the description of the card
     * @return The description you want to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Setter for the id number of the card
     * @return The id number you want to set
     */
    public void setID(int id) {
        this.idnumber = id;
    }

}
