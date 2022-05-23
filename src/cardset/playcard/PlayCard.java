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

    public PlayCard setNext(PlayCard next) {
        this.next = next;
        return next;
    }

    public PlayCard getNext() {
        return next;
    }

    abstract boolean knowCard(ICard card);

    void applyCard(ICard card) throws UserException {
        if (!Game.getCurrentPlayer().hasCard(card))
            throw new UserException("Player " + Game.getCurrentPlayer().toString() + "don't possess the card " + card.toString() + ".");
        activateCardWithTopCard(card);
    }

    abstract void topcardIsNumber(CardNumber topcard, ICard card) throws UserException;
    abstract void topcardIsPlusTwo(PlusTwo topcard, ICard card) throws UserException;
    abstract void topcardIsPlusFour(PlusFour topcard, ICard card) throws UserException;
    abstract void topcardIsSkip(Skip topcard, ICard card) throws UserException;
    abstract void topcardIsReverse(Reverse topcard, ICard card) throws UserException;
    abstract void topcardIsChangeColor(ChangeColor topcard, ICard card) throws UserException;

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

    private void cardIsNull(ICard topcard) {
        switch (topcard.getType()) {
            case PlusTwo -> ((PlusTwo) topcard).setEffect();
            case PlusFour -> ((PlusFour) topcard).setEffect();
            case Skip -> ((Skip) topcard).setEffect();
            case Reverse -> ((Reverse) topcard).setEffect();
        }
    }

}
