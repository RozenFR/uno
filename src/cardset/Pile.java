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

    /**
     * Set cards in Pile
     */
    private void setCards() {
        this.cards = new ArrayList<>();
    }

    /**
     * Set number of card in pile
     * @param nbCard Number of card
     */
    private void setNbCard(int nbCard) {
        if (nbCard < 0)
            throw new IllegalArgumentException("nbCard inferior to 0.");
        this.nbCard = nbCard;
    }

    /**
     * Get cards in pile
     * @return a list of cards
     */
    public ArrayList<ICard> getCards() {
        return cards;
    }

    /**
     * Get number of card in pile
     * @return number of card in pile
     */
    public int getNbCard() {
        return nbCard;
    }

    /**
     * Get the card on top of the pile
     * @return Card on top of pile
     * @throws CardException No card in pile
     */
    public ICard getTopCard() throws CardException {
        if (getNbCard() == 0)
            throw new CardException("Pile don't have card.");
        return getCards().get(getNbCard() - 1);
    }

    /**
     * Verify if player has a card
     * @param card card to verify
     * @return true if have card then false
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
     * Retrieve the top card of the pile
     * <ul>
     *     <li>Counter Uno case</li>
     * </ul>
     * @param player player targeted by counter uno
     */
    public void retrieveCardOnTop(Player player) {
        try {
            player.getHand().addCard(getTopCard());
            removeCard(getTopCard());
        } catch (CardException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * add card on top of pile
     * @param card card to put on top
     */
    @Override
    public void addCard(ICard card) {
        if (card == null)
            throw new IllegalArgumentException("card is null.");
        getCards().add(card);
        setNbCard(getNbCard() + 1);
    }

    /**
     * remove card from pile
     * @param card card to remove
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
