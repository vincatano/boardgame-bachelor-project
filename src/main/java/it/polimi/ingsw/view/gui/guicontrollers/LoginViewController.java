package it.polimi.ingsw.view.gui.guicontrollers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.SceneInterface;
import it.polimi.ingsw.view.gui.GuiHandler;
import it.polimi.ingsw.view.gui.drawers.GeneralFunctionalities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This is the controller of the first Scene, more precisely the LoginScene. This class implements Initializable and SceneInterface in order
 * to Override the interface methods.
 */
public class LoginViewController implements Initializable, SceneInterface {
    private static final int SOCKET = 1;
    private static final int RMI = 2;
    private static final String EMPTY = "";
    private static final String BLANK = " ";
    private static final String COMMA = ",";
    private static final String SEMICOLON = ";";
    private static final String SLASH = "/";
    private static final String BACKSLASH = "\\";
    private List<String> invalidStrings = new ArrayList<>();
    private Client client;
    private String  username;
    private int connectionType = SOCKET;
    private Stage stage;
    private GuiHandler guiHandler;
    private ObservableList<String> connectedPlayerList = FXCollections.observableArrayList();
    private String time;

    @FXML
    private JFXTextField usernameField;
    @FXML
    private JFXCheckBox socketBox;
    @FXML
    private JFXCheckBox rmiBox;

    /**
     * {@inheritDoc}
     * Initialize the list "invalidStrings" to a set of strings that the username must not contain.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        invalidStrings.add(BLANK);
        invalidStrings.add(COMMA);
        invalidStrings.add(SEMICOLON);
        invalidStrings.add(SLASH);
        invalidStrings.add(BACKSLASH);
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
     * Setter method to store the GuiHandler object in order to call methods on it.
     * @param guiHandler The Stage object.
     */
    public void setGuiHandler(GuiHandler guiHandler) {
        this.guiHandler = guiHandler;
    }


    /**
     * Method called when the user click on the loginButton, check if the username is valid, if it's valid then setConnection() method
     * will be called, on the other hand if it's not valid an alert window will be displayed.
     */
    @FXML
    public void handleMouseClicked() {
        username = usernameField.getText();
        checkUsernameValidity(username);
    }

    /**
     * Method called when the user enter space KeyButton, check if the username is valid, if it's valid then setConnection() method
     * will be called, on the other hand if it's not valid an alert window will be displayed.
     */
    @FXML
    public void handleEnterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            username = usernameField.getText();
            checkUsernameValidity(username);
        }
    }

    /**
     * Method called when the user chose to use socket, set the connectionType to SOCKET.
     */
    @FXML
    public void handleSocketBox() {
        if(socketBox.isSelected()) {
            rmiBox.setSelected(false);
            connectionType = SOCKET;
        }
    }

    /**
     * Method called when the user chose to use rmi, set the connectionType to RMI.
     */
    @FXML
    public void handleRmiBox() {
        if(rmiBox.isSelected()) {
            socketBox.setSelected(false);
            connectionType = RMI;
        }
    }

    /**
     * Set the username and the connectionType on the Client object and call method connect() on it.
     */
    private void setConnection() {
        client.setName(username);
        client.setKindConnection(connectionType);
        client.connect();
    }

    private void checkUsernameValidity(String username) {
        boolean invalidStringContained = false;

        for (String string: invalidStrings) {
            if(username.contains(string)) {
                invalidStringContained = true;
            }
        }

        if(username.equals(EMPTY) || invalidStringContained) {
            handleAlert("Nome utente non valido: il nome utente non deve essere vuoto e non deve contenere i seguenti caratteri: [spazi] [;] [,] [/] [\\].");
        } else {
            setConnection();
        }
    }

    /**
     * Method used to change the Login scene to WaitingRoom scene. Loads the waitingRoom fxml file, get the waitingRoom controller
     * and set it into the guiHandler SceneInterface object.
     * @throws IOException If the file can't be loaded this exception will be thrown.
     */
    private void changeSceneToWaitingRoom() throws IOException {
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

    /**
     * Method used to change the Login scene to Match scene. Loads the match fxml file, get the match controller
     * and set it into the guiHandler SceneInterface object.
     * @throws IOException If the file can't be loaded this exception will be thrown.
     */
    private void changeSceneToMatch() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/matchGui.fxml"));
        Parent root = fxmlLoader.load();

        // Get the Controller from the FXMLLoader
        MatchViewController controller = fxmlLoader.getController();
        // Set data in the controller
        controller.setClient(client);
        controller.setGuiHandler(guiHandler);
        controller.setStage(stage);

        if(time != null) {
            controller.setTime(time);
        } else {
            controller.setTime("0");
        }

        Scene scene = new Scene(root);
        // Calling methods that need to be executed before showing the gui
        controller.startTimer();
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
     * Call the method changeSceneToWaitingRoom().
     */
    @Override
    public void handleConnectedPlayers(String list) {
        this.connectedPlayerList = FXCollections.observableArrayList(Arrays.asList(list.split(" ")));
        try {
            changeSceneToWaitingRoom();
        } catch (IOException e) {
            handleAlert("Errore nel cambio scena, riavviare il gioco.");
        }
    }

    @Override
    public void handleTimer(String time) {
        this.time = time;
    }

    /**
     * {@inheritDoc}
     * Call the method changeSceneToMatch().
     */
    @Override
    public void handleMatchId(String idMatch) {
        client.setNumMatch(Integer.parseInt(idMatch));
        try {
            changeSceneToMatch();
        } catch (IOException e) {
            handleAlert("Errore nel cambio scena, riavviare il gioco.");
        }
    }
}
