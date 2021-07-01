package it.polimi.ingsw.network.client;


import it.polimi.ingsw.view.AbstractView;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This class contains all messages received from server, when added one it will notify the observer
 */
public class MessageQueue {
    private ConcurrentLinkedQueue<String> messages;
    private AbstractView view;


    MessageQueue(AbstractView view){
        this.view = view;
        messages = new ConcurrentLinkedQueue<>();
    }

    /**
     *
     * @param s message to add in queue
     */
    synchronized void add(String s){
        messages.add(s);
        view.notifyMessage();
    }

    /**
     *
     * @return message from queue
     */
    public synchronized String poll(){
        return messages.poll();
    }

    /**
     *
     * @return queue size
     */
    public synchronized int size(){return messages.size();}
}
