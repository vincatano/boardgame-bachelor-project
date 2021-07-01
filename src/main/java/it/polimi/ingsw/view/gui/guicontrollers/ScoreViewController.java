package it.polimi.ingsw.view.gui.guicontrollers;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.SceneInterface;
import it.polimi.ingsw.view.gui.GuiHandler;
import it.polimi.ingsw.view.gui.data.User;
import it.polimi.ingsw.view.gui.drawers.GeneralFunctionalities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * This is the controller of the ScoreView Scene. This class implements Initializable and SceneInterface in order
 * to Override the interface methods.
 */
public class ScoreViewController implements Initializable, SceneInterface {
    private Client client;
    private Stage stage;
    private ObservableList<String> connectedPlayerList = FXCollections.observableArrayList();
    private String time;
    private GuiHandler guiHandler;
    private ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    private Label announcementLabel;

    @FXML
    private TableView<User> scoreTable;

    @FXML
    private TableColumn<User,String> playerColumn;

    @FXML
    private TableColumn<User,String> scoreColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*no need to initialize something*/
    }

    /**
     * Setter method to store the Client Object in order to call methods on it.
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
     * Setter method to store the GuiHandler object in order to call methods on it.
     * @param guiHandler The Stage object.
     */
    public void setGuiHandler(GuiHandler guiHandler) {
        this.guiHandler = guiHandler;
    }

    /**
     * Load on the TableView all the players of the match and their scores. Also calls displays the winner of the game which is the
     * first user inside the list.
     * @param playerScore List containing all the player's username and their score.
     */
    public void loadScores(String playerScore) {
        ObservableList<String> scoreList = FXCollections.observableArrayList(Arrays.asList(playerScore.split(",")));
        String username;
        String score;

        for(int i = 0; i < scoreList.size();i++) {
            ObservableList<String> currentPlayerScore = FXCollections.observableArrayList(Arrays.asList(scoreList.get(i).split(" ")));
            username = currentPlayerScore.get(0);
            score = currentPlayerScore.get(1);
            User user = new User(username);
            user.setScore(score);
            userList.add(user);
            if(i == 0) {
                announcementLabel.setText(username+" ha vinto la partita!");
            }
        }

        playerColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreTable.setItems(userList);
    }

    /**
     * Method called when the player click on the playAgain button. Call sendCommand method on a client object to communicate
     * to the server that the player wants to play again.
     */
    @FXML
    public void handleMouseClicked() {
        client.sendCommand("playAgain "+client.getName());
    }

    /**
     * Method used to change the ScoreView scene to WaitingRoom scene. Loads the WaitingRoom fxml file, get the WaitingRoom controller
     * and set it into the guiHandler SceneInterface object.
     * @throws IOException If the file can't be loaded this exception will be thrown.
     */
    private void changeScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/waitingRoomGui.fxml"));
        Parent root = fxmlLoader.load();

        // Get the Controller from the FXMLLoader
        WaitingRoomViewController controller = fxmlLoader.getController();
        // Set data in the controller
        controller.setClient(client);
        controller.setList(connectedPlayerList);
        controller.setTime(time);
        controller.setStage(stage);
        controller.setGuiHandler(guiHandler);

        Scene scene = new Scene(root);
        controller.loadPlayers();
        controller.startTimer();
        guiHandler.setGui(controller);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Sala d'attesa");
        stage.show();
    }

    @Override
    public void handleAlert(String alertMessage) {
        GeneralFunctionalities.displayAlertWindow(alertMessage);
    }

    @Override
    public void handleConnectedPlayers(String connectedPlayers) {
        this.connectedPlayerList = FXCollections.observableArrayList(Arrays.asList(connectedPlayers.split(" ")));
    }

    /**
     * {@inheritDoc}
     * Call the method changeScene()
     */
    @Override
    public void handleTimer(String time) {
        this.time = time;
        try {
            changeScene();
        } catch (IOException e) {
            handleAlert("Errore nel cambio scena, riavviare il gioco.");
        }
    }
}
