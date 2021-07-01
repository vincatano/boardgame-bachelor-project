package it.polimi.ingsw.view.gui.drawers;

import it.polimi.ingsw.view.gui.data.ViewDice;
import it.polimi.ingsw.view.gui.guicontrollers.MatchViewController;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.List;

/**
 * Class that contains all the methods that can be used to draw the GUI RoundTrack.
 * This static class is used like a tool that offers some functionalities.
 */
public final class DrawRoundTrack {

    /**
     * Private constructor to prevent the instantiation of any DrawRoundTrack object
     */
    private DrawRoundTrack() {

    }

    /**
     * This method is used to draw the dice contained in the roundTrack.
     * @param roundTrackGrid The GridPane that represents the roundTrack
     * @param row The number of the row in which is located the dice that needs to be drawn
     * @param col The number of the column in which is located the dice that needs to be drawn
     * @param diceList The list containing all ViewDice objects
     * @param diceInfo The information of the dice (row and column, color and number)
     * @param controller The controller object that has called this method
     */
    public static void drawDiceRoundTrack(GridPane roundTrackGrid, int row, int col, List<ViewDice> diceList, String diceInfo, MatchViewController controller) {
        Node pane = GeneralFunctionalities.getChildrenByIndex(roundTrackGrid, row, col);
        ImageView image = new ImageView("/dice/" + diceInfo.substring(0, 2) + ".png");
        ((AnchorPane) pane).getChildren().add(image);
        GeneralFunctionalities.fitImageToParent(image, roundTrackGrid);

        /*add the WindowDiceEvent to the dice,plus add the dice to viewDiceList*/
        diceList.add(new ViewDice(image, diceInfo.substring(0,2)));
        EventAssigner.addRoundTrackEvent(image,controller);
    }
}

