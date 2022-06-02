package cardset.card;

import user.Game;
import user.UserException;

public class PlusTwo extends CardSpecial {

    public PlusTwo(ECardColor color) {
        setColor(color);
    }

    /**
     * Set the effect of card
     */
    @Override
    public void setEffect() {

        /** Next player has a +2 */
        if (Game.getNextPlayer().getFirstiCardByType(ECardType.PlusTwo) != null) {
            Game.setCumulCounter(Game.getCumulCounter() + 1);
        } else { /** Next Player don't have a +2 */

            /* Player must take +2 card */
            try {
                Game.getNextPlayer().pickedCardWithi((Game.getCumulCounter() + 1) * 2);
            } catch (UserException e) {
                throw new RuntimeException(e);
            }

            /** Force next player to pass */
            Game.getNextPlayer().setHasPlayed(true);
            Game.getNextPlayer().setHasPicked(true);

            /** Reset CumulCounter */
            Game.setCumulCounter(0);
        }
    }

    /**
     * Get the type of the card
     * @return ECardType.PlusTwo
     */
    @Override
    public ECardType getType() {
        return ECardType.PlusTwo;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
