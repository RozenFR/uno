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


    public Game() { // Used for test
        this.players = new ArrayList<>();
        this.deck = new Deck();
        this.pile = new Pile();
    }

    public Game(int nbPlayer, String filename) {
        initDeck(filename);
        initPlayer(nbPlayer);
        initPile();
    }

    private void initPile() {
        this.pile = new Pile();
    }

    public Pile getPile() {
        return pile;
    }

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

    public void addPlayer(String name) {
        Player player = new Player(name);
        getPlayers().add(player);
        setNbPlayer(getNbPlayer() + 1);
    }

    public void removePlayer(String name) {
        for(Player player : getPlayers()) {
            if (player.getName().equals(name)) {
                getPlayers().remove(player);
                setNbPlayer(getNbPlayer() - 1);
            }
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return getPlayers().get(getRoundOfPlayer());
    }

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

    public Deck getDeck() {
        return deck;
    }

    public void deckIsEmpty() {

    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public boolean getDirection() {
        return direction;
    }

    private void setNbPlayer(int nbPlayer) {
        this.nbPlayer = nbPlayer;
    }

    public int getNbPlayer() {
        return nbPlayer;
    }

    public void setRoundOfPlayer(int roundOfPlayer) {
        this.roundOfPlayer = roundOfPlayer;
    }

    public int getRoundOfPlayer() {
        return roundOfPlayer;
    }
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
