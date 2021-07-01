package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.ClientInterface;
import it.polimi.ingsw.network.ServerInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class is used to make a rmi connection to server, when created the constructor create also the client stub to pass to server like the
 * reference to the client
 */

public class RmiConnection implements ConnectionHandler {
    private static final String SERVERALERT = "alert Server not available";
    private static final int PORTRMI = 56789;
    private ServerInterface server;
    private Client client;
    private ClientInterface stub;

    RmiConnection(Client client, String addr){
        this.client = client;
        try {
            Registry registry = LocateRegistry.getRegistry(addr,PORTRMI);
            ClientStub obj = new ClientStub(client.getQueue(),client.getName());
            stub = (ClientInterface) UnicastRemoteObject.exportObject(obj,0);
            server = (ServerInterface) registry.lookup("server");
        } catch (Exception e) {
            client.setServiceMessage("alert Connection not available");
        }

    }


      public void connect(){
        if(!client.connected()) {
            try {
                String name = client.getName();
                if (server.login(name,stub)) {
                    client.setConnected(true);
                } else {
                    client.setConnected(false);

                }
            } catch (Exception e) {
                client.setServiceMessage(SERVERALERT);
            }
        }
    }

    @Override
    public void sendCommand(String cmd) {
        try {
            server.command(cmd);
        } catch (RemoteException e) {
            client.setServiceMessage(SERVERALERT);
        }
    }



}
