package it.polimi.ingsw.view.gui.drawers;

import it.polimi.ingsw.view.gui.data.ViewDice;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.List;

/**
 * Class that contains all the utilities methods for the GUI.
 * This static class is used like a tool that offers some functionalities.
 */
public final class GeneralFunctionalities {

    /**
     * Private constructor to prevent the instantiation of any GeneralFunctionalities object
     */
    private GeneralFunctionalities() {

    }

    /**
     * Method used to get the Anchor pane contained in a given GridPane cell, knowing the column and the row in which the AnchorPane is located.
     * @param gridPane The GridPane that contains the AnchorPane we want to get.
     * @param row The row number of the cell that contains the AnchorPane
     * @param col The column number of the cell that contains the AnchorPane
     * @return the AnchorPane object
     */
    public static Node getChildrenByIndex(GridPane gridPane, final int row, final int col) {
        ObservableList<Node> children = gridPane.getChildren();

        for (Node pane: children) {
            if(GridPane.getColumnIndex(pane) == col && GridPane.getRowIndex(pane) == row) {
                return pane;
            }
        }

        throw new IllegalStateException();
    }

    /**
     * Delete the ImageView in the given cell, also it deletes the reference in the diceList
     * @param pane The pane that contains the ImageView that needs to be deleted.
     * @param diceList @param diceList The list containing all ViewDice objects
     */
    public static void deleteDice(AnchorPane pane, List<ViewDice> diceList) {
        ObservableList<Node> children = pane.getChildren();
        if(!children.isEmpty()) {
            ViewDice dice = findDiceInfo(children.get(0),diceList);
            diceList.remove(dice);
            children.clear();
        }
    }

    /**
     * Delete the ImageView in the given cell
     * @param pane The pane that contains the ImageView that needs to be deleted.
     */
    private static void deleteOnlyDiceImage(AnchorPane pane) {
        ObservableList<Node> children = pane.getChildren();
        children.clear();
    }

    /**
     * Method used to get the information of a dice that is contained into the diceList.
     * @param node Node representing the imageView of the given dice
     * @param diceList The list containing all ViewDice objects
     * @return ViewDice object that represent the dice that has been given as parameter
     */
    public static ViewDice findDiceInfo(Node node,List<ViewDice> diceList) {
        for (ViewDice dice : diceList) {
            if(dice.getDiceImage() == node) {
                return dice;
            }
        }

        throw new IllegalStateException();
    }

    /**
     * Method used to fit an ImageView Object to a given AnchorPane
     * @param image The ImageView object that needs to be fitted
     * @param pane The AnchorPane to which the image need to be fitted
     */
    public static void fitImageToParent(ImageView image, AnchorPane pane) {
        image.fitWidthProperty().bind(pane.widthProperty());
        image.fitHeightProperty().bind(pane.heightProperty());
    }

    /**
     * Method used to fit an ImageView Object to a given GridPane
     * @param image The ImageView object that needs to be fitted
     * @param grid The GridPane to which the image need to be fitted
     */
    public static void fitImageToParent(ImageView image, GridPane grid) {
        image.fitWidthProperty().bind(grid.widthProperty().divide(grid.getColumnConstraints().size()));
        image.fitHeightProperty().bind(grid.heightProperty().divide(grid.getRowConstraints().size()));
    }

    /**
     * Method used to remove all dices that has been drawn in a given GridPane, if the GridPane it's the player WindowPatternCard
     * or if it's the DraftPool also deletes the ViewDice object related to the dice deleted.
     * @param grid The GridPane that contains the dice that need to be romeved
     * @param diceList The list containing all ViewDice objects
     * @param viewDiceToBeDeleted Boolean value that states is the gridPane is the player WindowPatternCard or the Draftpool rather than
     *                            another GridPane (use this value to decide whether to delete the viewDice object as well).
     */
    public static void removeDiceFromGrid(GridPane grid, List<ViewDice> diceList, boolean viewDiceToBeDeleted) {
        int numRows = grid.getRowConstraints().size();
        int numColumns = grid.getColumnConstraints().size();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if(viewDiceToBeDeleted) {
                    GeneralFunctionalities.deleteDice((AnchorPane)grid.getChildren().get(numColumns*i+j), diceList);
                } else {
                    GeneralFunctionalities.deleteOnlyDiceImage((AnchorPane)grid.getChildren().get(numColumns*i+j));
                }
            }
        }
    }

    /**
     * Due to the fact that every viewController have the same implementation of this method, it's a good choice to make it available
     * in this static class.
     * @param alertMessage Contains the alert message that needs to be shown to the user.
     */
    public static void displayAlertWindow(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(alertMessage);

        alert.showAndWait();
    }
}
