package cardset.card;

import user.Game;
import user.Player;

import java.util.Scanner;

public class ChangeColor extends CardSpecial {

    private ECardColor colorPicked;

    public ChangeColor(ECardColor color) {
        setColorPicked(color);
    }

    public void setColorPicked(ECardColor colorPicked) {
        this.colorPicked = colorPicked;
    }

    public ECardColor getColorPicked() {
        return colorPicked;
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

    /**
     * @param game
     */
    @Override
    public void setEffect(Game game) {
        System.out.println("Quelle couleur souhaitez-vous ? Rouge / Bleu / Jaune / Vert");
        Scanner strColor = new Scanner(System.in);
        String str = strColor.nextLine();
        ECardColor color = strToColor(str);
        setColorPicked(color);
    }

    /**
     * @return
     */
    @Override
    public ECardType getType() {
        return ECardType.ChangeColor;
    }

    /**
     * @return
     */
    @Override
    public ECardColor getColor() {
        return colorPicked;
    }
}
