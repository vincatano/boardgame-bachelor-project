package it.polimi.ingsw.view.gui.utilitywindows;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.gui.data.ViewDice;
import it.polimi.ingsw.view.gui.drawers.GeneralFunctionalities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

/**
 * Static class that defines a window screen that let the player increment or decrement the value of a Dice.
 */
public class IncDecWindow {
    private static final int DRAFT_ROWS = 3;
    private static final int NUM_ROWS = 0;
    private static final int NUM_COL = 2;
    private static final String INCDECVALUE = "IncDecValue";
    private static final String ALERT_STATE_NOT_VALID = "Non puoi aumentare/diminuire il valore di un dado in questa fase del turno.";
    private static final String ALERT_DICE_INC_DEC_CHOICE_NOT_VALID = "Mossa sbagliata : non puoi cambiare un 1 in un 6 o un 6 in un 1.";
    private static Stage stage;
    private static final double OFFSET = 10;
    private static int valueToChange;

    /**
     * Private constructor preventing instantiation of IncDecWindow objects.
     */
    private IncDecWindow() {

    }

    /**
     * Method used in order to display the IncDecWindow screen. In this window there will be two dice. The first one will be the same Dice
     * previously chosen by the player decremented by one in value and the second one will be incremented by one in value. Assigning events
     * on these two dices, the player will be able to increment or decrement the value of the dice.
     * @param client Client object
     * @param dice ViewDice object representing the dice previously chosen by the player
     * @param diceChosenRow The row of the dice previously chosen by the player
     * @param diceChosenColumn The column of the dice previously chosen by the player
     * @param currentState The current state of the turn
     */
    public static void display(Client client, ViewDice dice, int diceChosenRow, int diceChosenColumn, String currentState) {
        ObservableList<ImageView> diceList = FXCollections.observableArrayList();
        List<ViewDice> viewDiceList = new ArrayList<>();
        int valueDecreased;
        int valueIncreased;
        valueToChange = Character.getNumericValue(dice.getDiceNumber());

        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Aumenta o diminuisci il valore del dado");

        AnchorPane anchorPane = new AnchorPane();
        GridPane gridPane = new GridPane();

        if(valueToChange == 6) {
            valueDecreased = valueToChange-1;
            valueIncreased = 1;
        } else if(valueToChange == 1) {
            valueDecreased = 6;
            valueIncreased = valueToChange+1;

        } else {
            valueDecreased = valueToChange-1;
            valueIncreased = valueToChange+1;
        }

        String infoDiceDecreased = dice.getDiceColor()+""+valueDecreased;
        String infoDiceIncreased = dice.getDiceColor()+""+valueIncreased;

        ImageView imageDecrease = new ImageView("/dice/"+infoDiceDecreased+".png");
        ImageView imageIncrease = new ImageView("/dice/"+infoDiceIncreased+".png");
        diceList.addAll(imageDecrease,imageIncrease);
        viewDiceList.add(new ViewDice(imageDecrease,infoDiceDecreased));
        viewDiceList.add(new ViewDice(imageIncrease,infoDiceIncreased));

        for(int i = 0; i < diceList.size();i++) {
            addToGrid(gridPane,diceList.get(i),i);
            diceList.get(i).addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent mouseEvent) -> handleValueChosen(mouseEvent,currentState,client,viewDiceList,diceChosenRow,diceChosenColumn));
        }

        anchorPane.getChildren().add(gridPane);

        AnchorPane.setTopAnchor(gridPane,OFFSET);
        AnchorPane.setLeftAnchor(gridPane,OFFSET);
        AnchorPane.setBottomAnchor(gridPane,OFFSET);
        AnchorPane.setRightAnchor(gridPane,OFFSET);

        Scene scene = new Scene(anchorPane,200,100);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * Called when the player chose to increment or decrement the value of the dice.
     * @param event MouseEvent object used to get the dice the player has chosen.
     * @param currentState The current state of the turn
     * @param client Client object
     * @param viewDiceList The list of ViewDice
     * @param diceChosenRow The row of the dice previously chosen by the player
     * @param diceChosenColumn The column of the dice previously chosen by the player
     */
    private static void handleValueChosen(MouseEvent event,String currentState, Client client, List<ViewDice> viewDiceList, int diceChosenRow, int diceChosenColumn) {
        if(currentState.equals(INCDECVALUE)) {
            Node source = (Node) event.getSource();
            int diceColumn = GridPane.getColumnIndex(source);
            ViewDice chosenDice = GeneralFunctionalities.findDiceInfo(source, viewDiceList);

            if (!((valueToChange == 1 && diceColumn == 0) || (valueToChange == 6 && diceColumn == 1))) {
                int x = DRAFT_ROWS*diceChosenRow+diceChosenColumn;
                client.sendCommand("move " + client.getNumOfMatch() + " " + client.getName() + " D;" + chosenDice.getDiceNumber() + "," + chosenDice.getDiceColor() + "," + x + "," + 0);
                stage.close();
            } else {
                GeneralFunctionalities.displayAlertWindow(ALERT_DICE_INC_DEC_CHOICE_NOT_VALID);
            }
        } else {
            GeneralFunctionalities.displayAlertWindow(ALERT_STATE_NOT_VALID);
        }

        event.consume();
    }

    /**
     * Add to the grid the two dices.
     * @param gridPane GridPane object
     * @param image ImageView of the dice to add.
     * @param index Index of the cell to which the dice needs to be added.
     */
    private static void addToGrid(GridPane gridPane, ImageView image, int index) {
        GridPane.setConstraints(image,index,NUM_ROWS);
        image.fitHeightProperty().bind(gridPane.heightProperty());
        image.fitWidthProperty().bind(gridPane.widthProperty().divide(NUM_COL));
        gridPane.getChildren().add(image);
    }
}
