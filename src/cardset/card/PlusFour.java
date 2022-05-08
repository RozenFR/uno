package cardset.card;

import user.Game;
import user.Player;

import java.util.Scanner;

public class PlusFour extends CardSpecial {

    public PlusFour(ECardColor color) {
        setColor(ECardColor.None);
    }

    /**
     * Set the effect of a plus four card
     * @param game game currently playing
     */
    @Override
    void setEffect(Game game) {
        if (game == null)
            throw new IllegalArgumentException("game is null.");

        // Setup
        Player current = game.getCurrentPlayer();

        // Condition
        if (game.getCumulCounter() != 0) { // Player has played another card but counter is not 0
            game.getDeck().giveCardToPlayerWithi(current, 4);
            game.setCumulCounter(0);
            game.nextRound();
        }
    }

    /**
     * Get the card type
     * @return ECardType.PlusFour
     */
    @Override
    public ECardType getType() {
        return ECardType.PlusFour;
    }
}
