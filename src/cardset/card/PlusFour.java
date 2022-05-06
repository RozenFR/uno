package cardset.card;

import user.Game;

import java.util.Scanner;

public class PlusFour extends CardSpecial {

    private ECardColor colorPicked;

    public PlusFour(ECardColor color) {
        setColorPicked(ECardColor.None);
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
    void setEffect(Game game) {
        System.out.println("Quelle couleur souhaitez-vous ? Rouge / Bleu / Jaune / Vert");
        Scanner strColor = new Scanner(System.in);
        String str = strColor.nextLine();
        ECardColor color = strToColor(str);
        setColorPicked(color);
        if (game.getDirection()) {
            if (game.getRoundOfPlayer() == game.getNbPlayer()){
                game.getDeck().giveCardToPlayerWithi(game.getPlayers().get(0), 4);
                System.out.println(game.getPlayers().get(0).getName() + " received 4 cards.");
            }
            else {
                game.getDeck().giveCardToPlayerWithi(game.getPlayers().get(game.getRoundOfPlayer() + 1), 4);
                System.out.println(game.getPlayers().get(game.getRoundOfPlayer() + 1).getName() + " received 4 cards.");
            }
            if (game.getRoundOfPlayer() + 1 == game.getNbPlayer()) {
                game.setRoundOfPlayer(0);
            } else {
                game.setRoundOfPlayer(game.getRoundOfPlayer() + 1);
            }
        } else {
            if (game.getRoundOfPlayer() == 0){
                game.getDeck().giveCardToPlayerWithi(game.getPlayers().get(game.getNbPlayer() - 1), 4);
                System.out.println(game.getPlayers().get(0).getName() + " received 4 cards.");
            }
            else {
                game.getDeck().giveCardToPlayerWithi(game.getPlayers().get(game.getRoundOfPlayer() + 1), 4);
                System.out.println(game.getPlayers().get(game.getRoundOfPlayer() - 1).getName() + " received 4 cards.");
            }
            if (game.getRoundOfPlayer() - 1 < 0) {
                game.setRoundOfPlayer(game.getNbPlayer());
            } else {
                game.setRoundOfPlayer(game.getRoundOfPlayer() - 1);
            }
        }
    }

    /**
     * @return
     */
    @Override
    public ECardType getType() {
        return ECardType.PlusFour;
    }

    /**
     * @return
     */
    @Override
    public ECardColor getColor() {
        return getColorPicked();
    }
}
