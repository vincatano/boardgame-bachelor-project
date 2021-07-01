package it.polimi.ingsw.network.client;


/**
 * This interface is used to abstracting from the kind of connection the user choose.
 */
public interface ConnectionHandler {
    /**
     * Send a connection command to server
     */
     void connect();

    /**
     * Send a command to server
     * @param cmd command to send
     */
     void sendCommand(String cmd);
}
