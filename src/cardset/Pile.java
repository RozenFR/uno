package cardset;

import cardset.card.ChangeColor;
import cardset.card.ICard;
import user.Player;

import java.util.ArrayList;
import java.util.Objects;

public class Pile implements ICardSet {

    private int nbCard = 0;
    private ArrayList<ICard> cards;

    public Pile() {
        setCards();
    }

    private void setCards() {
        this.cards = new ArrayList<>();
    }

    public ArrayList<ICard> getCards() {
        return cards;
    }

    private void setNbCard(int nbCard) {
        this.nbCard = nbCard;
    }

    public int getNbCard() {
        return nbCard;
    }

    public ICard getTopCard() throws CardException {
        if (getNbCard() == 0)
            throw new CardException("Pile don't have card.");
        return getCards().get(getNbCard() - 1);
    }

    public boolean hasCard(ICard card) {
        if (card == null)
            throw new IllegalArgumentException("card is null");
        for (ICard gcard : getCards()) {
            if (gcard.equals(card))
                return true;
        }
        return false;
    }

    public void retrieveCardOnTop(Player player) {
        try {
            player.getHand().addCard(getTopCard());
            removeCard(getTopCard());
        } catch (CardException e) {
            throw new RuntimeException(e);
        }
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
        return "Pile{" +
                "nbCard=" + nbCard +
                ", cards=" + cards +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pile pile = (Pile) o;
        return nbCard == pile.nbCard && Objects.equals(cards, pile.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nbCard, cards);
    }
}
