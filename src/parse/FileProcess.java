package parse;

import cardset.Deck;
import cardset.card.ICard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileProcess {
    public static void readFile(String filename, Deck deck) throws IOException {
        if (filename == null)
            throw new IllegalArgumentException("filename is null.");

        File file = new File(filename);

        if (!file.isFile())
            throw new IllegalArgumentException("Unknown file.");

        BufferedReader reader = null;
        String line;

        // CdR
        ParseCard number = new ParseCardNumber("Card Number");
        ParseCard plusTwo = new ParseCardPlusTwo("Card Plus Two");
        ParseCard plusFour = new ParseCardPlusFour("Card Plus Four");
        ParseCard reverse = new ParseCardReverse("Card Reverse");
        ParseCard skip = new ParseCardSkip("Card Skip");
        ParseCard changeColor = new ParseCardChangeColor("Card Color Change");
        number.setNext(skip)
                .setNext(plusTwo)
                .setNext(plusFour)
                .setNext(reverse)
                .setNext(changeColor);


        try {
            reader = new BufferedReader(new FileReader(file));

            while ((line = reader.readLine()) != null) {
                try {
                    ICard card = number.process(line);
                    deck.addCard(card);
                }
                catch (ParseCardException e) {
                    System.err.println("Undefined parser for line : " + line);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
