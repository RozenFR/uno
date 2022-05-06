package parse;

import cardset.card.ICard;
import cardset.card.Reverse;

public class ParseCardReverse extends ParseCardSpecial {

    public ParseCardReverse(String name) {
        super(name);
    }

    /**
     * @param str
     * @return
     */
    @Override
    boolean knowParse(String str) {
        if (str == null || str.isEmpty())
            throw new IllegalArgumentException("str is null or empty.");
        return str.contains("CarteInversion");
    }

    /**
     * @param str
     * @return
     */
    @Override
    ICard applyParse(String str) {
        if (str == null || str.isEmpty())
            throw new IllegalArgumentException("str is null or empty.");
        String[] prstr = str.split(";");
        return new Reverse(strToColor(prstr[1]));
    }

}
