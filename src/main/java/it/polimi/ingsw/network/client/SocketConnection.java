package it.polimi.ingsw.network.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class is used to handle the socket connection
 */
public class SocketConnection implements ConnectionHandler {
    private static final String SERVERALERT = "alert Server not available";
    private static int PORTSOCKET = 45678;
    private Client client;
    private Socket socket;
    private InputComposer inputComposer;

    SocketConnection(Client client, String addr) {
        this.client = client;
        inputComposer = new InputComposer(client);
        try {
            socket = new Socket(addr, PORTSOCKET);
        } catch (IOException e) {
            client.setServiceMessage("alert Connection not available");        }
    }

    /**
     * this starts a new thread who listen every message from server
     * @param in communication channel already open
     */
    private void startListener(Scanner in){
        SocketListener listener = new SocketListener(client.getQueue(), socket, in);
        listener.start();
    }

    @Override
    public void connect() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter pr = new PrintWriter(socket.getOutputStream(), true);
            if (!client.connected()) {
                String rqst = inputComposer.compose("login");
                pr.println(rqst);
                String answ = in.nextLine();
                if (answ.equals("Connected, Welcome!")) {
                    client.setConnected(true);
                }
                client.setServiceMessage(answ);
            }
            startListener(in);
        } catch (Exception e) {
            client.setServiceMessage(SERVERALERT);
        }


    }

    @Override
    public void sendCommand(String cmd) {
        try {
            PrintWriter pr = new PrintWriter(socket.getOutputStream(), true);
            cmd = inputComposer.compose(cmd);
            pr.println(cmd);
        } catch (Exception e) {
            client.setServiceMessage(SERVERALERT);
        }

    }

}
