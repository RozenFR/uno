package cardset;

import cardset.card.ICard;
import user.Game;
import user.Player;
import user.UserException;

import java.util.ArrayList;
import java.util.Collections;

public class Deck implements ICardSet {

    private int nbCard = 0;
    private ArrayList<ICard> cards = new ArrayList<>();

    public Deck() {

    }

    private void setNbCard(int nbCard) {
        this.nbCard = nbCard;
    }

    private void setCards(ArrayList<ICard> cards) {
        this.cards = cards;
    }

    public int getNbCard() {
        return nbCard;
    }

    public ArrayList<ICard> getCards() {
        return cards;
    }

    public void shuffle() throws CardException {
        if (cards == null)
            throw new CardException("ArrayList cards is empty.");
        Collections.shuffle(getCards());
    }

    public void pileToDeck(Pile pile) {
        if (pile == null)
            throw new IllegalArgumentException("pile is null");
        if (pile.getCards().isEmpty())
            throw new IllegalArgumentException("pile:cards is empty.");
        for (ICard card : pile.getCards()) {
            getCards().add(card);
            pile.getCards().remove(card);
        }
    }

    public void giveCardToPlayer(Game game, Player player) throws UserException {
        if (player == null)
            throw new IllegalArgumentException("player is null");
        if (player.isHasPicked())
            throw new UserException("Player has already picked a card.");
        if (player.getHasPlayed())
            throw new UserException("User already played.");
        if (!game.getPlayers().get(game.getRoundOfPlayer()).equals(player))
            throw new UserException("It's not User round to take card.");

        if (player.isUnoStatus())
            player.setUnoStatus(false);

        player.getHand().addCard(giveCard());
        player.isHasPicked(true);
    }

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

    public boolean hasCard(ICard card) {
        if (card == null)
            throw new IllegalArgumentException("card is null");
        for (ICard gcard : getCards()) {
            if (gcard.equals(card))
                return true;
        }
        return false;
    }

    public ICard giveCard() {
        ICard card = getCards().get(0);
        getCards().remove(0);
        return card;
    }

    /**
     * @param card
     */
    @Override
    public void addCard(ICard card) {
        if (card == null)
            throw new IllegalArgumentException("card is null.");
        getCards().add(card);
    }

    /**
     * @param card
     */
    @Override
    public void removeCard(ICard card) {
        if (card == null)
            throw new IllegalArgumentException("card is null.");
        getCards().remove(card);
    }
}
