package cardset.card;

import user.Game;

public class Skip extends CardSpecial {

    public Skip(ECardColor color) {
        setColor(color);
    }

    /**
     * Skip the round of a player depending if it is clockwise or anticlockwise
     */
    @Override
    public void setEffect() {

        Game.getCurrentPlayer().setHasPicked(true);
        Game.getCurrentPlayer().setHasPlayed(true);

    }

    /**
     * Get the type of the card
     * @return ECardType.Skip
     */
    @Override
    public ECardType getType() {
        return ECardType.Skip;
    }

}
