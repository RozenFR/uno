package cardset.card;

import user.Game;
import user.Player;

import java.util.Objects;

public class Reverse extends CardSpecial {

    public Reverse(ECardColor color) {
        setColor(color);
    }

    /**
     * Set the effect of the card
     */
    @Override
    public void setEffect() {
        Game.setDirection(!Game.getDirection());
    }

    /**
     * Get the type of the card
     * @return ECardType.Reverse
     */
    @Override
    public ECardType getType() {
        return ECardType.Reverse;
    }

}
