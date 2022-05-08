package cardset.card;

import cardset.CardException;
import cardset.Pile;
import user.Game;
import user.Player;
import user.UserException;

public class PlusTwo extends CardSpecial {

    public PlusTwo(ECardColor color) {
        setColor(color);
    }

    /**
     * @param game
     */
    @Override
    public void setEffect(Game game) {
        // Exception
        if (game == null)
            throw new IllegalArgumentException("game is null.");

        // Setup
        Player current = game.getCurrentPlayer();

        // Condition
        if (current.getCard(ECardType.PlusTwo) != null) { // Player play a +2 but next player has a +2
            game.setCumulCounter(game.getCumulCounter() + 1);
        } else if (game.getCumulCounter() != 0) { // Player has played another card but counter is not 0
            game.getDeck().giveCardToPlayerWithi(current, 2 * game.getCumulCounter());
            game.setCumulCounter(0);
            game.nextRound();
        }

    }

    /**
     * @return
     */
    @Override
    public ECardType getType() {
        return ECardType.PlusTwo;
    }

    @Override
    public String toString() {
        return "PlusTwo{" +
                "color=" + getColor() +
                '}';
    }

}
