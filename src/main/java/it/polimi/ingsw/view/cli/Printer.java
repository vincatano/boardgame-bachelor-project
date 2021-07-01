package it.polimi.ingsw.view.cli;


import org.fusesource.jansi.AnsiConsole;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.*;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

/**
 * The class used to print data for the CLI
 */
public class Printer {
    private static final String ONE = "2680";
    private static final String TWO = "2681";
    private static final String THREE = "2682";
    private static final String FOUR = "2683";
    private static final String FIVE = "2684";
    private static final String SIX = "2685";
    private static final String CIRCLE = "25CF";
    private static final String IDNUMBER = "Numero id: ";
    private static final String DIFFICULTY = "     Difficoltà: ";
    private static final String ZERO = " 0 ";
    private static final String SETTEDTO = "Il timer è settato a: ";
    private static final String WAITING = "Fai una mossa..";
    private static final String NOTTURN = "In attesa di una mossa avversaria..";
    private static final String WINNER = "Il vincitore:   ";
    private static final String TOOLCARDS = "Carte utensile";
    private static final String CONNECTED = "Giocatori connessi:";
    private static final String DRAFT = "Prendere un dado dal draftpool:  draft indexDice";
    private static final String ROUND = "Prendere un dado dal roundtrack:  roundtrack x,y";
    private static final String WINDOW = "Prendere un dado dalla windowpatterncard:  window x,y";
    private static final String PLACE = "Posizionare il dado:  place x,y";
    private static final String PATTERNCARD = "Per scegliere la carta:  mycard idCarta";
    private static final String NUMBERTOOLCARD = "Per scegliere il valore del dado:  number numeroDado";
    private static final String TOOL = "Scegliere una toolcard:  toolcard idcarta";
    private static final String PASS = "Per andare avanti o passare il turno: pass";
    private static final String COORDINATES = "   y0 y1 y2 y3 y4";
    private static final String USERNAME = "Inserisci l'username:";
    private static final String CONNECTION = "Inserisci quale tipo di connessione vuoi utilizzare: \n1)SOCKET \n2)RMI";
    private static final String COSTO = "Costo";
    private static final String OBJECTIVE = "Carte obiettivo";
    private static final String DESCRIZIONE = "Descrizione";
    private static final String RESTRICTIONS = "restrictions";

    private Deparser deparser = new Deparser();

    private Scanner in = new Scanner(System.in);

    private List<String> connectedPlayers = new ArrayList<>();

    /**
     * Installs the AnsiConsole on the system.out so it will handle by himself the Ansi escapes
     */
    public Printer(){
        AnsiConsole.systemInstall();
    }


    /**
     * Print the welcome message
     */
    void welcome() {
        out.print(ansi().saveCursorPosition());
        out.print(ansi().a("Benvenuto in "));
        out.print( ansi().render("@|red S|@"));
        out.print( ansi().render("@|green A|@"));
        out.print( ansi().render("@|magenta G|@"));
        out.print( ansi().render("@|yellow R|@"));
        out.print( ansi().render("@|blue A|@"));
        out.print( ansi().render("@|red D|@"));
        out.print( ansi().render("@|green A|@"));
        out.print(ansi().a("    "));
        out.print(ansi().restoreCursorPosition().cursorDown(3));

    }

    /**
     * The method to ask the name of the player
     * @return The name of the player
     */
    public String getName() {
        out.print(ansi().a(USERNAME).cursorDown(1).cursorLeft(USERNAME.length()));
        String name = in.nextLine();
        while (name.equals("")||name.contains(" ") || name.contains("\\") || name.contains("/") || name.contains(",") || name.contains(";")) {
            out.print(ansi().a("Reinserisci il nome! (Non deve contenere [spazi],[,],[;],[/],[\\]").cursorLeft("Reinserisci il nome! (Non deve contenere [spazi],[,],[;],[/],[\\]".length()).cursorDown(1));
            name = in.nextLine();
        }
        return name;
    }

    /**
     * The method to ask which connection the client want to chose
     * @return An int representing the connection
     */
    public int getConnection() {
        out.print(ansi().a(CONNECTION).cursorDown(1).cursorLeft(CONNECTION.length()));
        return in.nextInt();
    }

    /**
     * The method prints if the connection has gone well
     * @param messageConnection The message for connection
     */
    public void printConnection(String messageConnection) {
        out.print(ansi().saveCursorPosition());
        out.print(ansi().fg(GREEN).a(messageConnection).reset()); //add a colour maybe
        out.print(ansi().restoreCursorPosition().cursorDown(3));

    }

    /**
     * The method prints the error message
     * @param alert The error message
     */
    public void printError(String alert) {
        out.print(ansi().saveCursorPosition());
        out.print(ansi().fg(RED).a(alert).reset().cursorDown(1));
        out.print(ansi().restoreCursorPosition().cursorDown(3));
    }


    /**
     * This method prints the timer and the specified string
     * @param timer The timer
     * @param output The specified string
     */
    public void printTimer(String timer, String output){
        out.print(ansi().saveCursorPosition());
        out.print(ansi().a(output + timer + "  "));
        out.print(ansi().restoreCursorPosition());
    }

    /**
     * Prints the players connected
     * @param players The name of players
     */
    public void printPlayersConnected(List<String> players){

        this.connectedPlayers = players;

        out.print(ansi().a(CONNECTED).cursorLeft(CONNECTED.length()));

        out.print(ansi().cursorDown(1));

        for (String player: players) {
            out.print(ansi().a(player + "  "));
            out.print(ansi().cursorLeft(player.length()+2).cursorDown(1));
        }

        out.print(ansi().cursorUp(players.size()));

    }


    /**
     * The method that prints the WindowPattern card that the player can chose
     * @param id The idnumber of the card
     * @param difficulty The difficulty of the card
     * @param restr The restrictions of the card
     */
    public void printchoosePatternCard(String id, String difficulty, List<String> restr){
        out.print(ansi().cursorDown(2));

        out.print(ansi().saveCursorPosition());

        out.print(ansi().a(IDNUMBER + id + DIFFICULTY +difficulty));

        out.print(ansi().restoreCursorPosition());
        out.print(ansi().saveCursorPosition());

        out.print(ansi().cursorDown(2));

        this.printPatternCard(restr);

        out.print(ansi().cursorDown(2));
    }

    /**
     * The method to wait to get the command
     * @return The command that the player has inserted
     */
    public String getCommand() {
        return in.nextLine();
    }

    /**
     * The method that prints the coordinates
     */
    private void printCoordinates(){
        out.print(ansi().saveCursorPosition());
        out.print(ansi().a(COORDINATES).cursorDown(1).cursorLeft(COORDINATES.length()));
        for(int i = 0; i < 4; i++){
            out.print(ansi().a("x"+i).cursorDown(1).cursorLeft(2));
        }
        out.print(ansi().restoreCursorPosition());
        out.print(ansi().cursorDown(1).cursorRight(3));
    }

    /**
     * Print the coordinates of the specified dimension
     * @param ri Initial row
     * @param rf Final row
     * @param ci Initial column
     * @param cf Final column
     * @param column Tha "name" of the index on column
     * @param row The "name" of the index on row
     */
    private void printCoordinates(int ri, int rf, int ci, int cf, char column, char row){
        out.print(ansi().cursorRight(3));
        for(int k = ci; k <= cf; k++){
            out.print(ansi().a(column));
            out.print(ansi().a(k));
            if(k<10)
                out.print(ansi().a(" "));
        }
        out.print(ansi().cursorLeft((cf-ci+1)*3+(3)).cursorDown(1));
        for(int i = ri; i < rf; i++){
            out.print(ansi().a(row).a(i).cursorDown(1).cursorLeft(2));
            if(i+1 == rf)
                out.print(ansi().cursorUp(1));
        }
    }

    /**
     * The method that prints the WindowPattern card
     * @param restr The list of strings representing the restrictions
     */
    private void printPatternCard(List<String> restr){
        for(int i = 0; i < restr.size(); i++){
            if((restr.get(i).charAt(1)-'0')!=0){
                out.print(ansi().bg(WHITE).fg(BLACK).a(" " + restr.get(i).charAt(1) + " ").reset());
            }
            else
                printBackground(restr.get(i).charAt(0));
            out.print(ansi().reset());
            if((restr.get(i).charAt(3)-'0')==4) {
                out.print(ansi().cursorDown(1).cursorLeft(15));
            }
        }
    }

    /**
     * The method that prints the placed dices
     * @param dices The list of string representing the placed dices
     */
    private void printPlacedDices(List<String> dices){
        int updown;
        int leftright;
        out.print(ansi().cursorUp(4));
        for(int i = 0; i < dices.size(); i++){
            updown = dices.get(i).charAt(2)-'0';
            leftright = dices.get(i).charAt(3)-'0';

            out.print(ansi().cursorDown(updown).cursorRight((leftright*3)));

            this.printDice(dices.get(i).charAt(0),dices.get(i).charAt(1));

            out.print(ansi().cursorUp(updown).cursorLeft((leftright*3)+3));

            out.print(ansi().reset());
            //out.print(ansi().restoreCursorPosition());
        }
        out.print(ansi().restoreCursorPosition());
        out.print(ansi().cursorUp(1));
    }

    /**
     * The method that prints the background
     * @param c The character representing the restriction (colour or number)
     */
    private void printBackground(Character c){

        Color color = this.findColor(c);

        out.print(ansi().bg(color).fg(color).a(ZERO).reset());

    }

    /**
     * Method to retrieve a color depending on a character
     * @param c The character representing the color
     * @return The Ansi color variable
     */
    private Color findColor(Character c){
        Color color = WHITE;
        switch (c) {
            case 'B':
                color = BLUE;
                break;
            case 'Y':
                color = YELLOW;
                break;
            case 'R':
                color = RED;
                break;
            case 'G':
                color = GREEN;
                break;
            case 'P':
                color = MAGENTA;
                break;
            default:
        }
        return color;
    }

    /**
     * The method that prints the dices
     * @param colour The colour of the dice
     * @param number The number of the dice
     */
    private void printDice(Character colour, Character number){
        try{
            PrintStream outStream = new PrintStream(out, true, "UTF-8");
            Character num = whatNumber(number);

            if(colour == 'W'){
                outStream.print(ansi().fg(WHITE).a(" " + num +" ").reset());
            }
            else{
                Color color = this.findColor(colour);
                outStream.print(ansi().bg(Color.WHITE).fg(color).a(" " + num +" ").reset());
            }

        } catch(UnsupportedEncodingException e){
            out.println("Caught exception: " + e.getMessage());
        }
    }

    /**
     * This method is used to find out which exadecimal number represents the right dice
     * @param number The number of the dice
     * @return The corresponding dice character to the number
     */
    private Character whatNumber(Character number){
        int intValue = 0;
        switch (number){
            case '1':
                intValue = Integer.parseInt(ONE, 16);
                break;
            case '2':
                intValue = Integer.parseInt(TWO, 16);
                break;
            case '3':
                intValue = Integer.parseInt(THREE, 16);
                break;
            case '4':
                intValue = Integer.parseInt(FOUR, 16);
                break;
            case '5':
                intValue = Integer.parseInt(FIVE, 16);
                break;
            case '6':
                intValue = Integer.parseInt(SIX, 16);
                break;
            default:
        }
        return (char)intValue;
    }

    /**
     * The method that prints the Waiting room screen
     * @param timer The timer of the waiting room
     * @param players The players connected
     * @param output The output string to print before the timer
     */
    void printWaitingRoom(String timer, List<String> players, String output){
        out.print(ansi().saveCursorPosition());

        this.printTimer(timer,output);

        out.print(ansi().cursorRight(output.length()+timer.length()+10));

        this.printPlayersConnected(players);

        out.print(ansi().restoreCursorPosition());

    }

    /**
     * Prints the screen for the selection of the WindowPattern cards
     * @param patternCards The pattern cards extracted
     * @param timer The timer
     */
    void printChooseCardRoom(String patternCards, String timer) {

        Deparser dep = new Deparser();

        List<String> numplusrestr;

        List<String> deparsed = dep.divideBySemicolon(patternCards);

        out.print(ansi().saveCursorPosition());

        out.print(ansi().eraseScreen(Erase.ALL));

        out.print(ansi().a(SETTEDTO).fg(BLUE).a(timer).reset().restoreCursorPosition());

        out.print(ansi().saveCursorPosition());

        out.print(ansi().cursorDown(3));


        for (String s: deparsed) {
            numplusrestr = dep.divideBySpace(s);

            this.printchoosePatternCard(numplusrestr.get(0),numplusrestr.get(1),dep.divideByComma(numplusrestr.get(2)));
        }

        out.print(ansi().a(PATTERNCARD).cursorLeft(PATTERNCARD.length()).cursorDown(1));

        //print all 4 windows and the timer (il timer deve essere fisso altrimenti il giocatore non può scrivere)
    }

    /**
     * Prints the match screen
     * @param setup The setup string with all the data of the match
     * @param timer The timer
     * @param players The players connected
     * @param turnState The current player
     */
    public void printMatch(String setup, String timer, List<String> players, String turnState) {


        String thisplayer = deparser.findPlayer(setup);

        deparser.setMyplayer(thisplayer);

        out.print(ansi().eraseScreen(Erase.ALL));

        out.print(ansi().saveCursorPosition());

        this.printMatchOtherCards(setup,players,deparser);

        out.print(ansi().restoreCursorPosition());
        out.print(ansi().cursorRight(90));
        this.printPlayersConnected(players);

        out.print(ansi().cursorRight(15));
        this.printActivePlayers(setup, deparser);

        out.print(ansi().restoreCursorPosition());
        out.print(ansi().cursorDown(8));
        out.print(ansi().saveCursorPosition());

        this.printCards(setup,deparser);

        out.print(ansi().restoreCursorPosition());
        out.print(ansi().cursorRight(70));

        this.printRoundtrack(setup,deparser);

        out.print(ansi().restoreCursorPosition());
        out.print(ansi().cursorDown(13));
        out.print(ansi().saveCursorPosition());

        this.printDescriptionCards(setup,deparser);

        out.print(ansi().restoreCursorPosition());
        out.print(ansi().cursorDown(15));
        out.print(ansi().saveCursorPosition());

        this.printMyPatternCard(setup,thisplayer,deparser);

        out.print(ansi().restoreCursorPosition().cursorLeft(3).saveCursorPosition());
        out.print(ansi().cursorRight(25));

        this.printPrivateCard(setup,deparser);

        out.print(ansi().restoreCursorPosition());
        out.print(ansi().cursorRight(60));

        this.printDraftPool(setup,deparser);

        out.print(ansi().restoreCursorPosition());
        out.print(ansi().cursorRight(100));

        this.printTurnOf(turnState);

        out.print(ansi().restoreCursorPosition());
        out.print(ansi().cursorRight(140));

        this.printTimerMatch(timer);

        out.print(ansi().restoreCursorPosition());
        out.print(ansi().cursorDown(8));
        out.print(ansi().saveCursorPosition());

        this.printCommands();

        out.print(ansi().restoreCursorPosition());

        this.printMove(players,turnState,deparser);

        out.print(ansi().cursorDown(10));

        //print all match layout (statico)
    }

    /**
     * Prints a different information depending on who's the current player
     * @param players The players connected
     * @param turnState The current player
     * @param deparser The deparser
     */
    private void printMove(List<String> players, String turnState, Deparser deparser) {
        boolean found = false;
        for (int i = 0; i < players.size() && !found;i++){
            if(turnState.contains(players.get(i)))
                found = true;
        }
        if(turnState.contains(deparser.getMyPlayer()))
            out.print(ansi().fg(YELLOW).a(WAITING).reset().cursorLeft(WAITING.length()));
        else if(!turnState.contains(deparser.getMyPlayer()) && found)
            out.print(ansi().fg(YELLOW).a(NOTTURN).reset().cursorLeft(NOTTURN.length()));
        else
            out.print(ansi().a(""));

    }

    /**
     * Prints the score screen
     * @param score The string representing all the players score and the winner
     */
    void printScore(String score) {
        Deparser dep = new Deparser();

        List<String> players = dep.divideByComma(dep.findString(score,"score "));
        List<String> playersandscore;

        String winner = "Nessun vincitore";

        int maximum=0;
        int result;

        out.print(ansi().eraseScreen(Erase.ALL));

        out.print(ansi().cursorDown(4));

        for(String s:players){

            out.print(ansi().cursorDown(1));

            playersandscore = dep.divideBySpace(s);

            out.print(ansi().a(playersandscore.get(0) + "  " + playersandscore.get(1)).cursorLeft(playersandscore.get(0).length() + playersandscore.get(1).length() + 2));

        }

        out.print(ansi().cursorUp(players.size()+2));

        out.print(ansi().cursorRight(20));
        out.print(ansi().bg(BLUE).fg(YELLOW).a(WINNER + winner).reset().cursorLeft(WINNER.length() + winner.length() + 20).cursorDown(players.size()+6));

        out.print(ansi().a("Gioca ancora? (Scrivi retry per accettare)"));

        out.print(ansi().cursorDown(2).cursorLeft("Gioca ancora? (Scrivi retry per accettare)".length()));



    }

    /**
     * Prints the state (Active or Inactive) of the player
     * @param setup The setup string
     * @param deparser The deparser
     */
    private void printActivePlayers(String setup, Deparser deparser){

        String tmp = deparser.findString(setup,"state");

        List<String> tempz = deparser.divideByComma(tmp);

        List<String> active;

        Color color;

        for (String s: tempz) {

            active = deparser.divideBySpace(s);

            if(active.get(1).charAt(0)=='A' && connectedPlayers.contains(active.get(0)))
                color = GREEN;
            else if(active.get(1).charAt(0)=='I' && connectedPlayers.contains(active.get(0)))
                color = RED;
            else
                color = BLACK;


            out.print(ansi().fg(color).a(active.get(1)).reset().cursorLeft(active.get(1).length()).cursorDown(1));

        }

    }

    /**
     * Prints the WindowPattern card of the other players
     * @param setup The setup string
     * @param players The players connceted
     * @param deparser The deparser
     */
    private void printMatchOtherCards(String setup, List<String> players, Deparser deparser){

            String parsed = deparser.otherCards(setup, players);

            List<String> tmp = deparser.divideBySemicolon(parsed);
            List<String> newString;

            for (int i = 0; i < players.size() - 1; i++) {
                newString = deparser.divideBySpace(tmp.get(i));
                if (!deparser.getMyPlayer().equals(newString.get(0))) {
                    out.print(ansi().saveCursorPosition());
                    out.print(ansi().a(newString.get(0))); //Print player name

                    out.print(ansi().cursorDown(1).cursorLeft(newString.get(0).length()));

                    this.printFavorTokens(setup, newString.get(0));

                    out.print(ansi().cursorDown(1));

                    this.printPatternCard(deparser.divideByComma(newString.get(1)));

                    if (newString.size() == 3){
                        this.printPlacedDices(deparser.divideByComma(newString.get(2)));
                        //out.print(ansi().cursorUp(1));
                        //out.print(ansi().saveCursorPosition());
                    }

                    out.print(ansi().restoreCursorPosition());

                    out.print(ansi().cursorRight(((i % 2) + 1) * (15 + 7)));
                }
            }

            out.print(ansi().cursorLeft(players.size()*(15+7)));
            out.print(ansi().saveCursorPosition());
            //out.print(ansi().restoreCursorPosition());
    }

    /**
     * Prints favor tokens for the player
     * @param setup The setup string
     * @param name The name of the player
     */
    private void printFavorTokens(String setup, String name) {

        try {
            PrintStream outStream = new PrintStream(out, true, "UTF-8");


            int tmp = setup.indexOf("favors " + name);
            int num = setup.charAt(tmp + ("favors " + name).length() + 1) - '0';

            char circle = (char)Integer.parseInt(CIRCLE, 16);

            for(int i = 0; i < num; i++){
                outStream.print(ansi().fg(CYAN).a(circle + " ").reset());
            }
            outStream.print(ansi().cursorLeft(num*2));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    /**
     * Print public cards and toolcards
     * @param setup The setup string
     * @param deparser The deparser
     */
    private void printCards(String setup, Deparser deparser){

        String toolCards = deparser.findString(setup,"toolcards");
        String objectiveCards = deparser.findString(setup,"publiccards");


        List<String> tmp = deparser.divideByBackslash(toolCards);
        List<String> tempz;

        out.print(ansi().a(TOOLCARDS + ("    ") + COSTO));

        out.print(ansi().cursorDown(1).cursorLeft((TOOLCARDS + ("    ") + COSTO).length()));

        for(int i = 0; i < tmp.size(); i++){
            tempz = deparser.divideBySlash(tmp.get(i));
            out.print(ansi().a(tempz.get(0)));

            out.print(ansi().cursorRight((TOOLCARDS + "    ").length()-tempz.get(0).length()));

            out.print(ansi().a(tempz.get(1)));

            out.print(ansi().cursorDown(1));

            out.print(ansi().cursorLeft((TOOLCARDS+"    ").length()+tempz.get(0).length()));

        }

        out.print(ansi().restoreCursorPosition());

        //here I finished printing toolcards, starting objective cards

        out.print(ansi().cursorRight((TOOLCARDS + "    " + COSTO).length() + 9));

        out.print(ansi().a(OBJECTIVE));

        out.print(ansi().cursorLeft((OBJECTIVE).length()).cursorDown(1));

        tmp = deparser.divideByBackslash(objectiveCards);

        for(int i = 0; i < tmp.size(); i++){
            tempz = deparser.divideBySlash(tmp.get(i));
            out.print(ansi().a(tempz.get(0)));
            out.print(ansi().cursorLeft(tempz.get(0).length()).cursorDown(1));
        }

        out.print(ansi().restoreCursorPosition());

    }

    /**
     * Prints the roundtrack
     * @param setup The setup string
     * @param deparser The deparser
     */
    private void printRoundtrack(String setup, Deparser deparser){

        List<String> tmp = deparser.divideByComma(deparser.findString(setup,"roundtrack"));

        out.print(ansi().a("Traccia dei Round"));
        out.print(ansi().cursorLeft("Traccia dei Round".length()).cursorDown(1));
        out.print(ansi().a("Round numero:"));
        for(int i = 1; i < 11; i++){
            out.print(ansi().a(" " + i + " "));
        }

        out.print(ansi().cursorLeft(33).cursorDown(1));

        int max = 0;
        for(String d : tmp){
            if(!d.equals("") && (d.charAt(2) - '0')> max){
                    max = (d.charAt(2)-'0');
            }
        }

        printCoordinates(0,max+1,1,10,'x','y');

        out.print(ansi().cursorUp(max));

        out.print(ansi().cursorRight(3));

        for (String dice: tmp){
            if(!dice.equals("")) {

                out.print(ansi().cursorRight((dice.charAt(3) - '0') * 3).cursorDown((dice.charAt(2) - '0')));


                this.printDice(dice.charAt(0), dice.charAt(1));


                out.print(ansi().cursorLeft(((dice.charAt(3) - '0') + 1) * 3).cursorUp((dice.charAt(2) - '0')));
            }
        }

        out.print(ansi().restoreCursorPosition());

    }

    /**
     * Prints the description for Toolcards
     * @param setup The setup string
     * @param deparser The deparser
     */
    private void printDescriptionCards(String setup, Deparser deparser){

        String tmp = deparser.findString(setup,"toolcards");

        List<String> tempz = deparser.divideByBackslash(tmp);
        List<String> last;

        out.print(ansi().a(TOOLCARDS + "   " + DESCRIZIONE));
        out.print(ansi().cursorLeft(TOOLCARDS.length() + "   ".length() + DESCRIZIONE.length()).cursorDown(1));

        for (int i = 0; i < tempz.size(); i++){
            last = deparser.divideBySlash(tempz.get(i));
            out.print(ansi().a(last.get(0)));
            out.print(ansi().cursorRight((TOOLCARDS.length() + "   ".length() - last.get(0).length())));

            out.print(ansi().a(last.get(2)));

            out.print(ansi().cursorLeft((TOOLCARDS.length() + "   ".length() + DESCRIZIONE.length() - last.get(0).length())+last.get(2).length()));

            out.print(ansi().cursorDown(1));
        }

        out.print(ansi().cursorDown(2));

        tmp = deparser.findString(setup,"publiccards");
        tempz = deparser.divideByBackslash(tmp);


        out.print(ansi().a(OBJECTIVE + "   " + DESCRIZIONE));
        out.print(ansi().cursorLeft(OBJECTIVE.length() + "   ".length() + DESCRIZIONE.length()).cursorDown(1));

        for (int i = 0; i < tempz.size(); i++){
            last = deparser.divideBySlash(tempz.get(i));
            out.print(ansi().a(last.get(0)));
            out.print(ansi().cursorRight((OBJECTIVE.length() + "   ".length() - last.get(0).length())));

            out.print(ansi().a(last.get(1)));

            out.print(ansi().cursorLeft((OBJECTIVE.length() + "   ".length() + DESCRIZIONE.length() - last.get(0).length())+last.get(1).length()));

            out.print(ansi().cursorDown(1));
        }
    }

    /**
     * Prints the WindowPattern card of the current player
     * @param setup The setup string
     * @param player The current player
     * @param deparser The deparser
     */
    private void printMyPatternCard(String setup, String player, Deparser deparser){

        String myCard = deparser.getMyCard(setup,player);

        List<String> newString = deparser.divideBySpace(myCard);

        out.print(ansi().a("   "+newString.get(0)));

        out.print(ansi().cursorDown(1).cursorLeft(newString.get(0).length()));

        this.printFavorTokens(setup,newString.get(0));

        out.print(ansi().cursorDown(1).cursorLeft(3));

        this.printCoordinates();
        this.printPatternCard(deparser.divideByComma(newString.get(1)));

        if(newString.size() == 3)
            this.printPlacedDices(deparser.divideByComma(newString.get(2)));

        out.print(ansi().restoreCursorPosition());

    }

    /**
     * Prints the private card
     * @param setup The setup string
     * @param deparser The deparser
     */
    private void printPrivateCard(String setup, Deparser deparser){

        String tmp = deparser.findString(setup,"privatecard");

        List<String> tempz = deparser.divideByComma(tmp);

        out.print(ansi().a("Carta obiettivo privata").cursorDown(1));

        out.print(ansi().cursorLeft("Carta obiettivo privata".length()).a("Numero  Colore Dadi"));

        out.print(ansi().cursorLeft("Numero  Colore Dadi".length()).cursorDown(1));

        Color color = WHITE;

        switch (tempz.get(1).charAt(0)){
            case 'b':
                color = BLUE;
                break;
            case 'g':
                color = YELLOW;
                break;
            case 'r':
                color = RED;
                break;
            case 'v':
                if(tempz.get(1).charAt(1)=='i')
                    color = MAGENTA;
                else
                    color = GREEN;
                break;
            default:
        }

        out.print(ansi().a(tempz.get(0) + "       ").fg(color).a(tempz.get(1).substring(0,1).toUpperCase() + tempz.get(1).substring(1)).reset());

    }

    /**
     * Prints the draftpool
     * @param setup The setup string
     * @param deparser The deparser
     */
    private void printDraftPool(String setup, Deparser deparser){

        String tmp = deparser.findString(setup,"draftpool");

        List<String> tempz = deparser.divideByComma(tmp);

        out.print(ansi().a("Riserva"));

        out.print(ansi().cursorDown(1).cursorLeft("Riserva".length()));

        printCoordinates(0,0,0,8,' ','\0');

        out.print(ansi().cursorRight(3));

        for (String s: tempz) {
            this.printDice(s.charAt(0),s.charAt(1));
        }

    }

    /**
     * Prints the player that is owning the turn
     * @param turnState The string telling who is the current player
     */
    private void printTurnOf(String turnState){
        out.print(ansi().a(turnState));
    }

    /**
     * The method that prints the timer for the match
     * @param timer The timer
     */
    private void printTimerMatch(String timer){
        if(Integer.valueOf(timer)>=0){
            out.print(ansi().a("Timer: "+timer));
        }

    }

    /**
     * The method that prints the commands legenda
     */
    private void printCommands(){

        out.print(ansi().cursorRight(70));

        out.print(ansi().a("Legenda mosse:  ").cursorLeft("  ".length()));

        out.print(ansi().cursorDown(1));

        out.print(ansi().a(DRAFT).cursorLeft(DRAFT.length()).cursorDown(1));
        out.print(ansi().a(ROUND).cursorLeft(ROUND.length()).cursorDown(1));
        out.print(ansi().a(WINDOW).cursorLeft(WINDOW.length()).cursorDown(1));
        out.print(ansi().a(PLACE).cursorLeft(PLACE.length()).cursorDown(1));
        out.print(ansi().a(TOOL).cursorLeft(TOOL.length()).cursorDown(1));
        out.print(ansi().a(PASS).cursorLeft(PASS.length()).cursorDown(1));


    }

    /**
     * The method used to make the player choose a dice in particular toolcards
     */
    void printChooseDice() {

        out.print(ansi().cursorDown(2));

        out.print(ansi().a(NUMBERTOOLCARD).cursorLeft(NUMBERTOOLCARD.length()).cursorDown(2));

        out.print(ansi().saveCursorPosition());
    }

    void printIncDecDice() {
        //Still to implement
    }

    /**
     * A private class used to divide strings, find substrings and retrieve the current player
     */
    private class Deparser {

        String myplayer;

        /**
         * @return The string representing the player associated to this deparser
         */
        private String getMyPlayer(){
            return myplayer;
        }

        /**
         * Set the player to the player given
         * @param myplayer The player to set
         */
        private void setMyplayer(String myplayer) {
            this.myplayer = myplayer;
        }

        /**
         * Method used to retrieve other players cards and write them in a string in a defined way
         * @param setup The setup string
         * @param players The players connected
         * @return The string representing WindowPatter cards of the other players, comprehensive of the dices placed
         */
        private String otherCards(String setup, List<String> players){

            StringBuilder builder = new StringBuilder();

            String tmp = setup.substring(setup.indexOf(RESTRICTIONS),setup.length());

            List<String> tempz = this.divideBySemicolon(tmp);


            for(int i = 0; i < (players.size()*2); i = i+2){
                tempz.set(i,tempz.get(i).replace(RESTRICTIONS + " ",""));
                tempz.set(i+1,tempz.get(i+1).replace("dices ",""));

                if(!tempz.get(i).substring(0,tempz.get(i).indexOf(',')).equals(myplayer)){
                    String player = tempz.get(i).substring(0,tempz.get(i).indexOf(','));
                    builder.append(player);
                    builder.append(" ");
                    builder.append(tempz.get(i).replace(";","").replace(tempz.get(i).substring(0,tempz.get(i).indexOf(',')+1),""));
                    builder.append(" ");
                    String dices = tempz.get(i+1).replace(";","").replace(player,"");
                    if(!dices.equals("") && dices.charAt(0)==',') {
                        dices = dices.substring(1, dices.length());
                        builder.append(dices);
                    }
                    if (builder.charAt(builder.length()-1) == ' ' || builder.charAt(builder.length()-1)==',')
                        builder.deleteCharAt(builder.length()-1);

                    builder.append(";");
                }
            }

            return builder.toString();
        }

        /**
         * Method to retrieve the card of the current player
         * @param setup The setup string
         * @param player The player you're looking for
         * @return The string representing WindowPatter cards of the player, comprehensive of the dices placed
         */
        private String getMyCard(String setup, String player){

            StringBuilder builder = new StringBuilder();

            String tmp = setup.substring(setup.indexOf((RESTRICTIONS + " " + player)));

            int secondIndex = tmp.indexOf(';', tmp.indexOf(';')+1);

            tmp = tmp.substring(0, secondIndex); //should find the pattern card and dices of my player

            List<String> tempz = this.divideBySemicolon(tmp);

            tempz.set(0,tempz.get(0).replace(RESTRICTIONS + " ",""));
            tempz.set(1,tempz.get(1).replace("dices ",""));

            builder.append(tempz.get(0).substring(0,tempz.get(0).indexOf(',')));
            builder.append(" ");
            builder.append(tempz.get(0).replace(";","").replace(tempz.get(0).substring(0,tempz.get(0).indexOf(',')+1),""));
            builder.append(" ");
            String dices = tempz.get(1).replace(";","").replace(player,"");

            if(!dices.equals("") && dices.charAt(0)==',') {
                dices = dices.substring(1, dices.length());
                builder.append(dices);
            }

            if (builder.charAt(builder.length()-1) == ' ' || builder.charAt(builder.length()-1)==',')
                builder.deleteCharAt(builder.length()-1);


            return builder.toString();
        }


        /**
         * Returns the substring you're looking for without the keyword toFind
         * @param setup The setup string
         * @param toFind The substring you're looking for
         * @return The string requested
         */
        //returns the substring you're looking for without the keyword toFind
        private String findString(String setup, String toFind){
            List<String> divided = divideBySemicolon(setup);

            int i;
            for(i = 0; i < divided.size() && !divideBySpace(divided.get(i)).get(0).equals(toFind);i++);

            return divided.get(i).substring(toFind.length()+1,divided.get(i).length());
        }

        /**
         * Method used to find the name of the current player
         * @param setup The setup string
         * @return The name of the current player
         */
        private String findPlayer(String setup){

            String tmp = setup.substring(setup.indexOf((RESTRICTIONS + " " )));

            int secondIndex = tmp.indexOf(';', tmp.indexOf(';')+1);

            tmp = tmp.substring(0, secondIndex);

            tmp = tmp.replace(RESTRICTIONS + " ","");

            return tmp.substring(0,tmp.indexOf(','));
        }

        /**
         * Method that returns a list of strings dividing the given one by semicolon
         * @param toDivide The string to divide
         * @return The list with the divided strings
         */
        private List<String> divideBySemicolon(String toDivide) {return Arrays.asList(toDivide.split(";")); }

        /**
         * Method that returns a list of strings dividing the given one by semicolon
         * @param toDivide The string to divide
         * @return The list with the divided strings
         */
        private List<String> divideByComma(String toDivide) {
            return Arrays.asList(toDivide.split(","));
        }

        /**
         * Method that returns a list of strings dividing the given one by semicolon
         * @param toDivide The string to divide
         * @return The list with the divided strings
         */
        private List<String> divideBySpace(String toDivide){return Arrays.asList(toDivide.split(" ")); }

        /**
         * Method that returns a list of strings dividing the given one by semicolon
         * @param toDivide The string to divide
         * @return The list with the divided strings
         */
        private List<String> divideBySlash(String toDivide) {return Arrays.asList(toDivide.split("/")); }

        /**
         * Method that returns a list of strings dividing the given one by semicolon
         * @param toDivide The string to divide
         * @return The list with the divided strings
         */
        private List<String> divideByBackslash(String toDivide) {return Arrays.asList(toDivide.split("\\\\")); }

    }


}
