package it.polimi.ingsw.network.client;

import it.polimi.ingsw.view.AbstractView;

/**
 * This class has the references to the messages received from server, and other service info like the type of connection, user name and the server address
 *
 */
public class Client  {
    private String name;
    private boolean connected;
    private int kindConnection;
    private String addr;
    private ConnectionHandler connectionHandler;
    private int numOfMatch;
    private MessageQueue messages;

    public Client(String addr) {
        this.connected=false;
        this.addr = addr;
        this.numOfMatch=-1;
    }

    /**
     * This method is used to set the abstract view observer
     * @param view view observer
     */
    void setQueue(AbstractView view){
        messages = new MessageQueue(view);
    }


    /**
     *
     * @param b true if it is connected to server
     */
    public synchronized void setConnected(boolean b){
        connected = b;
    }


    /**
     * Send a server request to connect
     */
    public synchronized void connect(){
        connectionHandler.connect();
    }

    /**
     *
     * @return client name
     */
    public synchronized String getName() {
        return name;
    }

    /**
     * Send to server a command
     * @param cmd command to send
     */
    public void sendCommand(String cmd) {
         connectionHandler.sendCommand(cmd);
    }

    /**
     *
     * @return true if connected
     */
    public synchronized boolean connected() {
        return connected;
    }

    /**
     *
     * @param n name to set to client
     */
    public synchronized void setName(String n) {
        name=n;
    }

    /**
     * According to the type of connection selected, initialize the object for connection setting the server address. If not valid number is set RMI by default
     * @param addr server address
     */
    private void setAddress(String addr) {
        if(kindConnection==1){
            connectionHandler = new SocketConnection(this,addr);
        }else if(kindConnection==2){
            connectionHandler = new RmiConnection(this,addr);
        }else{
            connectionHandler = new RmiConnection(this,addr);
        }
    }

    /**
     * Set type of connection
     * @param kindConnection type of connection
     */
    public void setKindConnection(int kindConnection) {
        try {
            this.kindConnection = kindConnection;
            setAddress(addr);
        }catch (RuntimeException e){
            this.kindConnection = 2;
        }
    }

    /**
     * @return queue message
     */
    MessageQueue getQueue() {
        return messages;
    }

    /**
     * set internal message on queue
     * @param serviceMessage internal message
     */
    void setServiceMessage(String serviceMessage) {
        messages.add(serviceMessage);
    }

    /**
     * set match number
     * @param i match number
     */
    public void setNumMatch(int i) {
        numOfMatch=i;
    }

    /**
     *
     * @return match number
     */
    public int getNumOfMatch(){return numOfMatch;}

}
