package it.polimi.ingsw.network;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface is used by client to connect to server via RMI
 */
public interface ServerInterface extends Remote {

    /**
     *
     * @param name name client that wants to connect
     * @param client client interface
     * @return false if there is a client already connected with same name
     * @throws RemoteException if connection not available
     */
    boolean login(String name,ClientInterface client) throws RemoteException;

    /**
     * Send command to server
     * @param cmd command to send
     * @throws RemoteException if connection not available
     */
    void command(String cmd) throws RemoteException;

    /**
     * If client wants to exit from game
     * @param name name client who wants to exit
     * @param client client interface
     * @throws RemoteException if connection not available
     */
    void disconnect(String name, ClientInterface client)throws RemoteException;
}
