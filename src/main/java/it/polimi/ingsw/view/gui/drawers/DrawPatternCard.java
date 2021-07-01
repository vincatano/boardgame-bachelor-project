package it.polimi.ingsw.view.gui.drawers;

import it.polimi.ingsw.view.gui.data.ViewDice;
import it.polimi.ingsw.view.gui.guicontrollers.MatchViewController;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.List;

/**
 * Class that contains all the methods that can be used to draw the GUI PatterCards.
 * This static class is used like a tool that offers some functionalities.
 */
public final class DrawPatternCard {

    /**
     * Private constructor to prevent the instantiation of any DrawPatternCard object
     */
    private DrawPatternCard() {

    }

    /**
     * This method is used to draw the dice contained a given player's patternCard cell.
     * @param grid The GridPane that represents the player's patternCard
     * @param row The number of the row in which is located the dice that needs to be drawn
     * @param col The number of the column in which is located the dice that needs to be drawn
     * @param isMyWindow A boolean that states if the dice that need to be drawn is a dice contained in the patternCard of the client.
     *                   This way it's known whether to add the assign the windowEvent to the dice.
     * @param diceList The list containing all ViewDice objects
     * @param diceInfo The information of the dice (row and column, color and number)
     * @param controller The controller object that has called this method
     */
    public static void drawDicePatternCard(GridPane grid, int row, int col, boolean isMyWindow, List<ViewDice> diceList, String diceInfo, MatchViewController controller) {
        Node pane = GeneralFunctionalities.getChildrenByIndex(grid,row,col);
        ImageView image = new ImageView("/dice/" + diceInfo.substring(0,2) + ".png");
        ((AnchorPane)pane).getChildren().add(image);
        GeneralFunctionalities.fitImageToParent(image,grid);

        /*If the current window is the player's one then add the WindowDiceEvent to the dice,plus add the dice to viewDiceList*/
        if(isMyWindow) {
            diceList.add(new ViewDice(image,diceInfo.substring(0,2)));
            EventAssigner.addWindowEvent(image,controller);
        }
    }

    /**
     * Method used to draw restrictions on a given cell of a patternCard.
     * @param restriction String containing all the information about restrictions (index of the cell, color and number)
     * @param anchorPane The anchor pane that needs to be drawn
     */
    public static void drawRestrictions(String restriction, Node anchorPane) {
        char colorRestriction = restriction.charAt(0);
        char shadeRestriction = restriction.charAt(1);

        if(colorRestriction == 'P') {
            anchorPane.setStyle("-fx-background-color: purple");
        } else if(colorRestriction == 'R') {
            anchorPane.setStyle("-fx-background-color: red");
        } else if(colorRestriction == 'B') {
            anchorPane.setStyle("-fx-background-color: blue");
        } else if(colorRestriction == 'Y') {
            anchorPane.setStyle("-fx-background-color: yellow");
        } else if(colorRestriction == 'G') {
            anchorPane.setStyle("-fx-background-color: green");
        }

        if(shadeRestriction!= '0') {
            anchorPane.setStyle("-fx-background-image: url('/restrictions/"+shadeRestriction+".png');"+
                    "-fx-background-size: contain;"+
                    "-fx-background-repeat: no-repeat;"+
                    "-fx-background-position: center center;");
        }
    }

    /**
     * Method used to draw FavTokens of a given player. Also fit them to the gridPane cells
     * @param tokenList List containing AnchorPanes on which ImageView need to be added
     * @param favorsList The list containing the player's name and the number of it's tokens.
     * @param favorGrid The gridPane that contains the given player's tokens
     */
    public static void drawFavTokens(ObservableList<Node> tokenList,List<String> favorsList, GridPane favorGrid) {
        for(int i = 0; i < tokenList.size();i++) {
            if(i < Integer.parseInt(favorsList.get(1))) {
                ImageView favimg = new ImageView("/viewLogo/favortoken.png");
                ((AnchorPane)tokenList.get(i)).getChildren().add(favimg);
                GeneralFunctionalities.fitImageToParent(favimg,favorGrid);
                favimg.setPreserveRatio(true);
            }
        }
    }

    /**
     * Method used to remove all the dice that are already drawn. This method is called before drawFavTokens.
     * @param tokenList List containing AnchorPanes on which ImageView need to be added
     */
    public static void removeFavTokens(ObservableList<Node> tokenList) {
        for(int i = 0; i < tokenList.size();i++) {
            AnchorPane pane = (AnchorPane)tokenList.get(i);
            if(pane.getChildren().size() > 0) {
                pane.getChildren().remove(0);
            }
        }
    }
}
