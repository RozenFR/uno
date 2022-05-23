package cardset.playcard;

import cardset.card.*;
import user.Game;
import user.UserException;

public class PlayCardPlusFour extends PlayCard {

    public PlayCardPlusFour(String name) {
        super(name);
    }

    /**
     * @param card a card
     * @return if know card true
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
        PlusFour pf = (PlusFour) card;
        pf.setEffect();
        Game.getPile().addCard(pf);
        Game.getCurrentPlayer().getHand().removeCard(pf);
    }

    /**
     * @param topcard
     * @param card
     */
    @Override
    void topcardIsPlusTwo(PlusTwo topcard, ICard card) {
        PlusFour pf = (PlusFour) card;
        pf.setEffect();
        Game.getPile().addCard(pf);
        Game.getCurrentPlayer().getHand().removeCard(pf);
    }

    /**
     * @param topcard
     * @param card
     */
    @Override
    void topcardIsPlusFour(PlusFour topcard, ICard card) {
        PlusFour pf = (PlusFour) card;
        pf.setEffect();
    }

    /**
     * @param topcard
     * @param card
     * @throws UserException
     */
    @Override
    void topcardIsSkip(Skip topcard, ICard card) throws UserException {
        PlusFour pf = (PlusFour) card;
        pf.setEffect();
        Game.getPile().addCard(pf);
        Game.getCurrentPlayer().getHand().removeCard(pf);
    }

    /**
     * @param topcard
     * @param card
     * @throws UserException
     */
    @Override
    void topcardIsReverse(Reverse topcard, ICard card) throws UserException {
        PlusFour pf = (PlusFour) card;
        pf.setEffect();
        Game.getPile().addCard(pf);
        Game.getCurrentPlayer().getHand().removeCard(pf);
    }

    /**
     * @param topcard
     * @param card
     * @throws UserException
     */
    @Override
    void topcardIsChangeColor(ChangeColor topcard, ICard card) throws UserException {
        PlusFour pf = (PlusFour) card;
        pf.setEffect();
        Game.getPile().addCard(pf);
        Game.getCurrentPlayer().getHand().removeCard(pf);
    }
}
