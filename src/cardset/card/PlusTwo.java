package cardset.card;

import user.Game;
import user.Player;
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

        // Setup
        Player current = Game.getCurrentPlayer();

        // Condition
        if (current.getFirstiCardByType(ECardType.PlusTwo) != null) { // Player play a +2 but next player has a +2
            Game.setCumulCounter(Game.getCumulCounter() + 1);
        } else if (Game.getCumulCounter() != 0) { // Player has played another card but counter is not 0
            try {
                Game.getCurrentPlayer().pickedCardWithi(2 * Game.getCumulCounter());
            } catch (UserException e) {
                throw new RuntimeException(e);
            }
            Game.setCumulCounter(0);
            current.nextRound();
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
