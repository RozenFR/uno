package cardset.card;

import user.Game;
import user.Player;

import java.util.Objects;

public abstract class CardSpecial implements ICard {

    private ECardColor color;

    abstract void setEffect(Game game);

    /**
     * Set color of the card
     * @param color color of the card
     */
    public void setColor(ECardColor color) {
        if (color == null)
            throw new IllegalArgumentException("color is null");
        this.color = color;
    }

    /**
     * Get the color of the card
     * @return color of the card
     */
    @Override
    public ECardColor getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardSpecial that = (CardSpecial) o;
        return color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
