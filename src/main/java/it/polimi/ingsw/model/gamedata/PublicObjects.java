package it.polimi.ingsw.model.gamedata;



import it.polimi.ingsw.model.gamedata.gametools.*;

import java.util.List;

/**
 * This class is used by each player to "see" the other's players stuff, except the private objective card and the common objects like the
 * draft pool or roundtrack
 */
public class PublicObjects {
    private List<Player> players;
    private List<ObjectiveCard> objectiveCards;
    private List<ToolCard> toolCards;
    private RoundTrack roundTrack;
    private DraftPool draftPool;

    /**
     *
     * @param draftPool draft pool to set
     */
    public void setDraftPool(DraftPool draftPool) {
        this.draftPool = draftPool;
    }

    /**
     *
     * @param objectiveCards objective card to set
     */
    public void setObjectiveCards(List<ObjectiveCard> objectiveCards) {
        this.objectiveCards = objectiveCards;
    }

    /**
     *
     * @param players other players
     */
    public void setOthersPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     *
     * @param toolCards all tool cards
     */
    public void setToolCards(List<ToolCard> toolCards) {
        this.toolCards = toolCards;
    }

    /**
     *
     * @param roundTrack round track to set
     */
    public void setRoundTrack(RoundTrack roundTrack){this.roundTrack = roundTrack;}

    /**
     *
     * @return objective cards
     */
    public List<ObjectiveCard> getObjectiveCards() {
        return objectiveCards;
    }

    /**
     *
     * @return other players
     */
    public List<Player> getPlayers() {
        return players;
    }


    /**
     *
     * @return all tool card of the match
     */
    public List<ToolCard> getToolCards() {
        return toolCards;
    }

    /**
     *
     * @return draft pool
     */
    public DraftPool getDraftPool() {
        return draftPool;
    }

    /**
     *
     * @return round track
     */
    public RoundTrack getRoundTrack() {
        return roundTrack;
    }
}
