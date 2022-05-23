package parse;

import cardset.card.ChangeColor;
import cardset.card.ECardColor;
import cardset.card.ICard;

public class ParseCardChangeColor extends ParseCard {

    public ParseCardChangeColor(String name) {
        super(name);
    }

    /**
     * @param str
     * @return
     */
    @Override
    boolean knowParse(String str) {
        return str.contains("CarteCouleur");
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
        return new ChangeColor(ECardColor.None);
    }
}
