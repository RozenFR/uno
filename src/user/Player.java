package user;

import cardset.CardException;
import cardset.Hand;
import cardset.card.ECardType;
import cardset.card.ICard;
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

    /**
     * set Victory
     * @param victory
     */
    private void setVictory(int victory) {
        this.victory = victory;
    }

    /**
     * get Victory
     * @return
     */
    public int getVictory() {
        return victory;
    }

    /**
     * set empty Hand
     */
    private void setHand() {
        this.hand = new Hand();
    }

    /**
     * get Hand
     * @return
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * get Number of card in hand
     * @return number of card in hand
     */
    public int getNbCard() {
        return hand.getNbCard();
    }

    /**
     * add victory
     * # actually no use because application is single win = single application
     */
    public void addVictory() {
        setVictory(getVictory() + 1);
    }

    /**
     * set hasPlayed
     * @param hasPlayed
     */
    public void setHasPlayed(boolean hasPlayed) {
        this.hasPlayed = hasPlayed;
    }

    /**
     * get hasPlayed
     * @return
     */
    public boolean getHasPlayed() {
        return this.hasPlayed;
    }

    /**
     * set hasPicked
     * @param hasPicked
     */
    public void setHasPicked(boolean hasPicked) {
        this.hasPicked = hasPicked;
    }

    /**
     * get hasPicked
     * @return
     */
    public boolean getHasPicked() {
        return this.hasPicked;
    }

    /**
     * set Uno Status
     * @param unoStatus
     */
    public void setUnoStatus(boolean unoStatus) {
        this.unoStatus = unoStatus;
    }

    /**
     * is Uno Status
     * @return
     */
    public boolean isUnoStatus() {
        return this.unoStatus;
    }

    /**
     * If player has a specific card
     * @param card
     * @return
     */
    public boolean hasCard(ICard card) {
        if (card == null)
            throw new IllegalArgumentException("card is null");
        for (ICard gcard : getHand().getCards()) {
            if (gcard.equals(card))
                return true;
        }
        return false;
    }

    /**
     * Get card from hand with specific index
     * @param i index
     * @return
     */
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
            throw new UserException(this.getName() + " can't picked a card.");
        if (this.isUnoStatus())
            this.setUnoStatus(false);
        if (hasPicked)
            throw new UserException(getName() + " already took a card.");
        /* Reset deck */
        if (Game.getDeck().getNbCard() < 2) {
            try {
                Game.getDeck().pileToDeck();
            } catch (CardException e) {
                throw new RuntimeException(e);
            }
        }

        this.getHand().addCard(Game.getDeck().giveCard());

        setHasPicked(true);
    }

    /**
     * Give a number of card to the player
     * @param i number of cards given
     */
    public void pickedCardWithi(int i) throws UserException {

        if (i < 1)
            throw new IllegalArgumentException("i is inferior to 1.");
        if (this.isUnoStatus())
            this.setUnoStatus(false);

        /* Reset Deck */
        if (Game.getDeck().getNbCard() < i) {
            try {
                Game.getDeck().pileToDeck();
            } catch (CardException e) {
                throw new RuntimeException(e);
            }
        }

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

        if (Game.getDeck().getNbCard() < 4)
            Game.getDeck().pileToDeck();

        // CoR to play card
        PlayCard number = new PlayCardNumber("Card Number");
        PlayCard changeColor = new PlayCardChangeColor("Card Change Color");
        PlayCard skip = new PlayCardSkip("Card Skip");
        PlayCard plusTwo = new PlayCardPlusTwo("Card Plus Two");
        PlayCardPlusFour plusFour = new PlayCardPlusFour("Card Plus Four");

        // setup CoR
        number.setNext(changeColor).setNext(skip).setNext(plusTwo);

        /* CoR play card */
        number.process(card);

        setHasPlayed(true);
        setHasPicked(true);
    }

    public void uno() throws UserException {
        if (!Game.getCurrentPlayer().equals(this)) {
            Game.getDeck().giveCardToPlayerWithi(this, 2);
            throw new UserException(getName() + " can't Uno, not current player. Player is punished.");
        }
        if (getNbCard() != 1)
            throw new UserException(getName() + " can't Uno.");
        if (!hasPlayed)
            throw new UserException(getName() + "can't uno because didn't play a card.");

        setUnoStatus(true);
    }

    public void counterUno() throws UserException {
        Player previous = Game.getPreviousPlayer();
        if (previous.isUnoStatus())
            throw new UserException("Player can't counter.");
        Game.getPile().retrieveCardOnTop(previous);
        previous.pickedCardWithi(2);
    }

    public void nextRound() {

        int round = Game.getRoundOfPlayer();
        int nb_player = Game.getNbPlayer();

        if (Game.getDirection()) { // Clockwise
            if (round == nb_player - 1)
                Game.setRoundOfPlayer(0);
            else
                Game.setRoundOfPlayer(round + 1);
        } else { // Anticlockwise
            if (round == 0)
                Game.setRoundOfPlayer(nb_player - 1);
            else
                Game.setRoundOfPlayer(round - 1);
        }

        /* Reset player played and picked */
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
