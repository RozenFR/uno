package cardset.card;

import java.util.Objects;

public class CardNumber implements ICard {

    private int value;
    private ECardColor color;

    public CardNumber(int value, ECardColor color) {
        setValue(value);
        setColor(color);
    }

    /**
     * Set value of card
     * @param value Value of card
     */
    private void setValue(int value) {
        this.value = value;
    }

    /**
     * Set Color of card
     * @param color color of card
     */
    private void setColor(ECardColor color) {
        this.color = color;
    }

    /**
     * Get value of card
     * @return value of card
     */
    public int getValue() {
        return value;
    }

    /**
     * Get the type of card
     * @return ECardType.Number
     */
    @Override
    public ECardType getType() {
        return ECardType.Number;
    }

    /**
     * get color of card
     * @return Color of the card
     */
    @Override
    public ECardColor getColor() {
        return this.color;
    }

    @Override
    public String toString() {
        return "CardNumber{" +
                "value=" + value +
                ", color=" + color +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardNumber that = (CardNumber) o;
        return value == that.value && color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, color);
    }
}
