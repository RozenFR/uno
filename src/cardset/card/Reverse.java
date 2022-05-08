package cardset.card;

import user.Game;
import user.Player;

import java.util.Objects;

public class Reverse extends CardSpecial {

    public Reverse(ECardColor color) {
        setColor(color);
    }

    /**
     * @param game
     */
    @Override
    void setEffect(Game game) {
        game.setDirection(!game.getDirection());
    }

    /**
     * @return
     */
    @Override
    public ECardType getType() {
        return ECardType.Reverse;
    }

    @Override
    public String toString() {
        return "Reverse{" +
                "color=" + getColor() +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
