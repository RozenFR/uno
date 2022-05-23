package parse;

import cardset.card.ICard;
import cardset.card.PlusFour;

public class ParseCardPlusFour extends ParseCard {

    public ParseCardPlusFour(String name) {
        super(name);
    }

    @Override
    boolean knowParse(String str) {
        return str.contains("CartePlus4");
    }

    @Override
    ICard applyParse(String str) {
        if (str == null || str.isEmpty())
            throw new IllegalArgumentException("str is null or empty.");
        String[] prstr = str.split(";");
        return new PlusFour();
    }
}
