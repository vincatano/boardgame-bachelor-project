package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.InputAnalyzer;
import it.polimi.ingsw.network.ClientInterface;
import it.polimi.ingsw.network.ServerInterface;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import static java.lang.System.out;

/**
 * This class make available the server on the network with rmi or socket connection
 */
public class NetworkManager {
    private static final int PORTRMI = 56789;
    private static final int PORTSOCKET = 45678;
    private Server server;

    NetworkManager(Server s) {
        server = s;
    }

    /**
     * initialize the network manager, it create the rmi registry and the socket connection
     */
    public void start(){
        try {
            Registry registry = LocateRegistry.createRegistry(PORTRMI);
            ServerInterface stub = (ServerInterface)UnicastRemoteObject.exportObject(server,PORTRMI);
            registry.bind("server", stub);
            try (ServerSocket serverSocket = new ServerSocket(PORTSOCKET)) {
                SocketContainer sc;
                out.println("Server is up");
                do  {
                    Socket socket = serverSocket.accept();
                    sc = new SocketContainer(socket, server);
                    new Thread(sc).start();
                }while (server!=null);
            }
        } catch (Exception e) {
            out.println("Can't listen for communication");
        }

    }

    /**
     * This class is used for socket connection, when a client is connected by socket, is created a new thread which keep the connection alive
     *
     */
    private class SocketContainer implements ClientInterface, Runnable{
        private Socket socket;
        private Server server;
        private String name;
        private boolean connected;
        private PrintWriter pr;
        private Scanner in;

        SocketContainer(Socket socket, Server s){
            this.socket = socket;
            server = s;
            connected =false;
        }

        /**
         * it first wait the login message, then wait a command from client
         */
        @Override
        public void run() {
            try {
                in = new Scanner(socket.getInputStream());
                pr = new PrintWriter(socket.getOutputStream(),true);
                loginPlayer();
                while (connected) {
                    receiveCommand();
                }
            } catch (Exception e) {
                server.disconnect(name,this);
            }

        }

        /**
         * handling the login message, and verify if the player is already connected
         */
        private void loginPlayer() {
            String message = in.nextLine();
            while (!connected) {
                if (message.startsWith("login")) {
                    InputAnalyzer analyzer = new InputAnalyzer();
                    String nameP = analyzer.getData(message);
                    setName(nameP);
                    if (server.login(nameP, this)) {
                        connected = true;
                    } else {
                        message = in.nextLine();
                    }
                }
            }
        }

        /**
         * keep listen for a command from client
         */
        private void receiveCommand() {
                String message;
                message = in.nextLine();
                server.command(message);
        }


        /**
         *
         * @param name name to set on this client reference
         */
        private void setName(String name){
            this.name =name;
        }


        @Override
        public String getName() {
            return name;
        }

        @Override
        public void ping()  {
            pr.println("ping");
        }

        @Override
        public String getTypeConnection() {
            return "(Socket)";
        }

        @Override
        public void update(String updateMove) {
            pr.println(updateMove);

        }

        @Override
        public void updatePlayers(String playersIn) {
            pr.println(playersIn);
        }

        @Override
        public void updateTurn(String whoIsTurn) {
            pr.println(whoIsTurn);

        }

        @Override
        public void setNumMatch(String num) {
            pr.println(num);
        }

        @Override
        public void updateMessage(String message) {
            pr.println(message);
        }

        @Override
        public void setTimer(String timer) {
            pr.println(timer);
        }
    }
}
