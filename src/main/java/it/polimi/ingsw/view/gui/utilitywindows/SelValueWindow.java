package it.polimi.ingsw.view.gui.utilitywindows;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.gui.data.ViewDice;
import it.polimi.ingsw.view.gui.drawers.GeneralFunctionalities;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Static class that defines a window screen that let the player select the value of a Dice.
 */
public class SelValueWindow {
    private static final int DRAFT_ROWS = 3;
    private static final int NUM_ROWS = 0;
    private static final int NUM_COL = 6;
    private static final String SELVALUE = "SelectingValue";
    private static final String ALERT_DICE_SEL_VALUE = "Non puoi scegliere il valore di un dado in questa fase del turno.";
    private static Stage stage;
    private static final double OFFSET = 10;

    /**
     * Private constructor preventing instantiation of SelValueWindow objects.
     */
    private SelValueWindow() {

    }

    /**
     * Method used in order to display the SelValueWindow screen. In this window there will be six dices. These colour of these dices
     * will be the same color of the dice previously chosen by the player, while the number will increase from one to 6. Assigning events
     * on these six dices, the player will be able to select the value of the dice.
     * @param client Client object
     * @param dice ViewDice object representing the dice previously chosen by the player
     * @param diceChosenRow The row of the dice previously chosen by the player
     * @param diceChosenColumn The column of the dice previously chosen by the player
     * @param currentState The current state of the turn
     */
    public static void display(Client client, ViewDice dice, int diceChosenRow, int diceChosenColumn, String currentState) {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Seleziona il valore del dado");

        AnchorPane anchorPane = new AnchorPane();
        GridPane gridPane = new GridPane();

        for(int i = 0; i < NUM_COL;i++) {
            ImageView image = new ImageView("/dice/"+dice.getDiceColor()+(i+1)+".png");
            GridPane.setConstraints(image,i,NUM_ROWS);
            image.fitHeightProperty().bind(gridPane.heightProperty());
            image.fitWidthProperty().bind(gridPane.widthProperty().divide(NUM_COL));
            gridPane.getChildren().add(image);
            image.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent mouseEvent) -> handleValueChosen(mouseEvent,currentState,client,dice,diceChosenRow,diceChosenColumn));
        }

        anchorPane.getChildren().add(gridPane);

        AnchorPane.setTopAnchor(gridPane,OFFSET);
        AnchorPane.setLeftAnchor(gridPane,OFFSET);
        AnchorPane.setBottomAnchor(gridPane,OFFSET);
        AnchorPane.setRightAnchor(gridPane,OFFSET);

        Scene scene = new Scene(anchorPane,600,100);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * Called when the player chose the value of the dice.
     * @param event MouseEvent object used to get the dice the player has chosen.
     * @param currentState The current state of the turn
     * @param client Client object
     * @param dice ViewDice object representing the dice previously chosen by the player
     * @param diceChosenRow The row of the dice previously chosen by the player
     * @param diceChosenColumn The column of the dice previously chosen by the player
     */
    private static void handleValueChosen(MouseEvent event,String currentState, Client client,ViewDice dice,int diceChosenRow,int diceChosenColumn) {
        if(currentState.equals(SELVALUE)) {
            Node source = (Node) event.getSource();
            int x = DRAFT_ROWS*diceChosenRow+diceChosenColumn;
            client.sendCommand("move " + client.getNumOfMatch() + " " + client.getName() + " D;" + (GridPane.getColumnIndex(source)+1) + "," + dice.getDiceColor() + "," + x + "," + 0);
            stage.close();
        } else {
            GeneralFunctionalities.displayAlertWindow(ALERT_DICE_SEL_VALUE);
        }
        event.consume();
    }
}
