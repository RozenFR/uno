package user;

import cardset.CardException;
import cardset.Deck;
import cardset.Pile;
import parse.FileProcess;

import java.io.IOException;
import java.util.ArrayList;

public class Game {

    private static boolean direction;
    private static int nbPlayer;
    private static int roundOfPlayer; // 0 to nbPlayer - 1
    private static ArrayList<Player> players;
    private static Deck deck;
    private static Pile pile;

    private static int cumulCounter = 0;

    /**
     * Constructor used for test and interface
     */
    public Game() { // Used for test
        Game.players = new ArrayList<>();
        Game.deck = new Deck();
        Game.pile = new Pile();
        Game.setDirection(true);
        Game.setRoundOfPlayer(0);
        Game.setNbPlayer(0);
    }

    /**
     * Initialize the pile
     */
    private void initPile() {
        this.pile = new Pile();
    }

    /**
     * Method to get the pile
     * @return the pile
     */
    public static Pile getPile() {
        return Game.pile;
    }

    /**
     * create a player with a name and add to player's list Command Inline use only
     * @param name name of the player
     */
    public static void addPlayer(String name) {
        Player player = new Player(name);
        Game.getPlayers().add(player);
        Game.setNbPlayer(getNbPlayer() + 1);
    }

    /**
     * Remove a player from the player's list
     * @param name
     */
    public static void removePlayer(String name) {
        for(Player player : getPlayers()) {
            if (player.getName().equals(name)) {
                getPlayers().remove(player);
                setNbPlayer(getNbPlayer() - 1);
            }
        }
    }

    /**
     * get the game's players
     * @return List of player
     */
    public static ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * get a player with his name
     * @param name
     * @return
     */
    public static Player getPlayer(String name) {
        for (Player player : getPlayers())
            if (player.getName() == name)
                return player;
        return null;
    }

    /**
     * get the player who has to play
     * @return current player
     */
    public static Player getCurrentPlayer() {
        return getPlayers().get(getRoundOfPlayer());
    }

    /**
     * Get the previous player
     * @return previous player
     */
    public static Player getPreviousPlayer() {
        Player player;
        if (direction) { // Clockwise
            if (getRoundOfPlayer() - 1 < 0)
                player = getPlayers().get(getNbPlayer() - 1);
            else
                player = getPlayers().get(getRoundOfPlayer() - 1);
        } else { // Anti-Clockwise
            if (getRoundOfPlayer() + 1 > getNbPlayer() - 1)
                player = getPlayers().get(0);
            else
                player = getPlayers().get(getRoundOfPlayer() + 1);
        }
        return player;
    }
    /**
     * Get the previous player
     * @return previous player
     */
    public static Player getNextPlayer() {
        Player player;
        if (!direction) { // Anticlockwise
            if (getRoundOfPlayer() - 1 < 0)
                player = getPlayers().get(getNbPlayer() - 1);
            else
                player = getPlayers().get(getRoundOfPlayer() - 1);
        } else { // Clockwise
            if (getRoundOfPlayer() + 1 > getNbPlayer() - 1)
                player = getPlayers().get(0);
            else
                player = getPlayers().get(getRoundOfPlayer() + 1);
        }
        return player;
    }

    /**
     * Intialize a list of cards with a file
     * @param filename target's file to generate cards
     */
    private void initDeck(String filename) {
        try {
            FileProcess.readFile(filename, getDeck());
            try {
                getDeck().shuffle();
            } catch (CardException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the deck
     * @return the deck
     */
    public static Deck getDeck() {
        return deck;
    }

    /**
     * is deck empty ? No use
     * @return true if deck don't have any cards
     */
    public boolean deckIsEmpty() {
        if (getDeck().getCards().isEmpty())
            return true;
        return false;
    }

    /**
     * set a counter for cumulative cards
     * @param cumulCounter number of cumulation in current pile
     */
    public static void setCumulCounter(int cumulCounter) {
        Game.cumulCounter = cumulCounter;
    }

    /**
     * Get the cumulation of card
     * @return counter of cumulation
     */
    public static int getCumulCounter() {
        return cumulCounter;
    }

    /**
     * set direction of the game :
     * - clockwise (true)
     * - anticlockwise (false)
     * @param direction direction of the game
     */
    public static void setDirection(boolean direction) {
        Game.direction = direction;
    }

    /**
     * Get the direction of the game
     * @return direction of the game
     */
    public static boolean getDirection() {
        return direction;
    }

    /**
     * Set number of player in the game
     * @param nbPlayer Number of player
     */
    private static void setNbPlayer(int nbPlayer) {
        Game.nbPlayer = nbPlayer;
    }

    /**
     * Get the number of player in the game
     * @return number of player in the game
     */
    public static int getNbPlayer() {
        return nbPlayer;
    }

    /**
     * Set the round of current player
     * @param roundOfPlayer round of player
     */
    public static void setRoundOfPlayer(int roundOfPlayer) {
        Game.roundOfPlayer = roundOfPlayer;
    }

    /**
     * get the round of a player
     * @return player's round
     */
    public static int getRoundOfPlayer() {
        return Game.roundOfPlayer;
    }

    @Override
    public String toString() {
        return "Game{" +
                "direction=" + direction +
                ", nbPlayer=" + nbPlayer +
                ", players=" + players +
                ", deck=" + deck +
                ", pile=" + pile +
                '}';
    }

}
