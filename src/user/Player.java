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

    public ICard getCard(ECardType type) {
        if (type == null)
            throw new IllegalArgumentException("type is null");
        for (ICard card : getHand().getCards())
            if (card.getType() == type)
                return card;
        return null;
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
     * Play a card in a game
     * @throws IllegalArgumentException game is null
     * @throws IllegalArgumentException card is null
     * @throws IllegalArgumentException pile is null
     * @throws UserException player don't have card (exception for CardNumber[-1, None])
     * @throws UserException Not the round of current player
     * @throws UserException Player already played
     * @param game game where we need to play the card
     * @param card card played
     */
    public void playCard(Game game, ICard card) throws UserException {
        Pile pile = game.getPile();

        // Exception
        if (game == null)
            throw new IllegalArgumentException("game is null.");
        if (card == null)
            throw new IllegalArgumentException("card is null.");
        if (pile == null)
            throw new IllegalArgumentException("pile is null.");
        if (!hasCard(card) && !card.equals(new CardNumber(-1, ECardColor.None)))
            throw new UserException("Player don't have this card.");
        if (!game.getPlayers().get(game.getRoundOfPlayer()).equals(this))
            throw new UserException("Player try to play a card when not its turn.");
        if (hasPlayed)
            throw new UserException("User Already played.");

        // Setup
        ICard topCard;
        try {
            topCard = game.getPile().getTopCard();
        } catch (CardException e) {
            throw new RuntimeException(e);
        }


        // Condition

        // Condition card with cumulation non equals to 0 and card non cumulable
        if (game.getCumulCounter() != 0 && card.getType() !=  topCard.getType()) {
            try {
                ICard icard = game.getPile().getTopCard();
                switch (icard.getType()) {
                    case PlusTwo :
                        PlusTwo plusTwo = (PlusTwo) icard;
                        plusTwo.setEffect(game);
                        break;
                    default:
                        break;
                }
            } catch (CardException e) {
                throw new RuntimeException(e);
            }
            setHasPlayed(true);
        }

        // Player didn't play
        if (!hasPlayed) {
            // Verify pile start if pile don't have any card just add card
            if (pile.getNbCard() == 0) {
                pile.addCard(card);
                getHand().removeCard(card);
            } else {
                switch (card.getType()) {
                    case Number :
                        CardNumber number = (CardNumber) card;
                        switch (topCard.getType()) {
                            case Number :
                                CardNumber numberPile = (CardNumber) topCard;
                                if (numberPile.getValue() != number.getValue() && numberPile.getColor() != number.getColor())
                                    throw new UserException("Number : Play a number different color and value.");
                                break;
                            case Skip:
                                if (topCard.getColor() != number.getColor())
                                    throw new UserException("Skip : Play a Number with different color.");
                                break;
                            case ChangeColor:
                                if (topCard.getColor() != card.getColor())
                                    throw new UserException("ChangeColor : Play a Number with wrong color");
                            default:
                                break;
                        }

                        pile.addCard(number);
                        getHand().removeCard(number);
                        break;
                    case Skip:
                        Skip skip = (Skip) card;
                        switch (topCard.getType()) {
                            case Number :
                                if (topCard.getColor() != skip.getColor())
                                    throw new UserException("Number : Play a Skip different color.");
                            default:
                                break;
                        }
                        skip.setEffect(game);
                        pile.addCard(skip);
                        getHand().removeCard(skip);
                        break;
                    case PlusTwo:
                        PlusTwo plustwo = (PlusTwo) card;
                        switch (topCard.getType()) {
                            case ChangeColor:
                                if (plustwo.getColor() != topCard.getColor())
                                    throw new UserException("ChangeColor : play +2 with wrong color");
                                break;
                        }
                        plustwo.setEffect(game);
                        pile.addCard(plustwo);
                        getHand().removeCard(plustwo);
                        break;
                    case ChangeColor:
                        ChangeColor changeColor = (ChangeColor) card;
                        changeColor.setEffect(game);
                        pile.addCard(changeColor);
                        getHand().removeCard(changeColor);
                        break;
                    default:
                        break;
                }
            }
            setHasPlayed(true);
        }
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
