package parse;

import cardset.card.ECardColor;
import cardset.card.ICard;

import java.text.ParseException;

public abstract class ParseCard {

    private String name;
    private ParseCard next;

    public ParseCard(String name) {
        setName(name);
    }

    public ECardColor strToColor(String str) {
        switch (str) {
            case "Rouge":
                return ECardColor.Red;
            case "Bleu":
                return ECardColor.Blue;
            case "Vert":
                return ECardColor.Green;
            case "Jaune":
                return ECardColor.Yellow;
            default:
                return ECardColor.None;
        }
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ParseCard setNext(ParseCard next) {
        this.next = next;
        return next;
    }

    public ParseCard getNext() {
        return next;
    }

    abstract boolean knowParse(String str);
    abstract ICard applyParse(String str);

    public ICard process(String str) throws ParseException, ParseCardException {
        if (knowParse(str)) {
            return applyParse(str);}
        else if (getNext() != null)
            return getNext().process(str);
        else
            throw new ParseCardException("Unknown Card.");
    }

    @Override
    public String toString() {
        return "ParseCard{" +
                "name='" + name + '\'' +
                ", next=" + next +
                '}';
    }
}
