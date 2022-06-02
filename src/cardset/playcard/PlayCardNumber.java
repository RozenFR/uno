package cardset.playcard;

import cardset.card.*;
import user.Game;
import user.UserException;

public class PlayCardNumber extends PlayCard {

    public PlayCardNumber(String name) {
        super(name);
    }

    /**
     * @param card
     * @return
     */
    @Override
    boolean knowCard(ICard card) {
        if (card instanceof CardNumber)
            return true;
        return false;
    }

    /**
     * @param topcard
     * @param card
     */
    @Override
    void topcardIsNumber(CardNumber topcard, ICard card) throws UserException {
        CardNumber num = (CardNumber) card;
        if (topcard.getValue() != num.getValue() && topcard.getColor() != num.getColor())
            throw new UserException(Game.getCurrentPlayer().toString() + " played a " + card.toString()
                    + " on top of " + topcard.toString());
        Game.getPile().addCard(num);
        Game.getCurrentPlayer().getHand().removeCard(num);
    }

    /**
     * @param card
     */
    @Override
    void topcardIsPlusTwo(PlusTwo topcard, ICard card) throws UserException {
        CardNumber num = (CardNumber) card;
        if (Game.getCumulCounter() != 0) {
            topcard.setEffect();
            throw new UserException("Player must play his +2");
        }
        Game.getPile().addCard(num);
        Game.getCurrentPlayer().getHand().removeCard(num);
    }

    /**
     * @param card
     */
    @Override
    void topcardIsPlusFour(PlusFour topcard, ICard card) throws UserException {
        CardNumber num = (CardNumber) card;
        if (topcard.getColor() != num.getColor())
            throw new UserException(Game.getCurrentPlayer().toString() + " played a " + card.toString()
                    + " on top of " + topcard.toString());
        Game.getPile().addCard(num);
        Game.getCurrentPlayer().getHand().removeCard(num);
    }

    /**
     * @param card
     */
    @Override
    void topcardIsSkip(Skip topcard, ICard card) throws UserException {
        CardNumber num = (CardNumber) card;
        if (topcard.getColor() != num.getColor())
            throw new UserException(Game.getCurrentPlayer().toString() + " played a " + card.toString()
                    + " on top of " + topcard.toString());
        Game.getPile().addCard(num);
        Game.getCurrentPlayer().getHand().removeCard(num);
    }

    /**
     * @param card
     */
    @Override
    void topcardIsReverse(Reverse topcard, ICard card) throws UserException {
        CardNumber num = (CardNumber) card;
        if (topcard.getColor() != num.getColor())
            throw new UserException(Game.getCurrentPlayer().toString() + " played a " + card.toString()
                    + " on top of " + topcard.toString());
        Game.getPile().addCard(num);
        Game.getCurrentPlayer().getHand().removeCard(num);
    }

    /**
     * @param card
     */
    @Override
    void topcardIsChangeColor(ChangeColor topcard, ICard card) throws UserException {
        CardNumber num = (CardNumber) card;
        if (topcard.getColor() != num.getColor())
            throw new UserException(Game.getCurrentPlayer().toString() + " played a " + card.toString()
                    + " on top of " + topcard.toString());
        Game.getPile().addCard(num);
        Game.getCurrentPlayer().getHand().removeCard(num);
    }
}
