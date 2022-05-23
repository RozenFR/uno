package parse;

import cardset.card.ICard;
import cardset.card.PlusTwo;

public class ParseCardPlusTwo extends ParseCard {

    public ParseCardPlusTwo(String name) {
        super(name);
    }

    @Override
    boolean knowParse(String str) {
        return str.contains("CartePlus2");
    }

    @Override
    ICard applyParse(String str) {
        if (str == null || str.isEmpty())
            throw new IllegalArgumentException("str is null or empty");
        String[] prstr = str.split(";");
        return new PlusTwo(strToColor(prstr[1]));
    }
}
