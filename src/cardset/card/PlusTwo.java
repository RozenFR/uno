package cardset.card;

import user.Game;

public class PlusTwo extends CardSpecial {

    private ECardColor color;

    public PlusTwo(ECardColor color) {
        setColor(color);
    }

    private void setColor(ECardColor color) {
        this.color = color;
    }

    /**
     * @param game
     */
    @Override
    void setEffect(Game game) {
        if (game.getDirection()) {
            if (game.getRoundOfPlayer() == game.getNbPlayer()){
                game.getDeck().giveCardToPlayerWithi(game.getPlayers().get(0), 2);
                System.out.println(game.getPlayers().get(0).getName() + " received 4 cards.");
            }
            else {
                game.getDeck().giveCardToPlayerWithi(game.getPlayers().get(game.getRoundOfPlayer() + 1), 2);
                System.out.println(game.getPlayers().get(game.getRoundOfPlayer() + 1).getName() + " received 4 cards.");
            }
        } else {
            if (game.getRoundOfPlayer() == 0){
                game.getDeck().giveCardToPlayerWithi(game.getPlayers().get(game.getNbPlayer() - 1), 2);
                System.out.println(game.getPlayers().get(0).getName() + " received 4 cards.");
            }
            else {
                game.getDeck().giveCardToPlayerWithi(game.getPlayers().get(game.getRoundOfPlayer() + 1), 2);
                System.out.println(game.getPlayers().get(game.getRoundOfPlayer() - 1).getName() + " received 4 cards.");
            }
        }
    }

    /**
     * @return
     */
    @Override
    public ECardType getType() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public ECardColor getColor() {
        return null;
    }

    @Override
    public String toString() {
        return "PlusTwo{" +
                "color=" + color +
                '}';
    }

}
