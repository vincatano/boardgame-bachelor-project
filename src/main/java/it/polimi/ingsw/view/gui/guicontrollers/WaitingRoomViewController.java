package it.polimi.ingsw.view.gui.guicontrollers;

import com.jfoenix.controls.JFXListView;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.SceneInterface;
import it.polimi.ingsw.view.gui.GuiHandler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * This is the controller of the WaitingRoom Scene. This class implements Initializable and SceneInterface in order
 * to Override the interface methods.
 */
public class WaitingRoomViewController implements Initializable, SceneInterface {
    private Client client;
    private Stage stage;
    private ObservableList<String> connectedPlayers = FXCollections.observableArrayList();
    private String fixedTime;
    private String tempTime;
    private String choseCardTimer;
    private GuiHandler guiHandler;

    @FXML
    private JFXListView<String> playerListView;

    @FXML
    private Label timerLabel;

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
     * Setter method to store the players that are currently connected to the match, this way they can be displayed.
     * @param connectedPlayers List of players currently connected in the match
     */
    public void setList(ObservableList<String> connectedPlayers) {
        this.connectedPlayers = connectedPlayers;
    }

    /**
     * Set the timer to store the current time in seconds of the waitingRoom timer.
     * @param time String representing the current time in seconds on the timer.
     */
    public void setTime(String time) {
        this.fixedTime = time;
        this.tempTime = time;
    }

    /**
     * Setter method to store the GuiHandler object in order to call methods on it.
     * @param guiHandler The Stage object.
     */
    public void setGuiHandler(GuiHandler guiHandler) {
        this.guiHandler = guiHandler;
    }

    /**
     * Update the ListView in order to show the players currently connected. This method is called by handleConnectedPlayers(), so
     * whenever a player disconnect, connect or reconnect to the match.
     */
    public void loadPlayers() {
        playerListView.setItems(connectedPlayers);
    }

    /**
     * Starts the WaitingRoom timer, every second call the method countTimer()
     */
    public void startTimer() {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> countTimer()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Decrement the timer if there are more than one player currently connected to the match.
     */
    private void countTimer() {
        if(connectedPlayers.size() >= 2 && !tempTime.equals("0")) {
            tempTime = Long.toString((Long.parseLong(tempTime) - 1));
        } else {
            tempTime = fixedTime;
        }

        timerLabel.setText("Sei attualmente in coda. Aspettando ulteriori giocatori...                       Timer : " + tempTime);
    }

    /**
     * Method used to change the WaitingRoom scene to PatternCardChoice scene. Loads the patternCard fxml file, get the patternCardChoice controller
     * and set it into the guiHandler SceneInterface object.
     * @throws IOException If the file can't be loaded this exception will be thrown.
     */
    private void changeScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/patternCardChoiceGui.fxml"));
        Parent root = fxmlLoader.load();

        // Get the Controller from the FXMLLoader
        PatternCardChoiceViewController controller = fxmlLoader.getController();
        // Set data in the controller
        controller.setClient(client);
        controller.setGuiHandler(guiHandler);
        controller.setStage(stage);
        controller.setTime(choseCardTimer);
        controller.setList(connectedPlayers);

        Scene scene = new Scene(root);
        //chiamate a metodi che devono essere eseguiti prima di visualizzare la gui
        controller.startTimer();
        guiHandler.setGui(controller);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Scelta della vetrata");
        stage.show();
    }

    @Override
    public void handleAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(alertMessage);

        alert.showAndWait();
    }

    @Override
    public void handleConnectedPlayers(String playerList) {
        this.connectedPlayers = FXCollections.observableArrayList(Arrays.asList(playerList.split(" ")));
        loadPlayers();
    }

    /**
     * {@inheritDoc}
     * Call the method changeScene().
     */
    @Override
    public void handleTimer(String time) {
        this.choseCardTimer = time;
        try {
            changeScene();
        } catch (IOException e) {
            handleAlert("Errore nel cambio scena, riavviare il gioco.");
        }
    }
}
