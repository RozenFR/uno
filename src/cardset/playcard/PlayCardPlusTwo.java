package cardset.playcard;

import cardset.card.*;
import user.Game;
import user.UserException;

public class PlayCardPlusTwo extends PlayCard {

    public PlayCardPlusTwo(String name) {
        super(name);
    }

    /**
     * @param card
     * @return
     */
    @Override
    boolean knowCard(ICard card) {
        if (card instanceof PlusTwo)
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
        PlusTwo pt = (PlusTwo) card;
        if (topcard.getColor() != pt.getColor())
            throw new UserException(Game.getCurrentPlayer().toString() + " played a " + card.toString()
                    + " on top of " + topcard.toString());
        pt.setEffect();
        Game.getPile().addCard(pt);
        Game.getCurrentPlayer().getHand().removeCard(pt);
    }

    /**
     * @param topcard
     * @param card
     */
    @Override
    void topcardIsPlusTwo(PlusTwo topcard, ICard card) {
        PlusTwo pt = (PlusTwo) card;
        pt.setEffect();
        Game.getPile().addCard(pt);
        Game.getCurrentPlayer().getHand().removeCard(pt);
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
        PlusTwo pt = (PlusTwo) card;
        if (topcard.getColor() != pt.getColor())
            throw new UserException(Game.getCurrentPlayer().toString() + " played a " + card.toString()
                    + " on top of " + topcard.toString());
        pt.setEffect();
        Game.getPile().addCard(pt);
        Game.getCurrentPlayer().getHand().removeCard(pt);
    }

    /**
     * @param topcard
     * @param card
     * @throws UserException
     */
    @Override
    void topcardIsReverse(Reverse topcard, ICard card) throws UserException {
        PlusTwo pt = (PlusTwo) card;
        if (topcard.getColor() != pt.getColor())
            throw new UserException(Game.getCurrentPlayer().toString() + " played a " + card.toString()
                    + " on top of " + topcard.toString());
        pt.setEffect();
        Game.getPile().addCard(pt);
        Game.getCurrentPlayer().getHand().removeCard(pt);
    }

    /**
     * @param topcard
     * @param card
     * @throws UserException
     */
    @Override
    void topcardIsChangeColor(ChangeColor topcard, ICard card) throws UserException {
        PlusTwo pt = (PlusTwo) card;
        if (topcard.getColor() != pt.getColor())
            throw new UserException(Game.getCurrentPlayer().toString() + " played a " + card.toString()
                    + " on top of " + topcard.toString());
        pt.setEffect();
        Game.getPile().addCard(pt);
        Game.getCurrentPlayer().getHand().removeCard(pt);
    }
}
