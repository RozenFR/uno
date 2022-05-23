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
        @BeforeEach
        public void init() {

            new Game();

            try {
                FileProcess.readFile("external/JeuTestCarteSimple.csv", Game.getDeck());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Game.addPlayer("Alice");
            Game.addPlayer("Bob");
            Game.addPlayer("Charles");

            for (int i = 0; i < 3; i++) {
                for (Player player : Game.getPlayers()) {
                    Game.getDeck().giveCardToPlayerWithi(player, 1);
                }
            }
            ICard cardDeck = Game.getDeck().getCards().get(0);
            Game.getPile().addCard(cardDeck);
            Game.getDeck().removeCard(cardDeck);

            alice = Game.getPlayers().get(0);
            bob = Game.getPlayers().get(1);
            charles = Game.getPlayers().get(2);
        }

        @Nested
        class LegalMoveWithSimpleCardTests {
            @Test
            public void AlicePlayCardWithRightColorTest() {

                // Verify Alice is current player
                if (!Game.getCurrentPlayer().equals(alice))
                    fail("Alice is not the current player.");
                System.out.println("Alice is current player.");

                // Verify if alice has 3 cards
                if (alice.getNbCard() != 3)
                    fail("Alice doesn't have 3 cards.");
                System.out.println("Alice have 3 cards in her hand.");

                try {
                    alice.playCard(alice.getHand().getCards().get(0));
                } catch (CardException | UserException e) {
                    fail("Alice didn't played 2 green.");
                }
                System.out.println("Alice played 2 green.");

                if (!alice.hasCard(new CardNumber(6, ECardColor.Yellow))
                        || !alice.hasCard(new CardNumber(1, ECardColor.Red)))
                    fail("Alice doesn't have 6 yellow or 1 red.");
                System.out.println("Alice have 6 yellow and 1 red.");

                if (Game.getPile().getNbCard() != 2)
                    fail("The pile is not updated");
                System.out.println("The pile has 2 cards.");

                Game.getCurrentPlayer().nextRound();
                if (Game.getRoundOfPlayer() != 1)
                    fail("The round has not passed.");
                System.out.println("Alice finished her round.");

                if (!Game.getCurrentPlayer().equals(bob))
                    fail("Bob is not current player.");
                System.out.println("Bob is current player.");
            }

            @Test
            public void BobPlayCardWithDifferentColorButSameValueTest() {

                AlicePlayCardWithRightColorTest();
                if (!Game.getCurrentPlayer().equals(bob))
                    fail("The current player is not Bob.");
                System.out.println("Bob is the current player.");

                if (Game.getCurrentPlayer().getNbCard() != 3)
                    fail("Bob doesn't have 3 cards.");
                System.out.println("Bob have 3 cards in his hand.");

                Player bob = Game.getPlayers().get(1);
                try {
                    bob.playCard(bob.getHand().getCards().get(0));
                } catch (CardException | UserException e) {
                    fail("Bob didn't play 2 Blue.");
                }
                System.out.println("Bob played 2 blue.");

                if (!bob.hasCard(new CardNumber(4, ECardColor.Yellow))
                        || !bob.hasCard(new CardNumber(9, ECardColor.Red)))
                    fail("bob need to have 4 yellow and 9 Red.");
                System.out.println("  Bob have 4 yellow and 9 red.");
            }
        }

        @Nested
        class IllegalMoveWithSimpleCardTests {
            /**
             *
             */
            @Test
            public void IllegalCardTest() {
                try {
                    alice.playCard(alice.getHand().getCards().get(1));
                    fail("Alice play her card.");
                } catch (CardException | UserException e) {

                    System.out.println("Alice want to play 6 yellow.");

                    if (Game.getPile().hasCard(new CardNumber(6, ECardColor.Yellow)))
                        fail("  Alice played her 6 yellow.");
                    System.out.println("  Alice couldn't play her 6 yellow.");

                    if (!alice.hasCard(new CardNumber(6, ECardColor.Yellow)))
                        fail("Alice don't have 6 yellow in hand.");
                    System.out.println("Alice have 6 Yellow in Hand.");

                    if (alice.getNbCard() != 3)
                        fail("Alice don't have 3 cards.");
                    System.out.println("Alice have 3 cards.");

                }
            }

            @Test
            public void PlayTwoLegalCardInRow() {
                try {

                    alice.playCard(alice.getiCard(0));
                    Game.getCurrentPlayer().nextRound();
                    if (alice.getNbCard() != 2 || alice.hasCard(new CardNumber(2, ECardColor.Green)))
                        fail("  Alice don't have 2 card and have the 2 Green in Hand.");
                    System.out.println("  Alice lay the 2 Green and pass.");

                    bob.playCard(bob.getiCard(0));
                    bob.nextRound();
                    if (bob.getNbCard() != 2 || bob.hasCard(new CardNumber(2, ECardColor.Blue)))
                        fail("  Bob don't have 2 card in Hand and have the 2 Blue in Hand.");
                    System.out.println(" Bob lay the 2 Blue and pass.");

                } catch (CardException | UserException e) {
                    fail("Fail Play Two Legal Card in a row.");
                }

                try {
                    charles.playCard(charles.getiCard(0));
                    if (charles.getNbCard() != 2)
                        fail("  Charles don't have 2 cards in Hand.");
                    System.out.println("  Charles have 2 cards in Hand.");

                    charles.playCard(charles.getiCard(0));
                    fail("  Charles has successfully cheated.");
                } catch (CardException | UserException e) {
                    if (!charles.hasCard(new CardNumber(7, ECardColor.Blue)))
                        fail("  Charles got us.");
                    System.out.println("  Charles couldn't lay his 2nd card.");
                }

            }

            @Test
            public void AliceEndHerRoundTest() {
                Game.getCurrentPlayer().nextRound();
                if (alice.getNbCard() != 3)
                    fail("Alice played a card without us knowing.");
                System.out.println("Alice have 3 cards in her Hand.");
            }

            @Test
            public void PlayAndPickInDeckTest() {

                try {
                    alice.playCard(alice.getiCard(0));
                } catch (UserException | CardException e) {
                    fail("Alice couldn't play a 2 Green.");
                }
                System.out.println("Alice played a 2 Green.");

                try {
                    alice.pickedCard();
                    fail("Alice successfully picked a card.");
                } catch (UserException e) {
                    if (alice.getNbCard() != 2)
                        fail("Alice don't have 2 cards.");
                    System.out.println("Alice have 2 cards in hand.");
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

                try {
                    alice.playCard(alice.getiCard(1));
                    fail("Alice played her 6 yellow card");
                } catch (UserException | CardException e) {
                    try {
                        alice.pickedCardWithi(2);
                    } catch (UserException ex) {
                        fail("Alice didn't picked 2 cards.");
                    }
                    System.out.println("Alice got punished.");

                    alice.nextRound();

                    if (!Game.getCurrentPlayer().equals(bob))
                        fail("It's not the round of bob.");
                    System.out.println("It's the round of bob.");

                    if (alice.getNbCard() != 5)
                        fail("Alice don't have 5 cards.");
                    System.out.println("Alice have 5 cards.");

                    if (!alice.hasCard(new CardNumber(6, ECardColor.Yellow)) && !alice.hasCard(new CardNumber(4, ECardColor.Red)))
                        fail("  Alice don't have her 6 Yellow and 4 Red." + alice.getHand().toString());
                    System.out.println("Alice has 6 Yellow and 4 Red.");

                    if (!Game.getDeck().getCards().get(0).equals(new CardNumber(2, ECardColor.Green))) {
                        fail("Next card in deck is not 2 Green");
                    }
                    System.out.println("Nex card in deck is 2 Green.");
                }
            }

            @Test
            public void BobNotHisRoundTest() {
                if (!Game.getPlayers().get(Game.getRoundOfPlayer()).equals(alice))
                    fail("It's not alice round.");
                System.out.println("It's alice round.");

                try {
                    bob.pickedCardWithi(1);
                    fail("Bob picked a card.");
                } catch (UserException e) {
                    Game.getDeck().giveCardToPlayerWithi(bob, 2);
                    System.out.println("Bob got punished.");
                    if (!Game.getPlayers().get(Game.getRoundOfPlayer()).equals(alice))
                        fail("It's not alice round.");
                    System.out.println("It's alice round.");
                }
            }
        }

    }

    @Nested
    class SecondTests {

        @BeforeEach
        public void init() {

            new Game();

            try {
                FileProcess.readFile("external/JeuTestCarteSimplePourTestUno.csv", Game.getDeck());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Game.addPlayer("Alice");
            Game.addPlayer("Bob");
            Game.addPlayer("Charles");

            for (int i = 0; i < 2; i++) {
                for (Player player : Game.getPlayers()) {
                    Game.getDeck().giveCardToPlayerWithi(player, 1);
                }
            }
            ICard cardDeck = Game.getDeck().getCards().get(0);
            Game.getPile().addCard(cardDeck);
            Game.getDeck().removeCard(cardDeck);

            alice = Game.getPlayers().get(0);
            bob = Game.getPlayers().get(1);
            charles = Game.getPlayers().get(2);
        }

        @Nested
        class UnoMoveTest {
            @Test
            public void AliceUnoTest() {
                if (alice.getNbCard() != 2)
                    fail("Alice don't have 2 cards.");
                System.out.println("Alice have 2 cards.");

                try {
                    alice.playCard(alice.getiCard(0));
                    try {
                        alice.uno(Game);
                    } catch (UserException e) {
                        fail("Alice can't Uno.");
                    }
                    alice.nextRound();
                } catch (UserException | CardException e) {
                    fail("Alice couldn't play 2 Green.");
                }

                if (alice.getNbCard() != 1)
                    fail("Alice don't have 1 card");
                System.out.println("Alice have 1 card.");

                ICard topcard = null;

                try {
                    topcard = Game.getPile().getTopCard();
                } catch (CardException e) {
                    throw new RuntimeException(e);
                }

                if (!topcard.equals(new CardNumber(2, ECardColor.Green)))
                    fail("Top pile is not 2 Green.");
                System.out.println("Top Pile is 2 Green.");

                if (!Game.getCurrentPlayer().equals(bob))
                    fail("Current Player is not Bob.");
                System.out.println("Current Player is Bob.");
            }

            @Test
            public void AliceForgotUnoTest() {
                try {
                    alice.playCard(alice.getiCard(0));
                    alice.nextRound();
                } catch (UserException | CardException e) {
                    fail("Alice didn't play 2 Green and didn't Uno.");
                }

                try {
                    bob.counterUno(Game);
                    System.out.println("Alice got punished.");
                } catch (UserException e) {
                    fail("Bob couldn't counter Alice.");
                }

                if (alice.getNbCard() != 4)
                    fail("Alice don't have 4 cards.");
                System.out.println("Alice have 4 cards.");

                try {
                    if (!Game.getPile().getTopCard().equals(new CardNumber(8, ECardColor.Green)))
                        fail("Top card in pile is not 8 Green.");
                    System.out.println("Top card in pile is 8 Green.");

                } catch (CardException e) {
                    throw new RuntimeException(e);
                }

                if (!Game.getCurrentPlayer().equals(bob))
                    fail("Bob is not current player.");
                System.out.println("Bob is current player.");

            }

            @Test
            public void BobTryUnoButNotRoundTest() {
                if (!Game.getCurrentPlayer().equals(alice))
                    fail("Alice is not current player.");
                System.out.println("Alice is current player.");
                try {
                    bob.uno(Game);
                } catch (UserException e) {
                    System.out.println("Bob got punished.");

                    if (bob.getNbCard() != 4)
                        fail("Bob don't have 4 cards.");
                    System.out.println("Bob have 4 cards.");

                    if (!Game.getCurrentPlayer().equals(alice))
                        fail("Alice is not current player.");
                    System.out.println("Alice is current player.");

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
        @BeforeEach
        public void init() {

            Game = new Game();

            try {
                FileProcess.readFile("external/JeuTestCartePasser.csv", Game.getDeck());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Game.addPlayer("Alice");
            Game.addPlayer("Bob");
            Game.addPlayer("Charles");
            for (int i = 0; i < 3; i++) {
                for (Player player : Game.getPlayers()) {
                    Game.getDeck().giveCardToPlayerWithi(player, 1);
                }
            }
            ICard cardDeck = Game.getDeck().getCards().get(0);
            Game.getPile().addCard(cardDeck);
            Game.getDeck().removeCard(cardDeck);

            alice = Game.getPlayers().get(0);
            bob = Game.getPlayers().get(1);
            charles = Game.getPlayers().get(2);
        }

        @Test
        public void LegalMoveWithSkipTest() {

            if (!Game.getCurrentPlayer().equals(alice))
                fail("Alice is not current player.");
            System.out.println("Alice is current player.");

            try {
                alice.playCard(alice.getiCard(0));
                System.out.println("Alice played Skip Red.");
                alice.nextRound();
                System.out.println("Alice end her round.");
            } catch (UserException | CardException e) {
                fail("Alice couldn't play skip card.");
            }

            if (!Game.getCurrentPlayer().equals(charles))
                fail("Charles is not current player.");
            System.out.println("Charles is current player.");

            try {
                charles.playCard(charles.getiCard(1));
                System.out.println("Charles played Skip Green.");
                charles.nextRound();
                System.out.println("Charles end his round.");
            } catch (UserException | CardException e) {
                fail("Charles couldn't play skip card.");
            }

            if (!Game.getCurrentPlayer().equals(bob))
                fail("Bob is not current player.");
            System.out.println("Bob is current player.");

            try {
                if (!Game.getPile().getTopCard().equals(new Skip(ECardColor.Green)))
                    fail("Top card in pile is not Skip Green.");
                System.out.println("Top card in pile is Skip Green.");
            } catch (CardException e) {
                throw new RuntimeException(e);
            }

            try {
                bob.playCard(bob.getiCard(1));
                System.out.println("Bob played 6 Green.");
                bob.nextRound();
                System.out.println("Bob end his round.");
            } catch (UserException | CardException e) {
                throw new RuntimeException(e);
            }

            if (!Game.getCurrentPlayer().equals(charles))
                fail("Charles is not current player.");
            System.out.println("Charles is current player.");

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
            if (!Game.getCurrentPlayer().equals(alice))
                fail("Alice is not current player.");
            System.out.println("Alice is current player.");

            try {
                alice.playCard(alice.getiCard(0));
                System.out.println("Alice play her Skip Red.");
                alice.nextRound();
                System.out.println("Alice end her round.");
            } catch (UserException | CardException e) {
                fail("Alice couldn't play her Skip Red.");
            }

            if (!Game.getCurrentPlayer().equals(charles))
                fail("Charles is not current player.");
            System.out.println("Charles is current player.");

            if (charles.getNbCard() != 3)
                fail("Charles don't have 3 cards.");
            System.out.println("Charles have 3 cards.");

            try {
                charles.playCard(charles.getiCard(0));
                fail("Charles play his 1 Blue.");
            } catch (UserException | CardException e) {
                System.out.println("Charles couldn't play his 1 Blue.");
                if (charles.getNbCard() != 3)
                    fail("Charles don't have 3 cards.");
                System.out.println("Charles have 3 cards.");
            }
        }

        @Test
        public void IllegalMoveWithSkipOnSimpleCardTest() {
            if (!Game.getCurrentPlayer().equals(alice))
                fail("Alice is not current player.");
            System.out.println("Alice is current player.");

            try {
                alice.playCard(alice.getiCard(1));
                System.out.println("Alice played a 9 Blue.");
                alice.nextRound();
                System.out.println("Alice end her round.");
            } catch (UserException | CardException e) {
                fail("Alice couldn't play a 9 Blue.");
            }

            try {
                bob.playCard(bob.getiCard(2));
                System.out.println("Bob played a 7 Blue.");
                bob.nextRound();
            } catch (UserException | CardException e) {
                fail("Bob couldn't play a 7 Blue.");
            }

            if (!Game.getCurrentPlayer().equals(charles))
                fail("Charles is not current player.");
            System.out.println("Charles is current player.");

            if (charles.getNbCard() != 3)
                fail("Charles don't have 3 cards.");
            System.out.println("Charles have 3 cards.");

            try {
                charles.playCard(charles.getiCard(1));
                fail("Charles played a Skip Green.");
                charles.nextRound();
                System.out.println("Charles end his round.");
            } catch (UserException | CardException e) {
                System.out.println("Charles couldn't play a Skip Green.");
                if (charles.getNbCard() != 3)
                    fail("Charles don't have 3 cards");
                System.out.println("Charles have 3 cards");
            }
        }
    }

    @Nested
    class FourthTests {
        @BeforeEach
        public void init() {

            Game = new Game();

            try {
                FileProcess.readFile("external/JeuTestCartePlusTwo.csv", Game.getDeck());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Game.addPlayer("Alice");
            Game.addPlayer("Bob");
            Game.addPlayer("Charles");
            for (int i = 0; i < 3; i++) {
                for (Player player : Game.getPlayers()) {
                    Game.getDeck().giveCardToPlayerWithi(player, 1);
                }
            }
            ICard cardDeck = Game.getDeck().getCards().get(0);
            Game.getPile().addCard(cardDeck);
            Game.getDeck().removeCard(cardDeck);

            alice = Game.getPlayers().get(0);
            bob = Game.getPlayers().get(1);
            charles = Game.getPlayers().get(2);
        }

        @Test
        public void LegalMoveWithPlusTwoTest() {
            if (!Game.getCurrentPlayer().equals(alice))
                fail("Alice is not current player.");
            System.out.println("Alice is current player.");

            try {
                alice.playCard(alice.getiCard(0));
                System.out.println("Alice play +2 Green");
                alice.nextRound();
                System.out.println("Alice end her round.");
            } catch (UserException | CardException e) {
                fail("Alice couldn't play her +2 Green.");
            }

            if (!Game.getCurrentPlayer().equals(bob))
                fail("Bob is not current player.");
            System.out.println("Bob is current player.");

            if (bob.getNbCard() != 3)
                fail("Bob don't have 3 cards.");
            System.out.println("Bob have 3 cards.");

            try {
                bob.playCard(null);
                System.out.println("Bob is forced to picked 2 cards and end his round.");
            } catch (UserException | CardException e) {
                fail("Bob didn't picked 2 cards.");
            }

            if (bob.getNbCard() != 5)
                fail("Bob don't have 5 cards." + bob.getHand().toString());
            System.out.println("Bob have 5 cards.");

            if (!Game.getCurrentPlayer().equals(charles))
                fail("Charles is not current player.");
            System.out.println("Charles is current player.");

            try {
                charles.playCard(charles.getiCard(2));
                System.out.println("Charles play his 1 Green.");
                charles.nextRound();
                System.out.println("Charles end his round.");
            } catch (UserException | CardException e) {
                fail("Charles couldn't play his 1 Green.");
            }

            if (charles.getNbCard() != 2)
                fail("Charles don't have 2 cards.");
            System.out.println("Charles have 2 cards.");

        }

        @Test
        public void LegalMoveWithCumulOfPlusTwoTest() {
            if(!Game.getCurrentPlayer().equals(alice))
                fail("Alice is not current player.");
            System.out.println("Alice is current player.");

            try {
                alice.pickedCardWithi(1);
                System.out.println("Alice picked a card from deck.");
                alice.nextRound();
                System.out.println("Alice end her round.");
            } catch (UserException e) {
                fail("Alice didn't pick a card from deck.");
            }

            if (!Game.getCurrentPlayer().equals(bob))
                fail("Bob is not current player.");
            System.out.println("Bob is current player.");

            try {
                bob.pickedCardWithi(1);
                System.out.println("Bob picked a card.");
                bob.nextRound();
                System.out.println("Bob end his round.");
            } catch (UserException e) {
                throw new RuntimeException(e);
            }

            if (!Game.getCurrentPlayer().equals(charles))
                fail("Charles is not current player.");
            System.out.println("Charles is current player.");

            try {
                charles.playCard(charles.getiCard(1));
                System.out.println("Charles play his +2 Green.");
                charles.nextRound();
                System.out.println("Charles end his round.");
            } catch (UserException | CardException e) {
                fail("Charles couldn't play his +2 Green.");
            }

            if (!Game.getCurrentPlayer().equals(alice))
                fail("Alice is not current player.");
            System.out.println("Alice is current player.");

            try {
                alice.playCard(alice.getiCard(0));
                System.out.println("Alice played her +2 Green.");
                alice.nextRound();
            } catch (UserException | CardException e) {
                fail("Alice couldn't play her +2 Green.");
            }

            if (!Game.getCurrentPlayer().equals(bob))
                fail("Bob is not current player.");
            System.out.println("Bob is current player.");

            if (bob.getNbCard() != 4)
                fail("Bob don't have 4 cards.");
            System.out.println("Bob have 4 cards.");

            try {
                bob.playCard(null);
                System.out.println("Bob is forced to picked +4 cards and end his round.");
            } catch (UserException | CardException e) {
                throw new RuntimeException(e);
            }

            if (bob.getNbCard() != 8)
                fail("Bob don't have 8 cards.");
            System.out.println("Bob have 8 cards.");

            if (!Game.getCurrentPlayer().equals(charles))
                fail("Charles is not current player.");
            System.out.println("Charles is current player.");

        }
    }

    @Nested
    class FifthTests {
        @BeforeEach
        public void init() {
            Game = new Game();

            try {
                FileProcess.readFile("external/JeuTestCarteCouleur.csv", Game.getDeck());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Game.addPlayer("Alice");
            Game.addPlayer("Bob");
            Game.addPlayer("Charles");
            for (int i = 0; i < 3; i++) {
                for (Player player : Game.getPlayers()) {
                    Game.getDeck().giveCardToPlayerWithi(player, 1);
                }
            }
            ICard cardDeck = Game.getDeck().getCards().get(0);
            Game.getPile().addCard(cardDeck);
            Game.getDeck().removeCard(cardDeck);

            alice = Game.getPlayers().get(0);
            bob = Game.getPlayers().get(1);
            charles = Game.getPlayers().get(2);
        }

        @Test
        public void LegalMoveWithChangeColor() {
            if (!Game.getCurrentPlayer().equals(alice))
                fail("Alice is not current player.");
            System.out.println("Alice is current player.");

            try {
                ChangeColor card = (ChangeColor) alice.getFirstiCardByType(ECardType.ChangeColor);
                card.setColor(ECardColor.Blue);
                alice.playCard(card);
                System.out.println("Alice play a Change Color and set Blue");
                alice.nextRound();
                System.out.println("Alice end her round");
            } catch (UserException | CardException e) {
                fail("Alice couldn't play a Change Color.");
            }

            if (!Game.getCurrentPlayer().equals(bob))
                fail("Bob is not current player.");
            System.out.println("Bob is current player.");

            try {
                bob.playCard(bob.getLastiCardByType(ECardType.Number));
                System.out.println("Bob play a 7 Blue.");
                bob.nextRound();
                System.out.println("Bob end his round.");
            } catch (UserException | CardException e) {
                fail("Bob couldn't play his card.");
            }

        }

        @Test
        public void IllegalMoveWithChangeColor() {
            if (!Game.getCurrentPlayer().equals(alice))
                fail("Alice is not current player.");
            System.out.println("Alice is current player.");

            try {
                ChangeColor card = (ChangeColor) alice.getFirstiCardByType(ECardType.ChangeColor);
                card.setColor(ECardColor.Red);
                alice.playCard(alice.getFirstiCardByType(ECardType.ChangeColor));
                System.out.println("Alice play a Change Color and set Blue");
                alice.nextRound();
                System.out.println("Alice end her round");
            } catch (UserException | CardException e) {
                fail("Alice couldn't play a Change Color.");
            }

            if (!Game.getCurrentPlayer().equals(bob))
                fail("Bob is not current player.");
            System.out.println("Bob is current player.");

            try {
                bob.playCard(bob.getLastiCardByType(ECardType.Number));
                fail("Bob play a 7 Blue.");
            } catch (UserException | CardException e) {
                System.out.println("Bob couldn't play his 7 Blue.");
                Game.getCurrentPlayer().nextRound();
                System.out.println("Bob end his round.");
            }

        }
    }
}
