package user;

import cardset.CardException;
import cardset.Deck;
import cardset.Pile;
import parse.FileProcess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private boolean direction = true;
    private int nbPlayer;
    private int roundOfPlayer = 0; // 0 to nbPlayer - 1
    private ArrayList<Player> players;
    private Deck deck;
    private Pile pile;

    private int cumulCounter = 0;

    /**
     * Constructor used for test
     */
    public Game() { // Used for test
        this.players = new ArrayList<>();
        this.deck = new Deck();
        this.pile = new Pile();
    }

    /**
     * Constructor generate with number of player and a file containing a list of cards
     * @param nbPlayer Number of player
     * @param filename Path to the file [Preference to absolute path]
     */
    public Game(int nbPlayer, String filename) {
        initDeck(filename);
        initPlayer(nbPlayer);
        initPile();
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
    public Pile getPile() {
        return pile;
    }

    /**
     * Initialize players with a number of player
     * @param nbPlayer Number of player to initialize
     */
    private void initPlayer(int nbPlayer) {
        this.players = new ArrayList<>();
        setNbPlayer(nbPlayer);
        for (int i = 0; i < nbPlayer; i++) {
            System.out.println("Joueur " + i + ", choisissez votre nom : ");
            Scanner answer = new Scanner(System.in);
            String name = answer.nextLine();
            Player player = new Player(name);
            getPlayers().add(player);
        }
    }

    /**
     * create a player with a name and add to player's list
     * @param name name of the player
     */
    public void addPlayer(String name) {
        Player player = new Player(name);
        getPlayers().add(player);
        setNbPlayer(getNbPlayer() + 1);
    }

    /**
     * Remove a player from the player's list
     * @param name
     */
    public void removePlayer(String name) {
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
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * get the player who has to play
     * @return current player
     */
    public Player getCurrentPlayer() {
        return getPlayers().get(getRoundOfPlayer());
    }

    /**
     * Get the previous player
     * @return previous player
     */
    public Player getPreviousPlayer() {
        Player player;
        if (direction) {
            if (getRoundOfPlayer() - 1 < 0)
                player = getPlayers().get(getNbPlayer() - 1);
            else
                player = getPlayers().get(getRoundOfPlayer() - 1);
        } else {
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
    public Deck getDeck() {
        return deck;
    }

    /**
     * is deck empty ?
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
    public void setCumulCounter(int cumulCounter) {
        this.cumulCounter = cumulCounter;
    }

    /**
     * Get the cumulation of card
     * @return counter of cumulation
     */
    public int getCumulCounter() {
        return cumulCounter;
    }

    /**
     * set direction of the game :
     * - clockwise (true)
     * - anticlockwise (false)
     * @param direction direction of the game
     */
    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    /**
     * Get the direction of the game
     * @return direction of the game
     */
    public boolean getDirection() {
        return direction;
    }

    /**
     * Set number of player in the game
     * @param nbPlayer Number of player
     */
    private void setNbPlayer(int nbPlayer) {
        this.nbPlayer = nbPlayer;
    }

    /**
     * Get the number of player in the game
     * @return number of player in the game
     */
    public int getNbPlayer() {
        return nbPlayer;
    }

    /**
     * Set the round of current player
     * @param roundOfPlayer round of player
     */
    public void setRoundOfPlayer(int roundOfPlayer) {
        this.roundOfPlayer = roundOfPlayer;
    }

    /**
     * get the round of a player
     * @return player's round
     */
    public int getRoundOfPlayer() {
        return roundOfPlayer;
    }

    /**
     * setup the next round
     */
    public void nextRound() {

        getPlayers().get(getRoundOfPlayer()).setHasPlayed(false);
        getPlayers().get(getRoundOfPlayer()).isHasPicked(false);

        if (direction) {
            if (getRoundOfPlayer() == getNbPlayer() - 1)
                setRoundOfPlayer(0);
            else
                setRoundOfPlayer(getRoundOfPlayer() + 1);
        } else {
            if (getRoundOfPlayer() == 0)
                setRoundOfPlayer(getNbPlayer() - 1);
            else
                setRoundOfPlayer(getRoundOfPlayer() - 1);
        }

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
