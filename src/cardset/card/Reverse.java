package cardset.card;

import user.Game;
import user.Player;

import java.util.Objects;

public class Reverse extends CardSpecial {

    private ECardColor color;

    public Reverse(ECardColor color) {
        setColor(color);
    }

    private void setColor(ECardColor color) {
        this.color = color;
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

    /**
     * @return
     */
    @Override
    public ECardColor getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Reverse{" +
                "color=" + color +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reverse reverse = (Reverse) o;
        return color == reverse.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
