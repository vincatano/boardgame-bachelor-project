package it.polimi.ingsw.view.gui.data;

import javafx.scene.image.ImageView;

/**
 * Class used to represent a dice present in the Gui Interface that can be actually be selected by the player.
 * Information about this dice are stored, like the it's color, number and image.
 */
public class ViewDice {
    private static final int COLOR_INDEX = 0;
    private static final int NUMBER_INDEX = 1;
    private ImageView image;
    private char color;
    private char number;

    /**
     * Classic constructor
     * @param image The imageView of the dice that needs to be stored
     * @param info A String containing as first character the dice's color and as second character the dice's number.
     */
    public ViewDice(ImageView image, String info) {
        this.image = image;
        this.color = info.charAt(COLOR_INDEX);
        this.number = info.charAt(NUMBER_INDEX);
    }

    /**
     * Getter method to deliver the dice's color
     * @return The dice's color
     */
    public char getDiceColor() {
        return this.color;
    }

    /**
     * Getter method to deliver the dice's number
     * @return The dice's number
     */
    public char getDiceNumber() {
        return this.number;
    }

    /**
     * Getter method to deliver the dice's ImageView
     * @return The dice's ImageView
     */
    public ImageView getDiceImage() {
        return image;
    }
}
