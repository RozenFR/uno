package cardset;

import cardset.card.ECardType;
import cardset.card.ICard;

import java.util.ArrayList;
import java.util.Objects;

public class Hand implements ICardSet {

    private int nbCard;
    private ArrayList<ICard> cards;

    public Hand() {
        setNbCard(0);
        setCards();
    }

    /**
     * Set number of card
     * @param nbCard Number of card
     */
    private void setNbCard(int nbCard) {
        this.nbCard = nbCard;
    }

    /**
     * Set list of cards
     */
    private void setCards() {
        this.cards = new ArrayList<>();
    }

    /**
     * Get the number of cards in hand
     * @return number of cards in hand
     */
    public int getNbCard() {
        return nbCard;
    }

    /**
     * Get cards in Hand
     * @return list of cards
     */
    public ArrayList<ICard> getCards() {
        return cards;
    }

    /**
     * Pick a card from the game
     * @param deck deck from the game
     * @deprecated Manage in Deck Class
     */
    public void takeCard(Deck deck) {
        if (deck == null)
            throw new IllegalArgumentException("deck is null.");
        getCards().add(deck.giveCard());
    }

    /**
     * Lay a card on a pile
     * @param card card to put on pile
     * @param pile pile from the game
     * @deprecated Manage in Player
     */
    public void layCard(ICard card, Pile pile) {
        if (pile == null)
            throw new IllegalArgumentException("pile is null.");
        if (getCards().remove(card))
            pile.addCard(card);
    }

    /**
     * Add a card in Hand
     * @param card card to put in hand
     */
    @Override
    public void addCard(ICard card) {
        if (card == null)
            throw new IllegalArgumentException("card is null.");
        getCards().add(card);
        setNbCard(getNbCard() + 1);
    }

    /**
     * Remove a card from hand
     * @param card to remove from hand
     */
    @Override
    public void removeCard(ICard card) {
        if (card == null)
            throw new IllegalArgumentException("card is null.");
        getCards().remove(card);
        setNbCard(getNbCard() - 1);
    }

    @Override
    public String toString() {
        return "Hand{" +
                "nbCard=" + nbCard +
                ", cards=" + cards +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hand hand = (Hand) o;
        return nbCard == hand.nbCard && Objects.equals(cards, hand.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nbCard, cards);
    }
}
