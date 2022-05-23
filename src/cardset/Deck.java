package cardset;

import cardset.card.ICard;
import user.Game;
import user.Player;
import user.UserException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Deck implements ICardSet {

    private int nbCard = 0;
    private ArrayList<ICard> cards = new ArrayList<>();

    public Deck() {

    }

    /**
     * Set a number of card in deck
     * @throws IllegalArgumentException nbCard < 0
     * @param nbCard Number of card
     */
    private void setNbCard(int nbCard) {
        if (nbCard < 0)
            throw new IllegalArgumentException("NbCard inferior to 0.");
        this.nbCard = nbCard;
    }

    /**
     * Get the number of card in deck
     * @return The number of card in Deck
     */
    public int getNbCard() {
        return nbCard;
    }

    /**
     *  set a list of cards in deck
     * @param cards list of cards
     */
    private void setCards(ArrayList<ICard> cards) {
        this.cards = cards;
    }

    /**
     * Get the card's list
     * @return list of cards
     */
    public ArrayList<ICard> getCards() {
        return cards;
    }

    /**
     * Shuffle the deck
     * @throws CardException cards is null
     */
    public void shuffle() throws CardException {
        if (cards == null)
            throw new CardException("ArrayList cards is empty.");
        Collections.shuffle(getCards());
    }

    /**
     * Reload the deck with pile and shuffle it
     * @param pile targeted pile
     */
    public void pileToDeck(Pile pile) {
        if (pile == null)
            throw new IllegalArgumentException("pile is null");
        if (pile.getCards().isEmpty())
            throw new IllegalArgumentException("pile:cards is empty.");

        ICard lastCard = null;

        for (ICard card : pile.getCards()) {
            lastCard = card;
            getCards().add(card);
            pile.getCards().remove(card);
        }
        getCards().remove(lastCard);
        pile.addCard(lastCard);
        try {
            shuffle();
        } catch (CardException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Give a card and remove it from Deck
     * @return a card from the deck
     */
    public ICard giveCard() {
        ICard card = getCards().get(0);
        getCards().remove(0);
        return card;
    }

    /**
     * Give a card to a player
     * @param game game played
     * @param player player targeted
     * @throws IllegalArgumentException game is null
     * @throws IllegalArgumentException player is null
     * @throws UserException player already picked a card
     * @throws UserException player already played.
     * @throws UserException Not player to play
     */
    public void giveCardToPlayer(Game game, Player player) throws UserException {
        if (game == null)
            throw new IllegalArgumentException("game is null.");
        if (player == null)
            throw new IllegalArgumentException("player is null.");
        if (player.getHasPicked())
            throw new UserException("Player has already picked a card.");
        if (player.getHasPlayed())
            throw new UserException("User already played.");
        if (!game.getPlayers().get(game.getRoundOfPlayer()).equals(player))
            throw new UserException("It's not User round to take card.");

        if (player.isUnoStatus())
            player.setUnoStatus(false);

        player.getHand().addCard(giveCard());
        player.setHasPicked(true);
    }

    /**
     * Give a number of card to a player
     * @param player player targeted
     * @param i number of cards given
     */
    public void giveCardToPlayerWithi(Player player, int i) {
        if (player == null)
            throw new IllegalArgumentException("player is null");
        if (i < 1)
            throw new IllegalArgumentException("i is inferior to 1.");
        if (player.isUnoStatus())
            player.setUnoStatus(false);
        for (int j = 0; j < i; j++) {
            player.getHand().addCard(giveCard());
        }
    }

    /**
     * Presence of a specific card in deck
     * @param card Card targeted
     * @return true if card is in deck
     */
    public boolean hasCard(ICard card) {
        if (card == null)
            throw new IllegalArgumentException("card is null");
        for (ICard gcard : getCards()) {
            if (gcard.equals(card))
                return true;
        }
        return false;
    }

    /**
     * add a card in deck
     * @param card card to place in deck
     */
    @Override
    public void addCard(ICard card) {
        if (card == null)
            throw new IllegalArgumentException("card is null.");
        getCards().add(card);
    }

    /**
     * Remove a card from deck
     * @param card card to place in deck
     */
    @Override
    public void removeCard(ICard card) {
        if (card == null)
            throw new IllegalArgumentException("card is null.");
        getCards().remove(card);
    }

    @Override
    public String toString() {
        return "Deck{" +
                "nbCard=" + nbCard +
                ", cards=" + cards +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deck deck = (Deck) o;
        return nbCard == deck.nbCard && Objects.equals(cards, deck.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nbCard, cards);
    }
}
