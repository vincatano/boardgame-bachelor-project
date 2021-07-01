package it.polimi.ingsw.view.gui.guicontrollers;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.SceneInterface;
import it.polimi.ingsw.view.gui.drawers.DrawPatternCard;
import it.polimi.ingsw.view.gui.GuiHandler;
import it.polimi.ingsw.view.gui.drawers.GeneralFunctionalities;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This is the controller of the PatternCardChoice Scene. This class implements Initializable and SceneInterface in order
 * to Override the interface methods.
 */
public class PatternCardChoiceViewController implements Initializable, SceneInterface {
    private Client client;
    private Stage stage;
    private String cardChoiceTimer;
    private String matchTimer;
    private GuiHandler guiHandler;
    private String setup;
    private List<String> givenPatternCards = new ArrayList<>();
    private ObservableList<String> connectedPlayers;
    private boolean isChosen = false;
    private String idMatch;

    @FXML
    private Label timerLabel;

    @FXML
    private GridPane patternCardGrid;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*no need to initialize something*/
    }

    /**
     * Setter method to store the Client object in order to call methods on it.
     * @param client The Client object.
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Setter method to store the Stage object in order to call methods on it.
     * @param stage The Stage object.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Set the timer to store the current time in seconds of the PatternCardChoice timer.
     * @param time String representing the current time in seconds on the timer.
     */
    public void setTime(String time) {
        this.cardChoiceTimer = time;
    }

    /**
     * Setter method to store the GuiHandler object in order to call methods on it.
     * @param guiHandler The Stage object.
     */
    public void setGuiHandler(GuiHandler guiHandler) {
        this.guiHandler = guiHandler;
    }

    /**
     * Setter method to store the players that are currently connected to the match, this way they can be displayed.
     * @param connectedPlayers List of players currently connected in the match
     */
    public void setList(ObservableList<String> connectedPlayers) {
        this.connectedPlayers = connectedPlayers;
    }

    /**
     * Starts the PatternCardChoice timer, every second call the method countTimer()
     */
    public void startTimer() {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> countTimer()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Decrement the timer if it's different from zero.
     */
    private void countTimer() {
        if(!cardChoiceTimer.equals("0")) {
            cardChoiceTimer = Long.toString((Long.parseLong(cardChoiceTimer) - 1));
        } else {
            cardChoiceTimer = "0";
        }

        timerLabel.setText("Timer : " + cardChoiceTimer);
    }

    /**
     * Method called as soon as a PatternCard is chose. Calls the method sendCommand() on the client in order to communicate to the server
     * which card the player has chosen.
     * @param event MouseEvent object used to get the ToolCard the player clicked
     */
    @FXML
    public void handleCardChoice(MouseEvent event) {
        if (!isChosen) {
            Node source = (Node) event.getSource();
            source.setEffect(new DropShadow());
            int col = GridPane.getColumnIndex(source);
            int row = GridPane.getRowIndex(source);
            String patternCardToSend = givenPatternCards.get(2 * row + col);
            client.sendCommand("chooseCard "+idMatch+" "+client.getName()+" "+patternCardToSend);
            isChosen = true;
        }
    }

    /**
     * Method used to change the PatternCardChoice scene to Match scene. Loads the Match fxml file, get the Match controller
     * and set it into the guiHandler SceneInterface object.
     * @throws IOException If the file can't be loaded this exception will be thrown.
     */
    private void changeScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/matchGui.fxml"));
        Parent root = fxmlLoader.load();

        // Get the Controller from the FXMLLoader
        MatchViewController controller = fxmlLoader.getController();
        // Set data in the controller
        controller.setClient(client);
        controller.setGuiHandler(guiHandler);
        controller.setStage(stage);
        controller.setTime(matchTimer);
        controller.setList(connectedPlayers);

        Scene scene = new Scene(root);
        //chiamate a metodi che devono essere eseguiti prima di visualizzare la gui
        controller.startTimer();
        controller.updateBoard(setup);
        controller.updateStatusTable();
        guiHandler.setGui(controller);

        stage.setScene(scene);
        stage.setResizable(true);
        stage.setTitle("Partita");
        stage.show();
    }

    @Override
    public void handleAlert(String alertMessage) {
        GeneralFunctionalities.displayAlertWindow(alertMessage);
    }


    /**
     * {@inheritDoc}
     * Call the method changeScene()
     */
    @Override
    public void handleTimer(String time) {
        this.matchTimer = time;
        try {
            changeScene();
        } catch (IOException e) {
            handleAlert("Errore nel cambio scena, riavviare il gioco.");
        }
    }

    @Override
    public void handleMatchId(String idMatch) {
        this.idMatch = idMatch;
        client.setNumMatch(Integer.parseInt(idMatch));
    }

    @Override
    public void handleConnectedPlayers(String playerList) {
        this.connectedPlayers = FXCollections.observableArrayList(Arrays.asList(playerList.split(" ")));
    }

    /**
     * {@inheritDoc}
     * In this case the only thing this method does is storing the setup message in order to pass it to the MatchGuiController when the scene
     * will be changed
     */
    @Override
    public void updateBoard(String setup) {
        this.setup = setup;
    }

    @Override
    public void setPatternCards(String windows) {
        ObservableList<Node> children = patternCardGrid.getChildren();
        List<String> patternCardList = Arrays.asList(windows.split(";"));

        for(int k = 0; k < patternCardList.size();k++) {
            List<String> substring = Arrays.asList(patternCardList.get(k).split(" "));

            givenPatternCards.add(substring.get(0));
            List<String> restrictionList = Arrays.asList(substring.get(2).split(","));

            GridPane gridContainer = (GridPane)children.get(k);
            ObservableList<Node> containerChildren = gridContainer.getChildren();

            GridPane currentWindow = (GridPane)(containerChildren.get(0));
            GridPane favorGrid = (GridPane) (containerChildren.get(1));

            ObservableList<Node> tokenList = favorGrid.getChildren();

            /*redraw all favor tokens*/
            DrawPatternCard.removeFavTokens(tokenList);
            DrawPatternCard.drawFavTokens(tokenList,substring,favorGrid);

            int numColumns = currentWindow.getColumnConstraints().size();
            int numRows = currentWindow.getRowConstraints().size();
            for(int i = 0; i < numRows; i++) {
                for(int j = 0; j < numColumns; j++) {
                    DrawPatternCard.drawRestrictions(restrictionList.get(numColumns*i+j) , currentWindow.getChildren().get(numColumns*i+j));
                }
            }
        }
    }
}
