package test;

import cardset.CardException;
import cardset.card.*;
import org.junit.jupiter.api.*;
import parse.FileProcess;
import user.Game;
import user.Player;
import user.UserException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

public class UnoTest {

    Game Game;
    Player alice;
    Player bob;
    Player charles;

    @Nested
    class FirstTests {

        /**
         * Test of Card Number
         * Use of Deck : JeuTestCarteSimple.csv
         * Nb Card Per player : 3
         */

        private static final int NB_CARD = 3;

        @BeforeEach
        public void init() {

            /* Number of card distributed in game */
            int nb_card = 3;

            /* Create a New Game*/
            new Game();

            /* Setup the deck */
            try {
                FileProcess.readFile("external/JeuTestCarteSimple.csv", Game.getDeck());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            /* Add 3 player : Alice, Bob and Charles */
            Game.addPlayer("Alice");
            Game.addPlayer("Bob");
            Game.addPlayer("Charles");

            /* Setup number of card for each player */
            for (int i = 0; i < NB_CARD; i++) {
                for (Player player : Game.getPlayers()) {
                    Game.getDeck().giveCardToPlayerWithi(player, 1);
                }
            }
            /* Put first card on top of pile */
            ICard cardDeck = Game.getDeck().getCards().get(0);
            Game.getPile().addCard(cardDeck);
            Game.getDeck().removeCard(cardDeck);

            /* Global Shortcut for player */
            alice = Game.getPlayers().get(0);
            bob = Game.getPlayers().get(1);
            charles = Game.getPlayers().get(2);
        }

        @Nested
        class LegalMoveWithSimpleCardTests {
            @Test
            public void AlicePlayCardWithRightColorTest() {

                /* Alice est le joueur courrant */
                if (!Game.getCurrentPlayer().equals(alice))
                    fail("Alice is not the current player.");
                System.out.println("Alice is current player.");

                /* Alice a 3 cartes */
                if (alice.getNbCard() != 3)
                    fail("Alice doesn't have 3 cards.");
                System.out.println("Alice have 3 cards in her hand.");

                /* Alice joue le 2 Vert */
                try {
                    alice.playCard(alice.getHand().getCards().get(0));
                    System.out.println("Alice played 2 green.");
                } catch (CardException | UserException e) {
                    fail("Alice didn't played 2 green.");
                }

                /* Alice possède le 6 Jaune et le 1 Rouge */
                if (!alice.hasCard(new CardNumber(6, ECardColor.Yellow))
                        || !alice.hasCard(new CardNumber(1, ECardColor.Red)))
                    fail("Alice doesn't have 6 yellow or 1 red.");
                System.out.println("Alice have 6 yellow and 1 red.");

                /* Le TAS possède 2 cartes */
                if (Game.getPile().getNbCard() != 2)
                    fail("The pile is not updated");
                System.out.println("The pile has 2 cards.");

                /* Alice finit son tour */
                alice.nextRound();

                /* Bob est le joueur courrant */
                if (!Game.getCurrentPlayer().equals(bob))
                    fail("Bob is not current player.");
                System.out.println("Bob is current player.");
            }

            @Test
            public void BobPlayCardWithDifferentColorButSameValueTest() {

                /* Reprise de setup précédent */
                AlicePlayCardWithRightColorTest();

                /* Bob est le joueur courrant */
                if (!Game.getCurrentPlayer().equals(bob))
                    fail("The current player is not Bob.");
                System.out.println("Bob is the current player.");

                /* Bob possède 3 cartes */
                if (Game.getCurrentPlayer().getNbCard() != 3)
                    fail("Bob doesn't have 3 cards.");
                System.out.println("Bob have 3 cards in his hand.");

                /* Bob joue le 2 Bleu */
                try {
                    bob.playCard(bob.getHand().getCards().get(0));
                    System.out.println("Bob played 2 blue.");
                } catch (CardException | UserException e) {
                    fail("Bob didn't play 2 Blue.");
                }

                /* Bob possède le 4 Jaune et le 9 Rouge */
                if (!bob.hasCard(new CardNumber(4, ECardColor.Yellow))
                        || !bob.hasCard(new CardNumber(9, ECardColor.Red)))
                    fail("bob need to have 4 yellow and 9 Red.");
                System.out.println("Bob have 4 yellow and 9 red.");

                /* Le TAS possède 3 cartes. */
                if (Game.getPile().getNbCard() != 3)
                    fail("Pile don't have 3 cards.");
                System.out.println("Pile have 3 cards.");

                /* Bob finit son tour */
                bob.nextRound();

                /* Charles est le joueur courrant */
                if (!Game.getCurrentPlayer().equals(charles))
                    fail("Charles is not current player.");
                System.out.println("Charles is current player.");
            }
        }

        @Nested
        class IllegalMoveWithSimpleCardTests {

            @Test
            public void IllegalCardTest() {
                /* Alice joue le 6 Jaune */
                try {
                    alice.playCard(alice.getHand().getCards().get(1));
                    fail("Alice play 6 Yellow.");
                } catch (CardException | UserException e) {

                    System.out.println("Alice can't play 6 yellow.");

                    /* Le TAS ne contient pas le 6 Jaune */
                    if (Game.getPile().hasCard(new CardNumber(6, ECardColor.Yellow)))
                        fail("Alice played her 6 yellow.");
                    System.out.println("Alice couldn't play her 6 yellow.");

                    /* Alice possède le 6 Jaune */
                    if (!alice.hasCard(new CardNumber(6, ECardColor.Yellow)))
                        fail("Alice don't have 6 yellow in hand.");
                    System.out.println("Alice have 6 Yellow in Hand.");

                    /* Alice a 3 cartes */
                    if (alice.getNbCard() != 3)
                        fail("Alice don't have 3 cards.");
                    System.out.println("Alice have 3 cards.");

                }
            }

            @Test
            public void PlayTwoLegalCardInRow() {
                /* Alice pose le 2 Vert */
                try {
                    alice.playCard(alice.getiCard(0));

                    /* Alice finit sont tour */
                    Game.getCurrentPlayer().nextRound();

                    /* Alice possède 2 cartes */
                    if (alice.getNbCard() != 2 || alice.hasCard(new CardNumber(2, ECardColor.Green)))
                        fail("Alice don't have 2 card and have the 2 Green in Hand.");
                    System.out.println("Alice lay the 2 Green and pass.");

                    /* Bob joue le 2 Bleu */
                    bob.playCard(bob.getiCard(0));

                    /* Bob finit son tour */
                    bob.nextRound();

                    /* Bob possède 2 cartes */
                    if (bob.getNbCard() != 2 || bob.hasCard(new CardNumber(2, ECardColor.Blue)))
                        fail("Bob don't have 2 card in Hand and have the 2 Blue in Hand.");
                    System.out.println("Bob lay the 2 Blue and pass.");

                } catch (CardException | UserException e) {
                    fail("Somethings gone unexpectedly wrong.");
                }

                /* Charles pose le 6 Bleu */
                try {
                    charles.playCard(charles.getiCard(0));

                    /* Charles possède 2 cartes */
                    if (charles.getNbCard() != 2)
                        fail("Charles don't have 2 cards in Hand.");
                    System.out.println("Charles have 2 cards in Hand.");

                    /* Charles joue le 7 Bleu => normalement fail */
                    charles.playCard(charles.getiCard(0));
                    fail("Charles has successfully cheated.");

                } catch (CardException | UserException e) {
                    /* Charles possède le 7 Bleu */
                    if (!charles.hasCard(new CardNumber(7, ECardColor.Blue)))
                        fail("Charles have 7 Blue.");
                    System.out.println("Charles couldn't lay his 7 Blue.");

                    /* Charles possède 2 cartes */
                    if (charles.getNbCard() != 2)
                        fail("Charles don't have 2 cards.");
                    System.out.println("Charles have 2 cards.");
                }

            }

            @Test
            public void AliceEndHerRoundTest() {
                /* Alice end her round */
                Game.getCurrentPlayer().nextRound();

                /* Alice possess 3 cards */
                if (alice.getNbCard() != 3)
                    fail("Alice played a card without us knowing.");
                System.out.println("Alice have 3 cards in her Hand.");
            }

            @Test
            public void PlayAndPickInDeckTest() {

                /* Alice joue le 2 Vert */
                try {
                    alice.playCard(alice.getiCard(0));
                    System.out.println("Alice played a 2 Green.");
                } catch (UserException | CardException e) {
                    fail("Alice couldn't play a 2 Green.");
                }

                /* Alice récupère une carte */
                try {
                    alice.pickedCard();
                    fail("Alice successfully picked a card.");
                } catch (UserException e) {
                    /* Alice possède 2 cartes */
                    if (alice.getNbCard() != 2)
                        fail("Alice don't have 2 cards.");
                    System.out.println("Alice have 2 cards in hand.");

                    /* Le Deck possède le 6 Jaune */
                    if (!Game.getDeck().hasCard(new CardNumber(6, ECardColor.Yellow)))
                        fail("The 6 Yellow is not in the deck anymore.");
                    System.out.println("The 6 Yellow is in the deck.");
                }
            }
        }
        @Nested
        class PunitionTests {
            @Test
            public void AliceIllegalMoveTest() {

                /* Alice joue le 6 Jaune */
                try {
                    alice.playCard(alice.getiCard(1));
                    fail("Alice played her 6 yellow card");
                } catch (UserException | CardException e) {
                    try {
                        /* Punition de Alice selon setup (fait à part pour la flexibilité du programme) */
                        alice.pickedCardWithi(2);
                        System.out.println("Alice got punished.");
                    } catch (UserException ex) {
                        fail("Alice didn't picked 2 cards.");
                    }

                    /* Alice passe son tour */
                    alice.nextRound();

                    /* Bob est le joueur courrant */
                    if (!Game.getCurrentPlayer().equals(bob))
                        fail("It's not the round of bob.");
                    System.out.println("It's the round of bob.");

                    /* Alice possède 5 cartes */
                    if (alice.getNbCard() != 5)
                        fail("Alice don't have 5 cards.");
                    System.out.println("Alice have 5 cards.");

                    /* Alice possède le 6 Jaune et le 4 Rouge */
                    if (!alice.hasCard(new CardNumber(6, ECardColor.Yellow))
                            || !alice.hasCard(new CardNumber(4, ECardColor.Red)))
                        fail("  Alice don't have her 6 Yellow and 4 Red." + alice.getHand().toString());
                    System.out.println("Alice has 6 Yellow and 4 Red.");

                    /* La prochaine carte de la pioche est le 2 Vert */
                    if (!Game.getDeck().getCards().get(0).equals(new CardNumber(2, ECardColor.Green))) {
                        fail("Next card in deck is not 2 Green");
                    }
                    System.out.println("Nex card in deck is 2 Green.");
                }
            }

            @Test
            public void BobNotHisRoundTest() {
                /* Alice est le joueur courrant */
                if (!Game.getCurrentPlayer().equals(alice))
                    fail("It's not alice round.");
                System.out.println("It's alice round.");

                /* Bob pioche une carte */
                try {
                    bob.pickedCard();
                    fail("Bob picked a card.");
                } catch (UserException e) {
                    /* Bob se fait punir */
                    try {
                        bob.pickedCardWithi(2);
                        System.out.println("Bob got punished.");
                    } catch (UserException ex) {
                        fail("Bob is not punished");
                    }
                    /* Alice est le joueur courrants */
                    if (!Game.getCurrentPlayer().equals(alice))
                        fail("It's not alice round.");
                    System.out.println("It's alice round.");

                    /* Bob possède le 6 Jaune et 4 Rouge */
                    if (!bob.hasCard(new CardNumber(6, ECardColor.Yellow)) ||
                            !bob.hasCard(new CardNumber(4, ECardColor.Red)))
                        fail("Bob don't have 6 Yellow or 4 Red");
                    System.out.println("Bob have 6 Yellow and 4 Red");
                }
            }
        }

    }

    @Nested
    class SecondTests {
        /**
         * Test du UNO
         * Deck path : JeuTestCarteSimplePourTestUno.csv
         * Nb Card per player : 2
         */

        private static final int NB_CARD = 2;

        @BeforeEach
        public void init() {
            /* Nouvelle partie */
            new Game();

            /* Setup du deck */
            try {
                FileProcess.readFile("external/JeuTestCarteSimplePourTestUno.csv", Game.getDeck());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            /* Ajout des joueurs : Alice, Bob et Charles */
            Game.addPlayer("Alice");
            Game.addPlayer("Bob");
            Game.addPlayer("Charles");

            /* Setup main des joueurs */
            for (int i = 0; i < NB_CARD; i++) {
                for (Player player : Game.getPlayers()) {
                    Game.getDeck().giveCardToPlayerWithi(player, 1);
                }
            }

            /* Pose de la première carte du TAS */
            ICard cardDeck = Game.getDeck().getCards().get(0);
            Game.getPile().addCard(cardDeck);
            Game.getDeck().removeCard(cardDeck);

            /* Setup des raccourcis joueurs */
            alice = Game.getPlayers().get(0);
            bob = Game.getPlayers().get(1);
            charles = Game.getPlayers().get(2);
        }

        @Nested
        class UnoMoveTest {
            @Test
            public void AliceUnoTest() {
                /* Alice possède 2 cartes */
                if (alice.getNbCard() != 2)
                    fail("Alice don't have 2 cards.");
                System.out.println("Alice have 2 cards.");

                /* Alice joue le 2 Vert */
                try {
                    alice.playCard(alice.getiCard(0));
                    /* Alice dit Uno */
                    try {
                        alice.uno();
                    } catch (UserException e) {
                        fail("Alice can't Uno.");
                    }
                    /* Alice passe son tour */
                    alice.nextRound();
                } catch (UserException | CardException e) {
                    fail("Alice couldn't play 2 Green.");
                }

                /* Alice possède 1 carte */
                if (alice.getNbCard() != 1)
                    fail("Alice don't have 1 card");
                System.out.println("Alice have 1 card.");

                /* Récupération de la carte au dessus du TAS */
                ICard topcard = null;

                try {
                    topcard = Game.getPile().getTopCard();
                } catch (CardException e) {
                    throw new RuntimeException(e);
                }

                /* La carte au sommet du TAS est le 2 Vert */
                if (!topcard.equals(new CardNumber(2, ECardColor.Green)))
                    fail("Top pile is not 2 Green.");
                System.out.println("Top Pile is 2 Green.");

                /* Le joueur courrant est Bob */
                if (!Game.getCurrentPlayer().equals(bob))
                    fail("Current Player is not Bob.");
                System.out.println("Current Player is Bob.");
            }

            @Test
            public void AliceForgotUnoTest() {
                /* Alice joue le 2 Vert */
                try {
                    alice.playCard(alice.getiCard(0));

                    /* Alice passe son tour */
                    alice.nextRound();
                } catch (UserException | CardException e) {
                    fail("Alice didn't play 2 Green and didn't Uno.");
                }

                /* Bob dit contre uno */
                try {
                    bob.counterUno();
                    System.out.println("Alice got punished.");
                } catch (UserException e) {
                    fail("Bob couldn't counter Alice.");
                }

                /* Alice possède 4 cartes */
                if (alice.getNbCard() != 4)
                    fail("Alice don't have 4 cards.");
                System.out.println("Alice have 4 cards.");

                /* La carte au sommet du TAS est le 8 Vert */
                try {
                    if (!Game.getPile().getTopCard().equals(new CardNumber(8, ECardColor.Green)))
                        fail("Top card in pile is not 8 Green.");
                    System.out.println("Top card in pile is 8 Green.");

                } catch (CardException e) {
                    throw new RuntimeException(e);
                }

                /* Le joueur courrant est Bob */
                if (!Game.getCurrentPlayer().equals(bob))
                    fail("Bob is not current player.");
                System.out.println("Bob is current player.");

            }

            @Test
            public void BobTryUnoButNotRoundTest() {
                /* Le joueur courrant est Alice */
                if (!Game.getCurrentPlayer().equals(alice))
                    fail("Alice is not current player.");
                System.out.println("Alice is current player.");

                /* Bob dit Uno */
                try {
                    bob.uno();
                } catch (UserException e) {
                    System.out.println("Bob got punished.");

                    /* Bob possède 4 cartes */
                    if (bob.getNbCard() != 4)
                        fail("Bob don't have 4 cards.");
                    System.out.println("Bob have 4 cards.");

                    /* Alice est toujours la joueuse courrante */
                    if (!Game.getCurrentPlayer().equals(alice))
                        fail("Alice is not current player.");
                    System.out.println("Alice is current player.");

                    /* La carte au sommet du TAS est le 8 Vert */
                    try {
                        if (!Game.getPile().getTopCard().equals(new CardNumber(8, ECardColor.Green)))
                            fail("Top card in pile is not 8 Green");
                        System.out.println("Top card in pile is 8 Green.");
                    } catch (CardException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }

    }

    @Nested
    class ThirdTests {

        /**
         * Test du passe ton tour
         * Deck path : JeuTestCartePasser.csv
         * Nb Card per Player : 3
         */

        private static final int NB_CARD = 3;

        @BeforeEach
        public void init() {
            /* Setup nouvelle partie */
            Game = new Game();

            /* Setup du deck */
            try {
                FileProcess.readFile("external/JeuTestCartePasser.csv", Game.getDeck());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            /* Ajout des joueurs Alice, Bob et Charles */
            Game.addPlayer("Alice");
            Game.addPlayer("Bob");
            Game.addPlayer("Charles");

            /* Distribution des cartes */
            for (int i = 0; i < NB_CARD; i++) {
                for (Player player : Game.getPlayers()) {
                    Game.getDeck().giveCardToPlayerWithi(player, 1);
                }
            }

            /* Pose de la première carte */
            ICard cardDeck = Game.getDeck().getCards().get(0);
            Game.getPile().addCard(cardDeck);
            Game.getDeck().removeCard(cardDeck);

            /* Raccourcis pour les joueurs */
            alice = Game.getPlayers().get(0);
            bob = Game.getPlayers().get(1);
            charles = Game.getPlayers().get(2);
        }

        @Test
        public void LegalMoveWithSkipTest() {

            /* Le joueur courrant est alice */
            if (!Game.getCurrentPlayer().equals(alice))
                fail("Alice is not current player.");
            System.out.println("Alice is current player.");

            /* Alice pose la passe tour rouge */
            try {
                alice.playCard(alice.getiCard(0));
                System.out.println("Alice played Skip Red.");

                /* Alice passe son tour */
                alice.nextRound();
                System.out.println("Alice end her round.");
            } catch (UserException | CardException e) {
                fail("Alice couldn't play skip card.");
            }

            /* Bob est obligé de passer son tour*/
            bob.nextRound();

            /* Charles est le joueur courrant */
            if (!Game.getCurrentPlayer().equals(charles))
                fail("Charles is not current player.");
            System.out.println("Charles is current player.");

            /* La carte au sommet du TAS est le passe tour rouge */
            try {
                if (!Game.getPile().getTopCard().equals(new Skip(ECardColor.Red)))
                    fail("Top card in pile is not Skip Red.");
                System.out.println("Top card in pile is Skip Red.");
            } catch (CardException e) {
                fail("Topcard is not Skip Red");
            }

            /* Charles joue passe tour vert*/
            try {
                charles.playCard(charles.getiCard(1));
                System.out.println("Charles played Skip Green.");

                /* Charles passe son tour */
                charles.nextRound();
                System.out.println("Charles end his round.");
            } catch (UserException | CardException e) {
                fail("Charles couldn't play skip card.");
            }

            /* Alice est obligé de passer son tour */
            alice.nextRound();

            /* Bob est le joueur courrant */
            if (!Game.getCurrentPlayer().equals(bob))
                fail("Bob is not current player.");
            System.out.println("Bob is current player.");

            /* La carte au sommet du TAS est un passe Vert */
            try {
                if (!Game.getPile().getTopCard().equals(new Skip(ECardColor.Green)))
                    fail("Top card in pile is not Skip Green.");
                System.out.println("Top card in pile is Skip Green.");
            } catch (CardException e) {
                throw new RuntimeException(e);
            }

            /* Bob joue le 6 Vert */
            try {
                bob.playCard(bob.getiCard(1));
                System.out.println("Bob played 6 Green.");
                /* Bob passe son tour */
                bob.nextRound();
                System.out.println("Bob end his round.");
            } catch (UserException | CardException e) {
                throw new RuntimeException(e);
            }

            /* Le joueur courrant est charles */
            if (!Game.getCurrentPlayer().equals(charles))
                fail("Charles is not current player.");
            System.out.println("Charles is current player.");

            /* La carte au sommet du TAS est le 6 Vert */
            try {
                if (!Game.getPile().getTopCard().equals(new CardNumber(6, ECardColor.Green)))
                    fail("Top card in pile is not 6 Green.");
                System.out.println("Top card in pile is 6 Green.");
            } catch (CardException e) {
                throw new RuntimeException(e);
            }

        }

        @Test
        public void IllegalMoveWithSimpleCardOnSkip() {
            /* Alice est la joueuse courrante */
            if (!Game.getCurrentPlayer().equals(alice))
                fail("Alice is not current player.");
            System.out.println("Alice is current player.");

            /* Alice joue le passe rouge */
            try {
                alice.playCard(alice.getiCard(0));
                System.out.println("Alice play her Skip Red.");
                /* Alice finit son tour */
                alice.nextRound();
                System.out.println("Alice end her round.");
            } catch (UserException | CardException e) {
                fail("Alice couldn't play her Skip Red.");
            }

            /* Bob est obligé de passer son tour */
            bob.nextRound();
            System.out.println("Bob end his round.");

            /* Le joueur courrant est charles */
            if (!Game.getCurrentPlayer().equals(charles))
                fail("Charles is not current player.");
            System.out.println("Charles is current player.");

            /* Charles possède 3 cartes */
            if (charles.getNbCard() != 3)
                fail("Charles don't have 3 cards.");
            System.out.println("Charles have 3 cards.");

            /* Charles joue le 1 Bleu */
            try {
                charles.playCard(charles.getiCard(0));
                fail("Charles play his 1 Blue.");

                /* Charles fint son tour */
                charles.nextRound();
            } catch (UserException | CardException e) {
                System.out.println("Charles couldn't play his 1 Blue.");
                if (charles.getNbCard() != 3)
                    fail("Charles don't have 3 cards.");
                System.out.println("Charles have 3 cards.");
            }
        }

        @Test
        public void IllegalMoveWithSkipOnSimpleCardTest() {
            /* Alice est la joueuse courrante */
            if (!Game.getCurrentPlayer().equals(alice))
                fail("Alice is not current player.");
            System.out.println("Alice is current player.");

            /* Alice pose le 9 Bleu */
            try {
                alice.playCard(alice.getiCard(1));
                System.out.println("Alice played a 9 Blue.");

                /* Alice finit son tour */
                alice.nextRound();
                System.out.println("Alice end her round.");
            } catch (UserException | CardException e) {
                fail("Alice couldn't play a 9 Blue.");
            }

            /* Bob joue le 7 Bleu */
            try {
                bob.playCard(bob.getiCard(2));
                System.out.println("Bob played a 7 Blue.");

                /* Bob passe son tour */
                bob.nextRound();
                System.out.println("Bob end his round.");
            } catch (UserException | CardException e) {
                fail("Bob couldn't play a 7 Blue.");
            }

            /* Charles est le joueur courrant */
            if (!Game.getCurrentPlayer().equals(charles))
                fail("Charles is not current player.");
            System.out.println("Charles is current player.");

            /* Charles possède 3 cartes */
            if (charles.getNbCard() != 3)
                fail("Charles don't have 3 cards.");
            System.out.println("Charles have 3 cards.");

            /* Charles joue son passe tour vert */
            try {
                charles.playCard(charles.getiCard(1));
                fail("Charles played a Skip Green.");

                /* Charles met fin à son tour */
                charles.nextRound();
                System.out.println("Charles end his round.");
            } catch (UserException | CardException e) {
                System.out.println("Charles couldn't play a Skip Green.");

                /* Charles possède 3 cartes */
                if (charles.getNbCard() != 3)
                    fail("Charles don't have 3 cards");
                System.out.println("Charles have 3 cards");
            }
        }
    }

    @Nested
    class FourthTests {

        /**
         * Test des cartes +2
         * Deck path : JeuTestCartePlusTwo.csv
         * Nb Card per player : 3
         */

        private final static int NB_CARD = 3;

        @BeforeEach
        public void init() {

            /* Setup nouvelle partie*/
            Game = new Game();

            /* Setup Deck */
            try {
                FileProcess.readFile("external/JeuTestCartePlusTwo.csv", Game.getDeck());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            /* Ajout joueurs Alice, Bob et Charles*/
            Game.addPlayer("Alice");
            Game.addPlayer("Bob");
            Game.addPlayer("Charles");

            /* Setup main joueur*/
            for (int i = 0; i < NB_CARD; i++) {
                for (Player player : Game.getPlayers()) {
                    Game.getDeck().giveCardToPlayerWithi(player, 1);
                }
            }

            /* Setup première carte*/
            ICard cardDeck = Game.getDeck().getCards().get(0);
            Game.getPile().addCard(cardDeck);
            Game.getDeck().removeCard(cardDeck);

            /* Raccourcis joueurs */
            alice = Game.getPlayers().get(0);
            bob = Game.getPlayers().get(1);
            charles = Game.getPlayers().get(2);
        }

        @Test
        public void LegalMoveWithPlusTwoTest() {

            /* Alice est la joueuse courrante */
            if (!Game.getCurrentPlayer().equals(alice))
                fail("Alice is not current player.");
            System.out.println("Alice is current player.");

            /* Bob possède 3 cartes */
            if (bob.getNbCard() != 3)
                fail("Bob don't have 3 cards.");
            System.out.println("Bob have 3 cards.");

            /* Alice joue son +2 Vert */
            try {
                alice.playCard(alice.getiCard(0));
                System.out.println("Alice play +2 Green");
                /* Alice met fin à son tour */
                alice.nextRound();
                System.out.println("Alice end her round.");
            } catch (UserException | CardException e) {
                fail("Alice couldn't play her +2 Green.");
            }

            /* Bob est le joueur courrant */
            if (!Game.getCurrentPlayer().equals(bob))
                fail("Bob is not current player.");
            System.out.println("Bob is current player.");

            /* Bob est obligé de passer son tour et de prendre 2 cartes */
            System.out.println("Bob is forced to picked 2 cards and end his round.");

            /* Bob possède 5 cartes */
            if (bob.getNbCard() != 5)
                fail("Bob don't have 5 cards.");
            System.out.println("Bob have 5 cards.");

            /* Bob met fin à son tour obligatoirement */
            bob.nextRound();
            System.out.println("Bob end his round.");

            /* Bob possède 5 cartes */
            if (bob.getNbCard() != 5)
                fail("Bob don't have 5 cards");
            System.out.println("Bob have 5 cards");

            /* Le joueur courrant est charles */
            if (!Game.getCurrentPlayer().equals(charles))
                fail("Charles is not current player.");
            System.out.println("Charles is current player.");

            /* Charles joue son 1 Vert */
            try {
                charles.playCard(charles.getiCard(2));
                System.out.println("Charles play his 1 Green.");

                /* Charles met fin à son tour*/
                charles.nextRound();
                System.out.println("Charles end his round.");
            } catch (UserException | CardException e) {
                fail("Charles couldn't play his 1 Green.");
            }

            /* Charles possède 2 cartes */
            if (charles.getNbCard() != 2)
                fail("Charles don't have 2 cards.");
            System.out.println("Charles have 2 cards.");

        }

        @Test
        public void LegalMoveWithCumulOfPlusTwoTest() {
            /* Alice est la joueuse courrante */
            if(!Game.getCurrentPlayer().equals(alice))
                fail("Alice is not current player.");
            System.out.println("Alice is current player.");

            /* Alice pioche une carte */
            try {
                alice.pickedCard();
                System.out.println("Alice picked a card from deck.");

                /* Alice met fin à son tour */
                alice.nextRound();
                System.out.println("Alice end her round.");
            } catch (UserException e) {
                fail("Alice didn't pick a card from deck.");
            }

            /* Bob est le joueur courrant */
            if (!Game.getCurrentPlayer().equals(bob))
                fail("Bob is not current player.");
            System.out.println("Bob is current player.");

            /* Bob pioche une carte */
            try {
                bob.pickedCard();
                System.out.println("Bob picked a card.");

                /* Bob met fin à son tour */
                bob.nextRound();
                System.out.println("Bob end his round.");
            } catch (UserException e) {
                throw new RuntimeException(e);
            }

            /* Le joueur courrant est Charles */
            if (!Game.getCurrentPlayer().equals(charles))
                fail("Charles is not current player.");
            System.out.println("Charles is current player.");

            /* Charles joue le +2 Vert */
            try {
                charles.playCard(charles.getiCard(1));
                System.out.println("Charles play his +2 Green.");

                /* Charles met fin à son tour */
                charles.nextRound();
                System.out.println("Charles end his round.");
            } catch (UserException | CardException e) {
                fail("Charles couldn't play his +2 Green.");
            }

            /* Alice est la joueuse courrante */
            if (!Game.getCurrentPlayer().equals(alice))
                fail("Alice is not current player.");
            System.out.println("Alice is current player.");

            /* Bob possède 4 cartes */
            if (bob.getNbCard() != 4)
                fail("Bob don't have 4 cards." + bob.getHand());
            System.out.println("Bob have 4 cards.");

            /* Alice joue son +2 Vert */
            try {
                alice.playCard(alice.getiCard(0));
                System.out.println("Alice played her +2 Green.");

                /* Alice met fin à son tour */
                alice.nextRound();
                System.out.println("Alice end her round.");
            } catch (UserException | CardException e) {
                fail("Alice couldn't play her +2 Green.");
            }

            /* Bob est le joueur courrant */
            if (!Game.getCurrentPlayer().equals(bob))
                fail("Bob is not current player.");
            System.out.println("Bob is current player.");

            /* Bob possède 8 cartes */
            if (bob.getNbCard() != 8)
                fail("Bob don't have 8 cards.");
            System.out.println("Bob have 8 cards.");

            /* Bob met fin à son tour */
            bob.nextRound();
            System.out.println("Bob end his round.");

            /* Charles est le joueur courrant */
            if (!Game.getCurrentPlayer().equals(charles))
                fail("Charles is not current player.");
            System.out.println("Charles is current player.");

        }
    }

    @Nested
    class FifthTests {

        /**
         * Test du change couleur
         * Deck path : JeuTestCarteCouleur.csv
         * Nb Card per player : 3
         */

        private final static int NB_CARD = 3;

        @BeforeEach
        public void init() {
            /* Nouvelle partie */
            Game = new Game();

            /* Setup Deck */
            try {
                FileProcess.readFile("external/JeuTestCarteCouleur.csv", Game.getDeck());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            /* Setup joueur Alice, Bob et Charles*/
            Game.addPlayer("Alice");
            Game.addPlayer("Bob");
            Game.addPlayer("Charles");

            /* Setup main joueur */
            for (int i = 0; i < NB_CARD; i++) {
                for (Player player : Game.getPlayers()) {
                    Game.getDeck().giveCardToPlayerWithi(player, 1);
                }
            }

            /* Première carte */
            ICard cardDeck = Game.getDeck().getCards().get(0);
            Game.getPile().addCard(cardDeck);
            Game.getDeck().removeCard(cardDeck);

            /* Raccourcis joueurs */
            alice = Game.getPlayers().get(0);
            bob = Game.getPlayers().get(1);
            charles = Game.getPlayers().get(2);
        }

        @Test
        public void LegalMoveWithChangeColor() {

            /* Alice est la joueuse courrante */
            if (!Game.getCurrentPlayer().equals(alice))
                fail("Alice is not current player.");
            System.out.println("Alice is current player.");

            /* Alice joue son change couleur en Bleu*/
            try {
                ChangeColor card = (ChangeColor) alice.getFirstiCardByType(ECardType.ChangeColor);
                card.setColor(ECardColor.Blue);
                alice.playCard(card);
                System.out.println("Alice play a Change Color and set Blue");

                /* Alice met fin à son tour*/
                alice.nextRound();
                System.out.println("Alice end her round");
            } catch (UserException | CardException e) {
                fail("Alice couldn't play a Change Color.");
            }

            /* Bob est le joueur courrant */
            if (!Game.getCurrentPlayer().equals(bob))
                fail("Bob is not current player.");
            System.out.println("Bob is current player.");

            /* Bob joue son 7 Bleu */
            try {
                bob.playCard(bob.getLastiCardByType(ECardType.Number));
                System.out.println("Bob play a 7 Blue.");

                /* Bob met fin à son tour */
                bob.nextRound();
                System.out.println("Bob end his round.");
            } catch (UserException | CardException e) {
                fail("Bob couldn't play his card.");
            }

        }

        @Test
        public void IllegalMoveWithChangeColor() {
            /* Alice est la joueuse courrante */
            if (!Game.getCurrentPlayer().equals(alice))
                fail("Alice is not current player.");
            System.out.println("Alice is current player.");

            /* Alice joue son change couleur en Bleu */
            try {
                ChangeColor card = (ChangeColor) alice.getFirstiCardByType(ECardType.ChangeColor);
                card.setColor(ECardColor.Red);
                alice.playCard(alice.getFirstiCardByType(ECardType.ChangeColor));
                System.out.println("Alice play a Change Color and set Blue");

                /* Alice met fin à son tour */
                alice.nextRound();
                System.out.println("Alice end her round");
            } catch (UserException | CardException e) {
                fail("Alice couldn't play a Change Color.");
            }

            /* Bob est le joueur courrant */
            if (!Game.getCurrentPlayer().equals(bob))
                fail("Bob is not current player.");
            System.out.println("Bob is current player.");

            /* Bob joue son 7 Bleu */
            try {
                bob.playCard(bob.getLastiCardByType(ECardType.Number));
                fail("Bob play a 7 Blue.");
            } catch (UserException | CardException e) {
                System.out.println("Bob couldn't play his 7 Blue.");

                /* Bob passe son tour */
                Game.getCurrentPlayer().nextRound();
                System.out.println("Bob end his round.");
            }

        }
    }
}
