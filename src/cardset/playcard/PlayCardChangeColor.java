package cardset.playcard;

import cardset.card.*;
import user.Game;
import user.UserException;

public class PlayCardChangeColor extends PlayCard {

    public PlayCardChangeColor(String name) {
        super(name);
    }

    /**
     * @param card
     * @return
     */
    @Override
    boolean knowCard(ICard card) {
        if (card instanceof ChangeColor)
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
        ChangeColor cc = (ChangeColor) card;
        Game.getPile().addCard(cc);
        Game.getCurrentPlayer().getHand().removeCard(cc);
    }

    /**
     * @param topcard
     * @param card
     */
    @Override
    void topcardIsPlusTwo(PlusTwo topcard, ICard card) {
        ChangeColor cc = (ChangeColor) card;
        Game.getPile().addCard(cc);
        Game.getCurrentPlayer().getHand().removeCard(cc);
    }

    /**
     * @param topcard
     * @param card
     */
    @Override
    void topcardIsPlusFour(PlusFour topcard, ICard card) {
        ChangeColor cc = (ChangeColor) card;
        Game.getPile().addCard(cc);
        Game.getCurrentPlayer().getHand().removeCard(cc);
    }

    /**
     * @param topcard
     * @param card
     * @throws UserException
     */
    @Override
    void topcardIsSkip(Skip topcard, ICard card) throws UserException {
        ChangeColor cc = (ChangeColor) card;
        Game.getPile().addCard(cc);
        Game.getCurrentPlayer().getHand().removeCard(cc);
    }

    /**
     * @param topcard
     * @param card
     * @throws UserException
     */
    @Override
    void topcardIsReverse(Reverse topcard, ICard card) throws UserException {
        ChangeColor cc = (ChangeColor) card;
        Game.getPile().addCard(cc);
        Game.getCurrentPlayer().getHand().removeCard(cc);
    }

    /**
     * @param topcard
     * @param card
     * @throws UserException
     */
    @Override
    void topcardIsChangeColor(ChangeColor topcard, ICard card) throws UserException {
        ChangeColor cc = (ChangeColor) card;
        Game.getPile().addCard(cc);
        Game.getCurrentPlayer().getHand().removeCard(cc);
    }
}
