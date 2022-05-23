package user;

import cardset.CardException;
import cardset.Hand;
import cardset.Pile;
import cardset.card.*;
import cardset.playcard.*;

import java.util.Objects;

public class Player {

    private String name;
    private int victory;
    private Hand hand;
    private boolean hasPlayed = false;
    private boolean hasPicked = false;
    private boolean unoStatus = false;

    public Player(String name) {
        setName(name);
        setVictory(0);
        setHand();
    }

    /**
     * set a name to player
     * @throws IllegalArgumentException name is null
     * @param name name of the player
     */
    private void setName(String name) {
        if (name == null)
            throw new IllegalArgumentException("name can't be null.");
        this.name = name;
    }

    /**
     * get the name of a player
     * @return
     */
    public String getName() {
        return name;
    }

    private void setVictory(int victory) {
        this.victory = victory;
    }

    public int getVictory() {
        return victory;
    }

    private void setHand() {
        this.hand = new Hand();
    }

    public Hand getHand() {
        return hand;
    }

    public int getNbCard() {
        return hand.getNbCard();
    }

    public void addVictory() {
        setVictory(getVictory() + 1);
    }

    public void setHasPlayed(boolean hasPlayed) {
        this.hasPlayed = hasPlayed;
    }

    public boolean getHasPlayed() {
        return this.hasPlayed;
    }

    public void setHasPicked(boolean hasPicked) {
        this.hasPicked = hasPicked;
    }

    public boolean getHasPicked() {
        return this.hasPicked;
    }

    public void setUnoStatus(boolean unoStatus) {
        this.unoStatus = unoStatus;
    }

    public boolean isUnoStatus() {
        return this.unoStatus;
    }

    public boolean hasCard(ICard card) {
        if (card == null)
            throw new IllegalArgumentException("card is null");
        for (ICard gcard : getHand().getCards()) {
            if (gcard.equals(card))
                return true;
        }
        return false;
    }

    public ICard getiCard(int i) {
        if (getHand().getCards().size() - 1 > i && i < 0)
            throw new IllegalArgumentException("i out of range.");
        return getHand().getCards().get(i);
    }

    /**
     * Get the first card with the corresponding type
     * @param type type targeted
     * @return First card with type
     */
    public ICard getFirstiCardByType(ECardType type) {
        for (ICard gcard : getHand().getCards()) {
            if (gcard.getType() == type)
                return gcard;
        }
        return null;
    }

    /**
     * Get the last card with the corresponding type
     * @param type type targeted
     * @return Last card with type
     */
    public ICard getLastiCardByType(ECardType type) {
        ICard temp = null;
        for (ICard gcard : getHand().getCards()) {
            if (gcard.getType() == type)
                temp =  gcard;
        }
        return temp;
    }

    /**
     * Give a card to the player
     */
    public void pickedCard() throws UserException {

        if (!Game.getCurrentPlayer().equals(this))
            throw new UserException(this.getName() + "can't picked a card.");
        if (this.isUnoStatus())
            this.setUnoStatus(false);
        if (hasPicked)
            throw new UserException(getName() + " already took a card.");

        this.getHand().addCard(Game.getDeck().giveCard());
    }

    /**
     * Give a number of card to the player
     * @param i number of cards given
     */
    public void pickedCardWithi(int i) throws UserException {

        if (i < 1)
            throw new IllegalArgumentException("i is inferior to 1.");
        if (!Game.getCurrentPlayer().equals(this))
            throw new UserException(this.getName() + "can't picked a card.");
        if (this.isUnoStatus())
            this.setUnoStatus(false);

        for (int j = 0; j < i; j++) {
            this.getHand().addCard(Game.getDeck().giveCard());
        }
    }

    /**
     * Play a card in a game
     * @param card card played
     */
    public void playCard(ICard card) throws CardException, UserException {

        if (hasPlayed)
            throw new UserException(getName() + " already played.");

        // CoR to play card
        PlayCard number = new PlayCardNumber("Card Number");
        PlayCard changeColor = new PlayCardChangeColor("Card Change Color");
        PlayCard skip = new PlayCardSkip("Card Skip");
        PlayCard plusTwo = new PlayCardPlusTwo("Card Plus Two");

        // setup
        number.setNext(changeColor).setNext(skip).setNext(plusTwo);

        number.process(card);

        setHasPlayed(true);
        setHasPicked(true);
    }

    public void uno(Game game) throws UserException {
        if (!game.getCurrentPlayer().equals(this)) {
            game.getDeck().giveCardToPlayerWithi(this, 2);
            throw new UserException("User can't Uno, not current player.");
        }
        if (getNbCard() != 1)
            throw new UserException("User can't Uno.");
        setUnoStatus(true);
    }

    public void counterUno(Game game) throws UserException {
        Player previous = game.getPreviousPlayer();
        if (previous.isUnoStatus())
            throw new UserException("Player can't counter.");
        game.getPile().retrieveCardOnTop(previous);
        game.getDeck().giveCardToPlayerWithi(previous, 2);
    }

    public void nextRound() {

        int round = Game.getRoundOfPlayer();
        int nb_player = Game.getNbPlayer();

        if (Game.getDirection()) {
            if (round == nb_player - 1)
                Game.setRoundOfPlayer(0);
            else
                Game.setRoundOfPlayer(round + 1);
        } else {
            if (round == 0)
                Game.setRoundOfPlayer(nb_player - 1);
            else
                Game.setRoundOfPlayer(round - 1);
        }

        this.setHasPlayed(false);
        this.setHasPicked(false);

    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
