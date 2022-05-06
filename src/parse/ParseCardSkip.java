package parse;

import cardset.card.ICard;
import cardset.card.Skip;

public class ParseCardSkip extends ParseCardSpecial {

    public ParseCardSkip(String name) {
        super(name);
    }

    /**
     * @param str
     * @return
     */
    @Override
    boolean knowParse(String str) {
        return str.contains("CartePasser");
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
        return new Skip(strToColor(prstr[1]));
    }
}
