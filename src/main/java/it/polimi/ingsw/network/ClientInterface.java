package it.polimi.ingsw.network;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface is used form server to communicate with client, whiteout know the type of communication it uses.
 */
public interface ClientInterface extends Remote {

    /**
     * @return client name.
     * @throws RemoteException if connection falls.
     */
    String getName()throws RemoteException;

    /**
     * This method is used to know if a client is still connected, if it throws an exception, the client is disconnected.
     * @throws RemoteException if connection falls.
     */
    void ping() throws RemoteException;

    /**
     *
     * @return the type of connection used
     * @throws RemoteException if connection falls.
     */
    String getTypeConnection() throws RemoteException;

    /**
     * Send to client an update string
     * @param update update string
     * @throws RemoteException if connection falls.
     */
    void update(String update) throws RemoteException;

    /**
     * Send to client all the client connected to the match
     * @param playersIn all client connected
     * @throws RemoteException if connection falls.
     */
    void updatePlayers(String playersIn) throws RemoteException;

    /**
     * Send name of the player who has to move
     * @param whoIsTurn player's turn
     * @throws RemoteException if connection falls.
     */
    void updateTurn(String whoIsTurn) throws RemoteException;

    /**
     * Send number of match
     * @param num number of match
     * @throws RemoteException if connection falls.
     */
    void setNumMatch(String num) throws RemoteException;

    /**
     * Send generic message
     * @param message message to send
     * @throws RemoteException if connection falls.
     */
    void updateMessage(String message) throws RemoteException;

    /**
     * Send to client the timer
     * @param timer timer to send
     * @throws RemoteException if connection falls.
     */
    void setTimer(String timer)throws RemoteException;
}
