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

    private void setNbCard(int nbCard) {
        this.nbCard = nbCard;
    }

    private void setCards() {
        this.cards = new ArrayList<>();
    }

    public int getNbCard() {
        return nbCard;
    }

    public ArrayList<ICard> getCards() {
        return cards;
    }

    public void takeCard(Deck deck) {
        if (deck == null)
            throw new IllegalArgumentException("deck is null.");
        getCards().add(deck.giveCard());
    }

    public void layCard(ICard card, Pile pile) {
        if (pile == null)
            throw new IllegalArgumentException("pile is null.");
        if (getCards().remove(card))
            pile.addCard(card);
    }

    /**
     * @param card
     */
    @Override
    public void addCard(ICard card) {
        if (card == null)
            throw new IllegalArgumentException("card is null.");
        getCards().add(card);
        setNbCard(getNbCard() + 1);
    }

    /**
     * @param card
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
