package cardset.card;

import user.Game;

import java.util.Objects;

public class Skip extends CardSpecial {

    private ECardColor color;

    public Skip(ECardColor color) {
        setColor(color);
    }

    private void setColor(ECardColor color) {
        this.color = color;
    }

    /**
     * Skip the round of a player depending if it is clockwise or anticlockwise
     */
    @Override
    public void setEffect(Game game) {
        game.nextRound();
    }

    /**
     * @return
     */
    @Override
    public ECardType getType() {
        return ECardType.Skip;
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
        return "Skip{" +
                "color=" + getColor() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skip skip = (Skip) o;
        return color == skip.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
