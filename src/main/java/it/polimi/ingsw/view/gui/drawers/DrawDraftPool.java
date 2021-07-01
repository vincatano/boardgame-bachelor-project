package it.polimi.ingsw.view.gui.drawers;

import it.polimi.ingsw.view.gui.data.ViewDice;
import it.polimi.ingsw.view.gui.guicontrollers.MatchViewController;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.List;

/**
 * Class that contains all the methods that can be used to draw the GUI DraftPool.
 * This static class is used like a tool that offers some functionalities.
 */
public final class DrawDraftPool {

    /**
     * Private constructor to prevent the instantiation of any DrawDraftPool object
     */
    private DrawDraftPool() {

    }

    /**
     * Method used to draw all the dice inside the draftPool. Deletes the dice that is already drawn in a cell (AnchorPane) and then add
     * the new ImageView object, add the current dice inside diceList due to the dice can be selected by the user. Also fit the image
     * to the GridPane cell and assign the draftEvent to the ImageView.
     * @param draftPool The GridPane that represents the draftPool
     * @param index The index of the cell where the dice needs to be drawn
     * @param draftList The list containing information about all the dice that needs to be drawn inside the draftPool
     * @param diceList The list containing all ViewDice objects
     * @param controller The controller object that has called this method
     */
    public static void drawDiceIntoDraft(GridPane draftPool, int index, List<String> draftList, List<ViewDice> diceList, MatchViewController controller) {
        AnchorPane pane = ((AnchorPane)(draftPool.getChildren().get(index)));
        GeneralFunctionalities.deleteDice(pane,diceList);
        if(index < draftList.size()) {
            ImageView image = new ImageView("/dice/" + draftList.get(index) + ".png");
            pane.getChildren().add(image);
            diceList.add(new ViewDice(image,draftList.get(index)));
            GeneralFunctionalities.fitImageToParent(image,draftPool);

            EventAssigner.addDraftEvent(image,controller);
        }
    }
}
