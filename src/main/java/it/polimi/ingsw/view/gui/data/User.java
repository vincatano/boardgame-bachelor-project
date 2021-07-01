package it.polimi.ingsw.view.gui.data;

/**
 * User class is used to store the username,status and score of a player in order to display these information inside a TableView object.
 * This way different message's information are merged in a single place and easy to use to update the table.
 */
public class User {
    private String username;
    private String status = "";
    private String score = "";

    /**
     * Constructor used when only the player's username is known
     * @param username The player's username
     */
    public User(String username) {
        this.username = username;
    }

    /**
     * Constructor used when both player's username and status are known
     * @param username The player's username
     * @param status The player's state (can be: active/inactive)
     */
    public User(String username, String status) {
        this.username = username;
        this.status = status;
    }

    /**
     * Setter method to set the player's username, added for eventualities.
     * @param username The player's username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Setter method to set the player's state
     * @param status The player's state (can be: active/inactive)
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Setter method used to set the player's score
     * @param score The player's score
     */
    public void setScore(String score) {
        this.score = score;
    }

    /**
     * Getter method to get the player's username
     * @return the player's username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Getter method to get the player's state
     * @return the player's state
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Getter method to get the player's score
     * @return the player's score
     */
    public String getScore() {
        return this.score;
    }
}
