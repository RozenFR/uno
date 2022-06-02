package cardset.playcard;

import cardset.CardException;
import cardset.card.*;
import user.Game;
import user.UserException;

public abstract class PlayCard {
    private String name;
    private PlayCard next;

    public PlayCard(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * set next chain from CoR
     * @param next
     * @return
     */
    public PlayCard setNext(PlayCard next) {
        this.next = next;
        return next;
    }

    /**
     * get next chain from CoR
     * @return
     */
    public PlayCard getNext() {
        return next;
    }

    /**
     * Know card or not
     * @param card
     * @return
     */
    abstract boolean knowCard(ICard card);

    /**
     * Proxy Method to activate card
     * @param card
     * @throws UserException
     */
    void applyCard(ICard card) throws UserException {
        if (!Game.getCurrentPlayer().hasCard(card))
            throw new UserException(Game.getCurrentPlayer().getName() + " don't possess the card " + card.toString() + ".");
        activateCardWithTopCard(card);
    }

    /**
     * Method used in case topcard from pile is a Number
     * @param topcard
     * @param card
     * @throws UserException
     */
    abstract void topcardIsNumber(CardNumber topcard, ICard card) throws UserException;

    /**
     * Method used in case topcard from pile is a PlusTwo
     * @param topcard
     * @param card
     * @throws UserException
     */
    abstract void topcardIsPlusTwo(PlusTwo topcard, ICard card) throws UserException;

    /**
     * Method used in case topcard from pile is a PlusFour
     * @param topcard
     * @param card
     * @throws UserException
     */
    abstract void topcardIsPlusFour(PlusFour topcard, ICard card) throws UserException;

    /**
     * Method used in case topcard from pile is a Skip
     * @param topcard
     * @param card
     * @throws UserException
     */
    abstract void topcardIsSkip(Skip topcard, ICard card) throws UserException;

    /**
     * Method used in case topcard from pile is a Reverse
     * @param topcard
     * @param card
     * @throws UserException
     */
    abstract void topcardIsReverse(Reverse topcard, ICard card) throws UserException;

    /**
     * Method used in case topcard from pile is a ChangeColor
     * @param topcard
     * @param card
     * @throws UserException
     */
    abstract void topcardIsChangeColor(ChangeColor topcard, ICard card) throws UserException;

    /**
     * Recursiv Method CoR for playing a card
     * @param card
     * @throws CardException
     * @throws UserException
     */
    public void process(ICard card) throws CardException, UserException {

        if (card == null) {
            cardIsNull(Game.getPile().getTopCard());
        } else {
            if (knowCard(card)) {
                applyCard(card);
            }
            else if (getNext() != null)
                getNext().process(card);
            else
                throw new CardException("Unknown Card.");
        }

    }

    /**
     * Method to activate effect and play card
     * @param card
     * @throws UserException
     */
    public void activateCardWithTopCard(ICard card) throws UserException {
        ICard topcard;
        try {
            topcard = Game.getPile().getTopCard();
        } catch (CardException e) {
            throw new RuntimeException(e);
        }

        switch (topcard.getType()) {
            case Number -> topcardIsNumber((CardNumber) topcard, card);
            case PlusTwo -> topcardIsPlusTwo((PlusTwo) topcard, card);
            case PlusFour -> topcardIsPlusFour((PlusFour) topcard, card);
            case Skip -> topcardIsSkip((Skip) topcard, card);
            case Reverse -> topcardIsReverse((Reverse) topcard, card);
            case ChangeColor -> topcardIsChangeColor((ChangeColor) topcard, card);
        }

    }

    /**
     * Case when card is null, set effect from card
     * @param topcard
     */
    private void cardIsNull(ICard topcard) {
        switch (topcard.getType()) {
            case PlusTwo -> ((PlusTwo) topcard).setEffect();
            case PlusFour -> ((PlusFour) topcard).setEffect();
            case Skip -> ((Skip) topcard).setEffect();
            case Reverse -> ((Reverse) topcard).setEffect();
        }
    }

}
