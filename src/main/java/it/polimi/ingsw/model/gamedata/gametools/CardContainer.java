package it.polimi.ingsw.model.gamedata.gametools;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * This class is used as container for all the cards in the game and to read and to deal cards at the beginning of the match
 */
public class CardContainer {

    private List<Integer> pattern = new ArrayList<>(24);
    private List<Integer> objectiveprivate = new ArrayList<>(5);
    private List<Integer> objectivepublic = new ArrayList<>(10);
    private List<Integer> tools = new ArrayList<>(12);

    private boolean privateused = false;
    private boolean publicused = false;
    private boolean patternused = false;
    private boolean toolsused = false;

    private static final String NUMBER = "NUMBER";
    private static final String NAME = "NAME";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String TYPE = "TYPE";
    private static final String POINTS = "POINTS";
    private static final String V = "V";
    private static final String CANC = "#";
    private static final String COLOUR = "COLOUR";
    private static final String WHERE = "WHERE";
    private static final String PROPE = "PROP";
    private static final String VALUES = "VALUES";
    private static final String RULES = "RULES";
    private static final String AUTOMATED = "AUTOMATED";
    private static final String STATE = "STATE";
    private static final String CMETHODS = "CMETHODS";
    private static final String PMETHODS = "PMETHODS";
    private static final String DIFFICULTY = "DIFFICULTY";

    static final String XMLERROR = "Error while reading the xml configuration files";



    /**
     * The constructor fills the lists of the class
     */
    public CardContainer(){
        for(int i = 0; i < 24; i++) {
            pattern.add((i));
            if (i < 12) {
                tools.add(i);
                if (i < 10) {
                    objectivepublic.add(i);
                    if (i < 5)
                        objectiveprivate.add(i);
                }
            }
        }
    }

    /**
     * This method is used to pullOut the window pattern cards depending on the number of the players
     * @param numPlayers The number of the players present at the beginning of the match
     * @return The list with the cards extracted
     */
    public List<WindowPatternCard> pullOutPattern(int numPlayers) {
        int dimension = numPlayers*4;

        if(numPlayers < 1 || numPlayers > 4)
            throw new IndexOutOfBoundsException();

        ArrayList<WindowPatternCard> tmp = new ArrayList<>(dimension);

        if(this.patternused)
            throw new IllegalStateException();

        this.patternused = true;
        int randomNum;
        int cont = 24;
        Random rand = new Random();

        for (int k = 0; k < dimension; k++) {
            randomNum = rand.nextInt(cont - k);
            try {
                tmp.add(this.readPatterns("src/main/resources/window_pattern_cards_formalization.xml", pattern.get(randomNum)));
            } catch (ParserConfigurationException | IOException | SAXException e) {
                System.err.print(XMLERROR);
            }
            pattern.remove(randomNum);
        }
        return tmp;
    }

    /**
     * This method is used to pullOut the private cards depending on the number of the players
     * @param numPlayers The number of the players present at the beginning of the match
     * @return The list with the cards exctracted
     */
    public List<ObjectiveCard> pullOutPrivate(int numPlayers) {

        if(numPlayers < 1 || numPlayers > 4)
            throw new IndexOutOfBoundsException();

        ArrayList<ObjectiveCard> tmp = new ArrayList<>(numPlayers);

        if(this.privateused)
            throw new IllegalStateException();
        this.privateused = true;
        int randomNum;
        int cont = objectiveprivate.size();
        Random rand = new Random();
        for (int k = 0; k < numPlayers; k++) {
            randomNum = rand.nextInt(cont - k);
            try {
                tmp.add(this.readObjective("src/main/resources/private_cards_formalization.xml", objectiveprivate.get(randomNum))); //Supposing that z is the selected card from this "turn"
            } catch (ParserConfigurationException | SAXException | IOException e) {
                System.err.print(XMLERROR);
            }
            objectiveprivate.remove(randomNum);
        }
        return tmp;
    }

    /**
     * This method is used to pullOut the public cards
     * @return The list with the cards exctracted
     */
    public List<ObjectiveCard> pullOutPublic(){
        int dimension = 3;
        ArrayList<ObjectiveCard> tmp = new ArrayList<>(dimension);

        if(this.publicused)
            throw new IllegalStateException();
        this.publicused = true;
        int randomNum;
        int cont = objectivepublic.size();
        Random rand = new Random();
        for (int k = 0; k < dimension; k++) {
            randomNum = rand.nextInt(cont - k);
            try {
                tmp.add(this.readObjective("src/main/resources/public_cards_formalization.xml", objectivepublic.get(randomNum)));
            } catch (ParserConfigurationException | IOException | SAXException e) {
                System.err.print(XMLERROR);
            }
            objectivepublic.remove(randomNum);
        }
        return tmp;
    }

    /**
     * This method is used to pullOut the tool cards
     * @return The list with the cards exctracted
     */
    public List<ToolCard> pullOutTools() {
        int dimension = 3;

        ArrayList<ToolCard> tmp = new ArrayList<>(dimension);
        if(this.toolsused)
            throw new IllegalStateException();
        this.toolsused = true;
        int randomNum;
        int cont = this.tools.size();
        Random rand = new Random();
        for (int k = 0; k < dimension; k++) {
            randomNum = rand.nextInt(cont - k);
            try {
                tmp.add(this.readTools("src/main/resources/tool_cards_formalization.xml", tools.get(randomNum))); //Supposing that z is the selected card from this "turn"
            } catch (ParserConfigurationException | IOException | SAXException e) {
                System.err.print(XMLERROR);
            }
            tools.remove(randomNum);
        }
        return tmp;
    }


    /**
     * The method creates the ObjectiveCard element taking all the parameters from the xml configuration files
     * @param namefile The name of the file from where the method needs to read all the parameters
     * @param cont The number of the card I want to read
     * @return The Objective card requested
     */
    private ObjectiveCard readObjective(String namefile, int cont) throws ParserConfigurationException, IOException, SAXException {
        ObjectiveCard myobj = new ObjectiveCard();
        int result;
        String path = new File(namefile).getAbsolutePath();
        File file = new File(path);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.parse(file);

        myobj.setName(document.getElementsByTagName(NAME).item(cont).getTextContent());
        myobj.setDescription(document.getElementsByTagName(DESCRIPTION).item(cont).getTextContent());
        myobj.setType(document.getElementsByTagName(TYPE).item(cont).getTextContent());
        String input = document.getElementsByTagName(POINTS).item(cont).getTextContent();

        if(!(input.equals(V))) {
            if(input.equals(CANC))
                myobj.setPoints(1);
            else{
                result = Integer.valueOf(input);
                myobj.setPoints(result);
            }
            myobj.setID(Integer.valueOf(document.getElementsByTagName(NUMBER).item(cont).getTextContent()));
            this.readRulesPublic(document,myobj.getRules().getRules(),cont);
        }
        else {
            myobj.setID(Integer.valueOf(document.getElementsByTagName(NUMBER).item(cont).getTextContent()));
            myobj.setPoints(1);
            myobj.getRules().getRules().add(document.getElementsByTagName(COLOUR).item(cont).getTextContent());
        }
        return myobj;
    }


    /**
     * This method reads the rules of the card
     * @param document The document from whom the method needs to read
     * @param rules The ArrayList to fill with the rules of the public card
     * @param cont The number of the card that has to be read
     */
    private void readRulesPublic(Document document, List<String> rules, int cont){
        rules.add(document.getElementsByTagName(WHERE).item(cont).getTextContent());
        rules.add(document.getElementsByTagName(PROPE).item(cont).getTextContent());
        rules.add(document.getElementsByTagName(VALUES).item(cont).getTextContent());
        rules.add(document.getElementsByTagName(COLOUR).item(cont).getTextContent());
    }

    /**
     * The method creates the WindowPatternCard element taking all the parameters from the xml configuration files
     * @param namefile The name of the file from where the method needs to read all the parameters
     * @param cont The number of the card I want to read
     * @return The WindowPatternCard requested
     */
    private WindowPatternCard readPatterns(String namefile, int cont) throws ParserConfigurationException, IOException, SAXException {
        WindowPatternCard mypattern = new WindowPatternCard(50,10,"noName");
        String all;
        String path = new File(namefile).getAbsolutePath();
        File file = new File(path);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);

        mypattern.setNum(Integer.valueOf(document.getElementsByTagName(NUMBER).item(cont).getTextContent()));
        mypattern.setName(document.getElementsByTagName(NAME).item(cont).getTextContent());
        mypattern.setDifficulty(Integer.valueOf(document.getElementsByTagName(DIFFICULTY).item(cont).getTextContent()));
        all = document.getElementsByTagName(RULES).item(cont).getTextContent();
        List<String> rules = Arrays.asList(all.split(","));
        for (String rule:rules){
            mypattern.addRestrictions(rule.charAt(0),Character.getNumericValue(rule.charAt(1)),Character.getNumericValue(rule.charAt(2)));
        }

        return mypattern;
    }

    /**
     * The method creates the ToolCard element taking all the parameters from the xml configuration files
     * @param namefile The name of the file from where the method needs to read all the parameters
     * @param cont The number of the card I want to read
     * @return The ToolCard requested
     */
    ToolCard readTools(String namefile, int cont) throws ParserConfigurationException, IOException, SAXException {
        ToolCard mytool = new ToolCard();
        String all;
        String path = new File(namefile).getAbsolutePath();
        File file = new File(path);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.parse(file);

        mytool.setID(Integer.valueOf(document.getElementsByTagName(NUMBER).item(cont).getTextContent()));
        mytool.setName(document.getElementsByTagName(NAME).item(cont).getTextContent());

        all = document.getElementsByTagName(STATE).item(cont).getTextContent();
        mytool.setStateList(Arrays.asList(all.split(",")));

        all = document.getElementsByTagName(AUTOMATED).item(cont).getTextContent();
        mytool.setAutomatedOperationlist(Arrays.asList(all.split(",")));

        all = document.getElementsByTagName(CMETHODS).item(cont).getTextContent();
        mytool.setNameCMethods(Arrays.asList(all.split(",")));


        NodeList nodelist = document.getElementsByTagName(PMETHODS).item(cont).getChildNodes();

        for(int i = 1; i < nodelist.getLength() - 1; i = i+2){
            all = nodelist.item(i).getTextContent(); //here the counter i starts from 1 because the first element in the nodelist is not a real element
            mytool.getNamePMethods().add(Arrays.asList(all.split(",")));
        }

        mytool.setDescription((document.getElementsByTagName(DESCRIPTION).item(cont).getTextContent()));

        return mytool;
    }

}
