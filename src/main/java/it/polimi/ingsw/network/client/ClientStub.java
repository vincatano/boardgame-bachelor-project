package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.ClientInterface;

/**
 * This class is used when the client choose an rmi connection. When he try to log in to server, passes this object like the reference of itself
 * for the communication
 */
public class ClientStub implements ClientInterface {
    private String name;
    private MessageQueue queue;

    ClientStub(MessageQueue q, String n) {
        queue = q;
        name=n;

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void ping() {
        //this is used to verify if the rmi connection is alive
    }

    @Override
    public String getTypeConnection(){
        return "(RMI)";
    }

    @Override
    public void update(String updateMove){
        queue.add(updateMove);
    }


    @Override
    public void updatePlayers(String playersIn){
        queue.add(playersIn);
    }

    @Override
    public void updateTurn(String whoIsTurn){
        queue.add(whoIsTurn);
    }

    @Override
    public void setNumMatch(String num){
        queue.add(num);
    }

    @Override
    public void updateMessage(String message){
        queue.add(message);
    }

    @Override
    public void setTimer(String timer){
        queue.add(timer);
    }
}
