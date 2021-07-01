package it.polimi.ingsw.model.gamedata.gametools;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.model.gamedata.gametools.CardContainer.XMLERROR;

/**
 * This class represents the ToolCards
 */
public class ToolCard extends Card {
    private boolean used;
    private int cost;
    private List<String> stateList;
    private List<String> automatedOperationlist = new ArrayList<>();
    private List<String> cMethods = new ArrayList<>();
    private List<List<String>> pMethods = new ArrayList<>();

    /**
     * The classic constructor
     */
    public ToolCard() {
        super();
        this.used = false;
        this.cost = 1;
    }

    /**
     * Method used to retrieve a specified Toolcard
     * @param i The card to retrieve
     * @return The card asked for
     */
    public ToolCard exactToolCard(int i){
        CardContainer cardContainer = new CardContainer();
        try {
            return cardContainer.readTools("src/main/resources/tool_cards_formalization.xml", i-1);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            System.err.print(XMLERROR);
        }
        return new ToolCard();
    }


    /**
     * Set a ToolCard as used so that its cost goes from 1 to 2
     */
    public void setUsed() {
        this.used = true;
        this.cost = 2;
    }

    /**
     * Says if a card has been used or not
     * @return If the card is used or not
     */
    public boolean isUsed() {
        return used;
    }

    /**
     * Getter for the cost of the card
     * @return The cost of the card
     */
    public int getCost() {
        return cost;
    }

    /**
     * Getter for the CMethods of the card
     * @return The CMethods of the card
     */
    public List<String> getNameCMethods(){
        return cMethods;
    }

    /**
     * Getter for the PMethods of the card
     * @return The PMethods of the card
     */
    public List<List<String>> getNamePMethods(){
        return pMethods;
    }

    /**
     * Getter for the AutomatedOperation of the card
     * @return The AutomatedOperation of the card
     */
    public List<String> getAutomatedoperationlist(){
        return automatedOperationlist;
    }

    /**
     * Getter for the State of the card
     * @return The State of the card
     */
    public List<String> getStateList(){
        return stateList;
    }


    /**
     * Setter for the CMethods of the card
     * @param x The methods to set
     */
    void setNameCMethods(List<String> x){
         this.cMethods = x;
    }

    /**
     * Setter for the PMethods of the card
     * @param x The methods to set
     */
    public void setNamePMethods(List<List<String>> x){
        this.pMethods = x;
    }

    /**
     * Setter for the AutomatedOperation of the card
     * @param x The AutomatedOperations to set
     */
    void setAutomatedOperationlist(List<String> x){
        this.automatedOperationlist = x;
    }

    /**
     * Setter for the State of the card
     * @param x The States to set
     */
    void setStateList(List<String> x){
        this.stateList = x;
    }

}
