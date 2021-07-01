package it.polimi.ingsw.model.gamedata;


import it.polimi.ingsw.model.gamedata.gametools.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * This class represents the table with all his elements
 */
public class Table {
    private List<Player> myPlayers;
    private List<ObjectiveCard> objectiveCards;
    private List<ToolCard> toolCards = new ArrayList<>(3);
    private RoundTrack roundTrack;
    private DraftPool draftPool;
    private DiceBag diceBag;
    private CardContainer container;
    private List<WindowPatternCard> windowPatternCards;
    private List<Integer> temporaryCards = new ArrayList<>();

    private static final String SCORE = "score ";
    private static final char SPACE = ' ';
    private static final char VIRG = ',';
    private static final String NOONE = "noone";



    /**
     * The classic constructor
     * @param players The players that will be part of the table
     */
    public Table(List<Player> players){
        this.diceBag = new DiceBag();
        this.draftPool = new DraftPool();
        this.container = new CardContainer();
        this.myPlayers = players;
        this.roundTrack = new RoundTrack();
    }

    /**
     * This methods pulls out all the cards needed for the game, sets the players for the dicebag and sets the public objects
     * for every player
     */
    public void initialize(){
        int i;
        this.toolCards = this.container.pullOutTools();
        this.objectiveCards = this.container.pullOutPublic();
        List<ObjectiveCard> tmp = this.container.pullOutPrivate(this.myPlayers.size());
        i = 0;
        for (Player p: this.myPlayers){
            p.setMyObjCard(tmp.get(i));
            i++;
        }
        windowPatternCards = this.container.pullOutPattern(this.myPlayers.size());

        this.diceBag.setNumPlayers(this.myPlayers.size());
    }


    /**
     * The method used to ask to the players which pattern card they want to use
     */
    public void selectWindowCards(){
        List<WindowPatternCard> patterns = new ArrayList<>();
        for (int i = 0; i < myPlayers.size(); i++) {
            patterns.add(windowPatternCards.get((i*4)));
            temporaryCards.add(windowPatternCards.get(i*4).getNum());
            patterns.add(windowPatternCards.get((i*4)+1));
            temporaryCards.add(windowPatternCards.get((i*4)+1).getNum());
            patterns.add(windowPatternCards.get((i*4)+2));
            temporaryCards.add(windowPatternCards.get((i*4)+2).getNum());
            patterns.add(windowPatternCards.get((i*4)+3));
            temporaryCards.add(windowPatternCards.get((i*4)+3).getNum());
            myPlayers.get(i).chooseWindow(patterns);
            patterns.clear();
        }
        setPublicObjects();
    }


    /**
     * Getter method for Objective cards
     * @return The list representing the objective cards
     */
    public List<ObjectiveCard> getObjCard() {
        return objectiveCards;
    }


    /**
     * Getter method for the dicebag
     * @return The list representing the dicebag
     */
    public List<Dice> getDiceFromBag() {
        return diceBag.pullOut();
    }


    /**
     * Getter method for draftpool
     * @return The list representing the draftpool
     */
    public List<Dice> getAllDraft() {
        return this.draftPool.getDraftPool();
    }


    /**
     * Getter method for the draftppol
     * @return The draftpool
     */
    public DraftPool getDraftPool(){
        return this.draftPool;
    }


    /**
     * Getter method for the roundtrack
     * @return The roundtrack
     */
    public RoundTrack getRoundTrack(){
        return this.roundTrack;
    }


    /**
     * Getter method for dicebag
     * @return The dicebag
     */
    public DiceBag getDiceBag() {
        return this.diceBag;
    }


    /**
     * Getter method for Tool cards
     * @return The list representing the Tool cards
     */
    public List<ToolCard> getToolCards(){
        return this.toolCards;
    }


    /**
     * Sets the dices left from the draftpool on the roundtrack at the specified round
     * @param numRound The round specified
     */
    public void setLastDices(int numRound){
        this.roundTrack.setDiceOnRoundTrack(numRound,this.draftPool.getDraftPool());
    }

    /**
     * Method to assign a random WindowPattern card when a players doesn't make a choice
     * @param p The name of the players
     */
    public void setWindow(String p){
        Random random = new Random();
        int i = 0;
        while (i < this.myPlayers.size() && !(p.equals(this.myPlayers.get(i).getUsername()))) {
            i++;
        }
        Player player = this.myPlayers.get(i);
        player.setMyWindow(windowPatternCards.get(random.nextInt(4) + 4*i));
    }

    /**
     * The method called to assign the WindowPattern card that the player has chosen
     * @param p The name of the player
     * @param id The idnumber of the card chosen
     */
    public void setWindow(Player p, int id){
        int i = 0;

        while (i < this.myPlayers.size() && !(p.getUsername().equals(this.myPlayers.get(i).getUsername()))) {
            i++;
        }

        if(temporaryCards.contains(id)) {
            if (windowPatternCards.get(i * 4).getNum() == id)
                this.myPlayers.get(i).setMyWindow(windowPatternCards.get(i * 4));
            if (windowPatternCards.get((i * 4) + 1).getNum() == id)
                this.myPlayers.get(i).setMyWindow(windowPatternCards.get((i * 4) + 1));
            if (windowPatternCards.get((i * 4) + 2).getNum() == id)
                this.myPlayers.get(i).setMyWindow(windowPatternCards.get((i * 4) + 2));
            if (windowPatternCards.get((i * 4) + 3).getNum() == id)
                this.myPlayers.get(i).setMyWindow(windowPatternCards.get((i * 4) + 3));
        }
        else
            this.setWindow(p.getUsername());
    }


    /**
     * Fill the draftpool by pulling out dices from the dicebag
     */
    public void fillDraftPool(){
        this.draftPool.addNewDices(diceBag.pullOut());
    }

    /**
     * Set the public objects for every player present in the table
     */
    public void setPublicObjects(){
        for (Player p: this.myPlayers) {
            PublicObjects publicObjects = new PublicObjects();

            publicObjects.setToolCards(this.getToolCards());

            publicObjects.setObjectiveCards(this.getObjCard());

            publicObjects.setDraftPool(this.getDraftPool());

            publicObjects.setRoundTrack(this.roundTrack);

            List<Player> players = new ArrayList<>();
            for (Player player: this.myPlayers){
                if(!(player.getUsername().equals(p.getUsername()))) {
                    players.add(player);
                }
            }

            publicObjects.setOthersPlayers(players);

            p.setPublicObjects(publicObjects);
        }
    }

    /**
     * Method that finds out who's the winner of the match and return a string representing the score of every player
     * and the winner
     * @return The string with each score(in the first position there's the winner)
     */
    public String calculatePoints(){
        StringBuilder builder = new StringBuilder();
        builder.append(SCORE);

        HashMap<Player,Integer> scores = new HashMap<>();

        for (Player player:this.myPlayers){
            scores.put(player,player.calculatePoints());
        }

        builder.append(this.findWinner(scores));

        for (Player player : scores.keySet()) {
            builder.append(player.getUsername());
            builder.append(SPACE);
            builder.append(scores.get(player));
            builder.append(VIRG);
        }

        if(builder.toString().charAt(builder.length()-1)== VIRG)
            builder.deleteCharAt(builder.length()-1);

        return builder.toString();
    }


    /**
     * Find the winner
     * @param scores The scores of each player
     * @return The string with the winner
     */
    private String findWinner(HashMap<Player,Integer> scores){

        StringBuilder builder = new StringBuilder();
        Player winner = new Player(NOONE);


        int numberActive = 0;
        for (Player p: this.myPlayers){
            if(p.isActive())
                numberActive++;
        }

        if(numberActive == 1){
            for(Player p: this.myPlayers){
                if(p.isActive()){
                    winner = p;
                    appendWinner(builder,scores,winner);
                }
            }
        }
        else {
            winner = moreThenOneActive(scores,winner);
            appendWinner(builder,scores,winner);
        }

        return builder.toString();

    }

    private Player moreThenOneActive(HashMap<Player,Integer> scores, Player winner){
        int max = -50;
        List<Player> drawPlayers = new ArrayList<>();

        for(Player player: this.myPlayers){
            int currentScore = scores.get(player);
            if(currentScore > max) {
                winner = player;
                drawPlayers.clear();
                max = currentScore;
            }
            else if(currentScore == max)
                drawPlayers.add(player);
        }

        if(drawPlayers.size() > 1) {
            return this.drawOnScore(drawPlayers);
        }
        else {
            return winner;
        }
    }

    /**
     * Find the winner if there's been a draw on points
     * @param drawPlayers The players that have the same points
     * @return The player designed as winner
     */
    private Player drawOnScore(List<Player> drawPlayers) {
        int maxDraw = 0;
        Player winner = new Player(NOONE);

        List<Player> drawPlayersOnPrivate = new ArrayList<>();

        for (Player player : drawPlayers) {
            int privatePoints = player.calculatePointsPrivate();

            if(privatePoints > maxDraw){
                winner = player;
                drawPlayersOnPrivate.clear();
                maxDraw = privatePoints;
            }
            else if(privatePoints == maxDraw)
                drawPlayersOnPrivate.add(player);
        }

        if(drawPlayersOnPrivate.size() > 1)
            return(this.drawOnPrivateScore(drawPlayersOnPrivate));

        return winner;

        }

    /**
     * Find winner if there's been a draw on PrivateScore
     * @param drawPlayersOnPrivate List of players that have the same points
     * @return The designed winner
     */
    private Player drawOnPrivateScore(List<Player> drawPlayersOnPrivate) {
        int maxFavors = 0;
        Player winner = new Player(NOONE);

        List<Player> drawPlayersOnFavors = new ArrayList<>();

        for(Player player : drawPlayersOnPrivate){
            int favorTokens = player.getMyFavTokens();

            if(favorTokens > maxFavors){
                winner = player;
                drawPlayersOnFavors.clear();
                maxFavors = favorTokens;
            }
            else if(favorTokens == maxFavors)
                drawPlayersOnFavors.add(player);
        }

        if(drawPlayersOnFavors.size() > 1) {
                return(this.drawOnFavors(drawPlayersOnFavors));
            }

            return winner;
    }

    /**
     * Find winner if there's been a draw on Favor tokens
     * @param drawPlayersOnFavors List of players drawing on favor tokens
     * @return The designed winner
     */
    private Player drawOnFavors(List<Player> drawPlayersOnFavors) {

        int max = 0;

        Player winner = new Player(NOONE);

        for (Player player : drawPlayersOnFavors) {
            if(this.myPlayers.indexOf(player) > max) {
                winner = player;
                max = this.myPlayers.indexOf(player);
            }
        }

        return winner;

    }

    /**
     * Method to add to builder the name and the points of the winner
     * @param builder The stringbuilder
     * @param scores The Hashmap that stores the score for every player
     * @param winner The winner
     */
    private void appendWinner(StringBuilder builder,HashMap<Player,Integer> scores,Player winner){
        builder.append(winner.getUsername());
        builder.append(SPACE);
        builder.append(scores.get(winner));
        builder.append(VIRG);
        scores.remove(winner);
    }

    /**
     * For every player in the table reset the "selected" value to every dice placed on the card, does this also for roundtrack and draftpool
     */
    public void resetSelection() {
        myPlayers.forEach(player -> player.getWindowPatternCard().resetSelection());
        roundTrack.resetSelection();
        draftPool.resetSelection();
    }
}