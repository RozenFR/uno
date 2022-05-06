package user;

import cardset.CardException;
import cardset.Hand;
import cardset.Pile;
import cardset.card.*;

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

    private void setName(String name) {
        this.name = name;
    }

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

    public void isHasPicked(boolean hasPicked) {
        this.hasPicked = hasPicked;
    }

    public boolean isHasPicked() {
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

    public ICard getFirstiCardByType(ICard card) {
        // TODO
        return null;
    }

    public ICard getLastiCardByType(ICard card) {
        // TODO
        return null;
    }

    public void playCard(Game game, ICard card) throws UserException {
        Pile pile = game.getPile();

        if (card == null)
            throw new IllegalArgumentException("card is null.");
        if (pile == null)
            throw new IllegalArgumentException("pile is null.");
        if (hasPlayed)
            throw new UserException("Player has already played.");
        if (!hasCard(card))
            throw new UserException("Player don't have this card.");
        if (!game.getPlayers().get(game.getRoundOfPlayer()).equals(this))
            throw new UserException("Player try to play a card when not its turn.");

        if (pile.getNbCard() == 0) {
            pile.addCard(card);
            getHand().removeCard(card);
        } else {
            switch (card.getType()) {
                case Number :
                    CardNumber number = (CardNumber) card;
                    try {
                        switch (game.getPile().getTopCard().getType()) { // TODO
                            case Number :
                                CardNumber numberPile = (CardNumber) game.getPile().getTopCard();
                                if (numberPile.getValue() != number.getValue() && numberPile.getColor() != number.getColor())
                                    throw new UserException("Number : Play a number different color and value.");
                                break;
                            case Skip:
                                Skip skipPile = (Skip) game.getPile().getTopCard();
                                if (skipPile.getColor() != number.getColor())
                                    throw new UserException("Number : Play a skip with different color.");
                                break;
                            default:
                                break;
                        }
                    } catch (CardException e) {
                        throw new RuntimeException(e);
                    }

                    pile.addCard(number);
                    getHand().removeCard(number);
                    break;
                case Skip:
                    Skip skip = (Skip) card;
                    try {
                        switch (game.getPile().getTopCard().getType()) { // TODO
                            case Number :
                                CardNumber numberPile = (CardNumber) game.getPile().getTopCard();
                                if (numberPile.getColor() != skip.getColor())
                                    throw new UserException("Skip : Play a number different color.");
                            default:
                                break;
                        }
                    } catch (CardException e) {
                        throw new RuntimeException(e);
                    }
                    skip.setEffect(game);
                    pile.addCard(skip);
                    getHand().removeCard(skip);
                    break;
                case ChangeColor:
                    break;
                default:
                    break;
            }
        }

        setHasPlayed(true);
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

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", hand=" + hand +
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
