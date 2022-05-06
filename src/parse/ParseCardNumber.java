package parse;

import cardset.card.CardNumber;
import cardset.card.ECardColor;
import cardset.card.ICard;

public class ParseCardNumber extends ParseCard {

    public ParseCardNumber(String name) {
        super(name);
    }

    /**
     * @param str
     * @return true if str is CardNumber
     */
    @Override
    boolean knowParse(String str) {
        return str.contains("CarteSimple");
    }

    /**
     * @param str
     * @return CardNumber
     */
    @Override
    ICard applyParse(String str) {
        if (str == null || str.isEmpty())
            throw new IllegalArgumentException("str is null or empty.");
        String[] prstr = str.split(";");
        return new CardNumber(Integer.parseInt(prstr[2]), strToColor(prstr[1]));
    }
}
