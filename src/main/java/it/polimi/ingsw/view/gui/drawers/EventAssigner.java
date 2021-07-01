package it.polimi.ingsw.view.gui.drawers;

import it.polimi.ingsw.view.gui.guicontrollers.MatchViewController;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Class that contains all the methods that can be used to assign events to a dice.
 * This static class is used like a tool that offers some functionalities.
 */
public final class EventAssigner {

    /**
     * Private constructor to prevent the instantiation of any EventAssigner object
     */
    private EventAssigner() {

    }

    /**
     * Method used to assign to a given DraftPool dice (ImageView) the event that needs to be launched when the player clicks on it.
     * @param image ImageView object representing the dice
     * @param controller The controller object that has called this method
     */
    public static void addDraftEvent(ImageView image, MatchViewController controller) {
        image.addEventHandler(MouseEvent.MOUSE_PRESSED, controller::handleDraftDiceClicked);
    }

    /**
     * Method used to assign to a given Pattern card dice (ImageView) the event that needs to be launched when the player clicks on it.
     * @param image ImageView object representing the dice
     * @param controller The controller object that has called this method
     */
    public static void addWindowEvent(ImageView image,MatchViewController controller) {
        image.addEventHandler(MouseEvent.MOUSE_PRESSED, controller::handleWindowDiceClicked);
    }

    /**
     * Method used to assign to a given RoundTrack dice (ImageView) the event that needs to be launched when the player clicks on it.
     * @param image ImageView object representing the dice
     * @param controller The controller object that has called this method
     */
    public static void addRoundTrackEvent(ImageView image,MatchViewController controller) {
        image.addEventHandler(MouseEvent.MOUSE_PRESSED, controller::handleRoundTrackDiceClicked);
    }

}
