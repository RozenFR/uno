package cardset.playcard;

import cardset.card.*;
import user.Game;
import user.UserException;

public class PlayCardSkip extends PlayCard {

    public PlayCardSkip(String name) {
        super(name);
    }

    /**
     * @param card
     * @return
     */
    @Override
    boolean knowCard(ICard card) {
        if (card instanceof Skip)
            return true;
        return false;
    }

    /**
     * @param topcard
     * @param card
     * @throws UserException
     */
    @Override
    void topcardIsNumber(CardNumber topcard, ICard card) throws UserException {
        Skip skip = (Skip) card;
        if (topcard.getColor() != skip.getColor())
            throw new UserException(Game.getCurrentPlayer().toString() + " played a " + card.toString()
                    + " on top of " + topcard.toString());
        skip.setEffect();
        Game.getPile().addCard(skip);
        Game.getCurrentPlayer().getHand().removeCard(skip);
    }

    /**
     * @param topcard
     * @param card
     */
    @Override
    void topcardIsPlusTwo(PlusTwo topcard, ICard card) {
        topcard.setEffect();
    }

    /**
     * @param topcard
     * @param card
     */
    @Override
    void topcardIsPlusFour(PlusFour topcard, ICard card) {
        topcard.setEffect();
    }

    /**
     * @param topcard
     * @param card
     * @throws UserException
     */
    @Override
    void topcardIsSkip(Skip topcard, ICard card) throws UserException {
        Skip skip = (Skip) card;
        skip.setEffect();
        Game.getPile().addCard(skip);
        Game.getCurrentPlayer().getHand().removeCard(skip);
    }

    /**
     * @param topcard
     * @param card
     * @throws UserException
     */
    @Override
    void topcardIsReverse(Reverse topcard, ICard card) throws UserException {
        Skip skip = (Skip) card;
        if (topcard.getColor() != skip.getColor())
            throw new UserException(Game.getCurrentPlayer().toString() + " played a " + card.toString()
                    + " on top of " + topcard.toString());
        skip.setEffect();
        Game.getPile().addCard(skip);
        Game.getCurrentPlayer().getHand().removeCard(skip);
    }

    /**
     * @param topcard
     * @param card
     * @throws UserException
     */
    @Override
    void topcardIsChangeColor(ChangeColor topcard, ICard card) throws UserException {
        Skip skip = (Skip) card;
        if (topcard.getColor() != skip.getColor())
            throw new UserException(Game.getCurrentPlayer().toString() + " played a " + card.toString()
                    + " on top of " + topcard.toString());
        skip.setEffect();
        Game.getPile().addCard(skip);
        Game.getCurrentPlayer().getHand().removeCard(skip);
    }
}
