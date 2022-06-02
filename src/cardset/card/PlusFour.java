package cardset.card;

import user.Game;
import user.Player;
import user.UserException;

public class PlusFour extends CardSpecial {

    public PlusFour() {
        setColor(ECardColor.None);
    }

    public PlusFour(ECardColor color) {
        setColor(color);
    }

    /**
     * Set the effect of a plus four card
     */
    @Override
    public void setEffect() {

        // Setup
        Player current = Game.getCurrentPlayer();

        // Condition
        if (Game.getCumulCounter() != 0) { // Player has played another card but counter is not 0
            try {
                current.pickedCardWithi(4);
            } catch (UserException e) {
                throw new RuntimeException(e);
            }
            Game.setCumulCounter(0);

            Game.getCurrentPlayer().nextRound();
        }
    }

    /**
     * Get the card type
     * @return ECardType.PlusFour
     */
    public ECardType getType() {
        return ECardType.PlusFour;
    }


}
