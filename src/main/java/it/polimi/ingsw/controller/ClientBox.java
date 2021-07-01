package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.ClientInterface;

import java.rmi.RemoteException;

import static java.lang.System.out;


/**
 * This class is used to make invisible, from server side, the problems of the communication. The idea is to have an object
 * that represent the main info of the connected client. This class has a ClientInterface attribute which provide to the real
 * communication.
 */
public class ClientBox {
    private ClientInterface client;
    private String name;
    private static final String CONNECTED="connectedPlayers";
    private static final String MATCH="match ";
    private static final String TIME="timer ";
    private static final String ALERT="alert ";
    private static final String CONNECTIONALERT = "Cannot reach the client";


    ClientBox(ClientInterface c, String n){
        client=c;
        name=n;
    }

    void ping() throws RemoteException {
        client.ping();
    }

    public String getName() {
        return name;
    }

    /**
     * Send the list of connected players.
     * @param finalPlayersIn players' name.
     */
    void updatePlayers(String finalPlayersIn)  {
        try {
            finalPlayersIn = CONNECTED + finalPlayersIn;
            client.updatePlayers(finalPlayersIn);
        }catch (RemoteException e){
            out.println(CONNECTIONALERT);

        }
    }

    /**
     * Sends the number of the match where the player will play.
     * @param numOfMatch match id
     */
    public void setNumMatch(int numOfMatch) {
        try {
            String num = String.valueOf(numOfMatch);
            client.setNumMatch(MATCH + num);
        }catch (RemoteException e){
            out.println(CONNECTIONALERT);
        }
    }

    /**
     * Send the name of the player who has to move.
     * @param s message
     */
    public void updateTurn(String s) {
        try {
            client.updateTurn(s);
        } catch (RemoteException e) {
            out.println(CONNECTIONALERT);

        }
    }

    /**
     * Send an update message
     * @param updateMove message
     */
    public void update(String updateMove) {
        try {
            client.update(updateMove);
        } catch (RemoteException e) {
            out.println(CONNECTIONALERT);
        }
    }

    /**
     * Send the timer.
     * @param tempTime timer.
     */
    public void setTimer(long tempTime) {
        try {
            tempTime = tempTime / 1000;
            String timer = String.valueOf(tempTime);
            client.setTimer(TIME + timer);
        }catch (RemoteException e){
            out.println(CONNECTIONALERT);
        }
    }

    /**
     * Send an alert for a Wrong move.
     * @param s alert message.
     */
    public void wrongMove(String s) {
        try {
            client.updateMessage(ALERT+s);
        } catch (RemoteException e) {
            out.println(CONNECTIONALERT);
        }
    }

    /**
     * Sill send the message for the choose of the pattern card.
     * @param windows all the four pattern card from which choose.
     */
    public void chooseWindow(String windows) {
        try {
            client.updateMessage(windows);
        } catch (RemoteException e) {
            out.println(CONNECTIONALERT);
        }
    }

    public ClientInterface getInterface() {
        return client;
    }
}
