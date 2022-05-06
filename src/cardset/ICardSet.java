package cardset;

import cardset.card.ICard;

public interface ICardSet {
    void addCard(ICard card);
    void removeCard(ICard card);
}
