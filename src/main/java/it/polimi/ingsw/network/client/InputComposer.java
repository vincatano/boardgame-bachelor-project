package it.polimi.ingsw.network.client;

import it.polimi.ingsw.view.cli.GameData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is used by cliHandler to analyze if the input from user is well formed.
 */
public class InputComposer {
    private static final String MOVE = "move ";
    private static final String CHOOSECARD = "chooseCard ";
    private static final String PLAYAGAIN = "playAgain ";
    private static final String DISCONNECT = "disconnect ";
    private static final String LOGIN = "login ";

    private static final String DRAFT = "draft ";
    private static final String WINDOW = "window ";
    private static final String ROUNDTRACK = "roundtrack ";
    private static final String PLACE = "place ";
    private static final String TOOLCARD = "toolcard ";
    private static final String RETRY = "retry";
    private static final String MYCARD = "mycard ";
    private static final String PASS = "pass";
    private static final String NUMBER = "number ";

    private static final String ALERT = "alert Formato mossa errato!";
    private static final String ERROR = "error";

    private static final int MAXRIG = 4 ;
    private static final int MAXCOL = 5;


    private Client client;
    private List<String> words;

    /**
     * adds all the forbidden words in a list
     * @param client client reference
     */
    public InputComposer(Client client) {
        this.client = client;
        words = new ArrayList<>();
        words.add(MOVE);
        words.add(CHOOSECARD);
        words.add(PLAYAGAIN);
        words.add(DISCONNECT);
        words.add(LOGIN);

    }

    /**
     * Compose the login message
     * @param cmd command to compose
     * @return message well formed
     */
    String compose(String cmd) {
        if(cmd.equals("login")) {
            return LOGIN + " <User>" + client.getName() + "<User>";
        }
        return cmd;
    }

    /**
     * This method check if the input is well formed, and compose the command for the server thanks to gameData who memorize the current state of the
     * model
     * @param command user input
     * @param gameData game data, represent server model
     * @return error if it's not a good input else the command to send
     */
    public String sanitize(String command, GameData gameData) {
        for (String s : words) {
            if (command.contains(s)) {
                return ERROR;
            }
        }

        String res;
        if(command.startsWith(DRAFT)) {
            res=command.replace(DRAFT,"");
            return getDraftData(res,gameData);
        }
        else if (command.startsWith(WINDOW)) {
            res=command.replace(WINDOW,"");
            return getWindowData(res,gameData);
        }
        else if (command.startsWith(ROUNDTRACK) ){
            res=command.replace(ROUNDTRACK,"");
            return getRoundData(res,gameData);
        }
        else if (command.startsWith(PLACE) ){
            res=command.replace(PLACE,"");
            return getPos(res);
        }
        else if (command.startsWith(TOOLCARD) ){
            res=command.replace(TOOLCARD,"");
            return MOVE+client.getNumOfMatch()+" "+client.getName()+" "+"T;"+res;
        }
        else if (command.equals(RETRY)) {
            return PLAYAGAIN + client.getName();
        }
        else if (command.startsWith(MYCARD) ){
            res=command.replace(MYCARD,"");
            return CHOOSECARD+client.getNumOfMatch()+" "+client.getName()+" "+res;
        }else if(command.equals(PASS)){
            return MOVE+client.getNumOfMatch()+" "+client.getName()+" "+PASS;
        }else if(command.startsWith(NUMBER)){
            res=command.replace(NUMBER,"");
            return getDiceData(res,gameData);
        }

        return ERROR;

    }

    /**
     * this is a special command for the toolcard 11 which choose the number of a dice
     * @param res dice number to set
     * @param gameData model representation
     * @return error if string is not well formed else the right command
     */
    private String getDiceData(String res, GameData gameData) {
        try{
            int num = Integer.parseInt(res);
            return MOVE+client.getNumOfMatch()+" "+client.getName()+" "+"D;"+gameData.getChangedDice(num)+","+"0"+" --";
        }catch (RuntimeException e){
            client.setServiceMessage(ALERT);
            return ERROR;
        }
    }

    /**
     * check if position
     * @param res position chosen
     * @return error if string is not well formed else the right command
     */
    private String getPos(String res) {
        try{
            String[] pos = res.split(",");
            int x = Integer.parseInt(pos[0]);
            int y = Integer.parseInt(pos[1]);
            if(x>=0 && x< MAXRIG && y>=0 && y<MAXCOL){
                return MOVE+client.getNumOfMatch()+" "+client.getName()+" "+"P;"+res;
            }else {
                return ERROR;
            }
        }catch (RuntimeException e ){
            client.setServiceMessage(ALERT);
            return ERROR;
        }
    }

    /**
     * Get command for dice chosen from round track
     * @param res dice position on round track
     * @param gameData model representation
     * @return error if string is not well formed else the right command
     */
    private String getRoundData(String res, GameData gameData) {
        try {
            String[] pos = (res.split(","));
            int x = Integer.parseInt(pos[0]);
            int y = Integer.parseInt(pos[1]);
            return MOVE+client.getNumOfMatch()+" "+client.getName()+" "+"D;"+gameData.getRound(x-1,y)+","+ Arrays.toString(pos);
        }catch (RuntimeException e){
            client.setServiceMessage(ALERT);
            return ERROR;
        }
    }

    /**
     * get command for a dice chosen from window pattern card
     * @param res dice position on window pattern card
     * @param gameData model representation
     * @return error if string is not well formed else the right command
     */
    private String getWindowData(String res, GameData gameData) {
        try {
            String[] pos = (res.split(","));
            int x = Integer.parseInt(pos[0]);
            int y = Integer.parseInt(pos[1]);
            return MOVE+client.getNumOfMatch()+" "+client.getName()+" "+"D;"+gameData.getWindow(x,y)+","+ Arrays.toString(pos);
        }catch (RuntimeException e){
            client.setServiceMessage(ALERT);
            return ERROR;
        }
    }

    /**
     * get command for a dice chosen from draft pool
     * @param res dice position on draft pool
     * @param gameData model representation
     * @return error if string is not well formed else the right command
     */
    private String getDraftData(String res, GameData gameData) {
        try {
            int x = Integer.parseInt(res);
            return MOVE+client.getNumOfMatch()+" "+client.getName()+" "+"D;"+gameData.getDraft(x)+","+x+","+"0";
        }catch (RuntimeException e){
            client.setServiceMessage(ALERT);
            return ERROR;
        }

    }
}
