package cardset.card;

import user.Game;
import user.Player;

import java.util.Scanner;

public class ChangeColor extends CardSpecial {

    public ChangeColor(ECardColor color) {
        setColor(color);
    }

    /**
     * Change the color according to player's will
     */
    @Override
    public void setEffect() {

    }

    /**
     * Get the type of the card
     * @return ECardType.ChangeColor
     */
    @Override
    public ECardType getType() {
        return ECardType.ChangeColor;
    }

}
