package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.gui.guicontrollers.LoginViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This static class extends Application and it's used to start the GUI
 */
public class GuiView extends Application {
    private static Client userClient;
    private static GuiHandler guiHandler;

    /**
     * Empty constructor
     */
    public GuiView() {
        /*no need to create an instance of this class*/
    }

    /**
     * Setter method, used to save up the Client object for it's needed also in further GUI scenes.
     * @param client Reference to the Client object
     */
    public static void setClient(Client client) {
        userClient = client;
    }

    /**
     * Setter method, used to save up the GuiHandler object for it's needed also in further GUI scenes.
     * @param handler Reference to the GuiHandler object
     */
    public static void setGuiHandler(GuiHandler handler) {
        guiHandler = handler;
    }

    /**
     * {@inheritDoc}
     *
     * This method creates the main stage and launches the Login scene.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/loginGui.fxml"));
        Parent root = fxmlLoader.load();

        // Get the Controller from the FXMLLoader
        LoginViewController controller = fxmlLoader.getController();
        // Set data in the controller
        controller.setClient(userClient);
        controller.setGuiHandler(guiHandler);
        controller.setStage(primaryStage);

        Scene scene = new Scene(root);
        guiHandler.setGui(controller);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Sagrada");

        primaryStage.setOnCloseRequest(event -> {
            //If the numOfMatch of the client isn't the default one
            if(userClient.getNumOfMatch() >= 0) {
                userClient.sendCommand("disconnect " + userClient.getNumOfMatch() + " " + userClient.getName());
            }
            System.exit(0);
        });

        primaryStage.show();
    }


    /**
     * This method call the launch() method, which manage to call the Start method
     */
    public static void launching() {
        launch();
    }
}
