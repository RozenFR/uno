import cardset.CardException;
import cardset.card.CardNumber;
import cardset.card.ECardColor;
import cardset.card.ICard;
import cardset.card.Skip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import parse.FileProcess;
import user.Game;
import user.Player;
import user.UserException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

public class UnoTest {

    Game game;
    Player alice;
    Player bob;
    Player charles;

    @Nested
    class FirstTests {
        @BeforeEach
        public void init() {
            game = new Game();

            try {
                FileProcess.readFile("external/JeuTestCarteSimple.csv", game.getDeck());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            game.addPlayer("Alice");
            game.addPlayer("Bob");
            game.addPlayer("Charles");
            for (int i = 0; i < 3; i++) {
                for (Player player : game.getPlayers()) {
                    game.getDeck().giveCardToPlayerWithi(player, 1);
                }
            }
            ICard cardDeck = game.getDeck().getCards().get(0);
            game.getPile().addCard(cardDeck);
            game.getDeck().removeCard(cardDeck);

            alice = game.getPlayers().get(0);
            bob = game.getPlayers().get(1);
            charles = game.getPlayers().get(2);
        }

        @Nested
        class LegalMoveWithSimpleCardTests {
            @Test
            public void AlicePlayCardWithRightColorTest() {

                if (!game.getPlayers().get(game.getRoundOfPlayer()).getName().equals("Alice"))
                    fail("  Alice is not the current player.");
                System.out.println("  Alice is current player.");

                if (game.getPlayers().get(game.getRoundOfPlayer()).getNbCard() != 3)
                    fail("  Alice doesn't have 3 cards.");
                System.out.println("  Alice have 3 cards in her hand.");

                try {
                    alice.playCard(game, alice.getHand().getCards().get(0));
                } catch (UserException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("  Alice played 2 green.");
                if (!alice.hasCard(new CardNumber(6, ECardColor.Yellow))
                        || !alice.hasCard(new CardNumber(1, ECardColor.Red)))
                    fail("  Alice doesn't have 6 yellow or 1 red.");
                System.out.println("  Alice have 6 yellow and 1 red.");

                if (game.getPile().getNbCard() != 2)
                    fail("  The pile is not updated");
                System.out.println("  The pile has 2 cards.");

                game.nextRound();
                if (game.getRoundOfPlayer() != 1)
                    fail("  The round has not passed.");
                System.out.println("  Alice finished her round.");
            }

            @Test
            public void BobPlayCardWithDifferentColorButSameValueTest() {
                AlicePlayCardWithRightColorTest();
                if (!game.getPlayers().get(game.getRoundOfPlayer()).getName().equals("Bob"))
                    fail("The current player is not Charles.");
                System.out.println("Charles is the current player.");

                if (game.getPlayers().get(game.getRoundOfPlayer()).getNbCard() != 3)
                    fail("  Bob doesn't have 3 cards.");
                System.out.println("  Bob have 3 cards in his hand.");

                Player bob = game.getPlayers().get(1);
                try {
                    bob.playCard(game, bob.getHand().getCards().get(0));
                } catch (UserException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("  Bob played 2 blue.");
                if (!bob.hasCard(new CardNumber(4, ECardColor.Yellow)) || !bob.hasCard(new CardNumber(9, ECardColor.Red)))
                    fail("  bob doesn't have 4 yellow or 9 red. " + bob.getHand().toString());
                System.out.println("  Bob have 4 yellow and 9 red.");
            }
        }

        @Nested
        class IllegalMoveWithSimpleCardTests {
            @Test
            public void IllegalCardTest() {
                Player alice = game.getPlayers().get(0);
                try {
                    alice.playCard(game, alice.getHand().getCards().get(1));
                } catch (UserException e) {
                    System.out.println("  Alice want to play 6 yellow.");
                    if (game.getPile().hasCard(new CardNumber(6, ECardColor.Yellow)))
                        fail("  Alice played her 6 yellow.");
                    System.out.println("  Alice couldn't play her 6 yellow.");
                }
            }

            @Test
            public void PlayTwoLegalCardInRow() {
                try {
                    alice.playCard(game, alice.getiCard(0));
                    game.nextRound();
                    if (alice.getNbCard() != 2 || alice.hasCard(new CardNumber(2, ECardColor.Green)))
                        fail("  Alice don't have 2 card and have the 2 Green in Hand.");
                    System.out.println("  Alice lay the 2 Green and pass.");
                    bob.playCard(game, bob.getiCard(0));
                    game.nextRound();
                    if (bob.getNbCard() != 2 || bob.hasCard(new CardNumber(2, ECardColor.Blue)))
                        fail("  Bob don't have 2 card in Hand and have the 2 Blue in Hand.");
                    System.out.println("  Bob lay the 2 Blue and pass.");
                } catch (UserException e) {
                    throw new RuntimeException(e);
                }

                try {
                    charles.playCard(game, charles.getiCard(0));
                    if (charles.getNbCard() != 2)
                        fail("  Charles don't have 2 cards in Hand.");
                    System.out.println("  Charles have 2 cards in Hand.");

                    charles.playCard(game, charles.getiCard(0));
                    fail("  Charles has successfully cheated.");
                } catch (UserException e) {
                    if (!charles.hasCard(new CardNumber(7, ECardColor.Blue)))
                        fail("  Charles got us.");
                    System.out.println("  Charles couldn't lay his 2nd card.");
                }

            }

            @Test
            public void AliceEndHerRoundTest() {
                game.nextRound();
                if (alice.getNbCard() != 3)
                    fail("Alice played a card without us knowing.");
                System.out.println("Alice have 3 cards in her Hand.");
            }

            @Test
            public void PlayAndPickInDeckTest() {
                try {
                    alice.playCard(game, alice.getiCard(0));
                } catch (UserException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Alice played a 2 Green.");
                try {
                    game.getDeck().giveCardToPlayer(game, alice);
                    fail("Alice successfully picked a card.");
                } catch (UserException e) {
                    if (alice.getNbCard() != 2)
                        fail("Alice don't have 2 cards.");
                    System.out.println("Alice have 2 cards in hand.");
                    if (!game.getDeck().hasCard(new CardNumber(6, ECardColor.Yellow)))
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
                    alice.playCard(game, alice.getiCard(1));
                    fail("  Alice played her 6 yellow card");
                    game.nextRound();
                } catch (UserException e) {
                    game.getDeck().giveCardToPlayerWithi(alice, 2);
                    System.out.println("  Alice got punished.");
                    game.nextRound();
                    if (game.getPlayers().get(game.getRoundOfPlayer()) != bob)
                        fail("  It's not the round of bob.");
                    System.out.println("  It's the round of bob.");
                    if (!alice.hasCard(new CardNumber(6, ECardColor.Yellow)) && !alice.hasCard(new CardNumber(4, ECardColor.Red)))
                        fail("  Alice don't have her 6 Yellow and 4 Red." + alice.getHand().toString());
                    System.out.println("  Alice has 6 Yellow and 4 Red.");
                }
            }

            @Test
            public void BobNotHisRoundTest() {
                if (!game.getPlayers().get(game.getRoundOfPlayer()).equals(alice))
                    fail("  It's not alice round.");
                System.out.println("  It's alice round.");

                try {
                    game.getDeck().giveCardToPlayer(game, bob);
                    fail("  Bob picked a card.");
                } catch (UserException e) {
                    game.getDeck().giveCardToPlayerWithi(bob, 2);
                    System.out.println("  Bob got punished.");
                    if (!game.getPlayers().get(game.getRoundOfPlayer()).equals(alice))
                        fail("  It's not alice round.");
                    System.out.println("  It's alice round.");
                }
            }
        }

    }

    @Nested
    class SecondTests {

        @BeforeEach
        public void init() {
            game = new Game();

            try {
                FileProcess.readFile("external/JeuTestCarteSimplePourTestUno.csv", game.getDeck());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            game.addPlayer("Alice");
            game.addPlayer("Bob");
            game.addPlayer("Charles");
            for (int i = 0; i < 2; i++) {
                for (Player player : game.getPlayers()) {
                    game.getDeck().giveCardToPlayerWithi(player, 1);
                }
            }
            ICard cardDeck = game.getDeck().getCards().get(0);
            game.getPile().addCard(cardDeck);
            game.getDeck().removeCard(cardDeck);

            alice = game.getPlayers().get(0);
            bob = game.getPlayers().get(1);
            charles = game.getPlayers().get(2);
        }

        @Nested
        class UnoMoveTest {
            @Test
            public void AliceUnoTest() {
                if (alice.getNbCard() != 2)
                    fail("Alice don't have 2 cards.");
                System.out.println("Alice have 2 cards.");
                try {
                    alice.playCard(game, alice.getiCard(0));
                    try {
                        alice.uno(game);
                    } catch (UserException e) {
                        fail("Alice can't Uno.");
                    }
                    game.nextRound();
                } catch (UserException e) {
                    fail("Alice couldn't play 2 Green.");
                }

                if (alice.getNbCard() != 1)
                    fail("Alice don't have 1 card");
                System.out.println("Alice have 1 card.");

                if (!game.getPile().getCards().get(game.getPile().getNbCard() - 1).equals(new CardNumber(2, ECardColor.Green)))
                    fail("Top pile is not 2 Green.");
                System.out.println("Top Pile is 2 Green.");

                if (!game.getPlayers().get(game.getRoundOfPlayer()).equals(bob))
                    fail("Current Player is not Bob.");
                System.out.println("Current Player is Bob.");
            }

            @Test
            public void AliceForgotUnoTest() {
                try {
                    alice.playCard(game, alice.getiCard(0));
                    game.nextRound();
                } catch (UserException e) {
                    fail("Alice didn't play 2 Green and didn't Uno.");
                }
                try {
                    bob.counterUno(game);
                    System.out.println("Alice got punished.");
                } catch (UserException e) {
                    fail("Bob couldn't counter Alice.");
                }

                if (alice.getNbCard() != 4)
                    fail("Alice don't have 4 cards.");
                System.out.println("Alice have 4 cards.");

                try {
                    if (!game.getPile().getTopCard().equals(new CardNumber(8, ECardColor.Green)))
                        fail("Top card in pile is not 8 Green.");
                    System.out.println("Top card in pile is 8 Green.");
                } catch (CardException e) {
                    throw new RuntimeException(e);
                }

                if (!game.getCurrentPlayer().equals(bob))
                    fail("Bob is not current player.");
                System.out.println("Bob is current player.");

            }

            @Test
            public void BobTryUnoButNotRoundTest() {
                if (!game.getCurrentPlayer().equals(alice))
                    fail("Alice is not current player.");
                System.out.println("Alice is current player.");
                try {
                    bob.uno(game);
                } catch (UserException e) {
                    System.out.println("Bob got punished.");

                    if (bob.getNbCard() != 4)
                        fail("Bob don't have 4 cards.");
                    System.out.println("Bob have 4 cards.");

                    if (!game.getCurrentPlayer().equals(alice))
                        fail("Alice is not current player.");
                    System.out.println("Alice is current player.");

                    try {
                        if (!game.getPile().getTopCard().equals(new CardNumber(8, ECardColor.Green)))
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
            game = new Game();

            try {
                FileProcess.readFile("external/JeuTestCartePasser.csv", game.getDeck());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            game.addPlayer("Alice");
            game.addPlayer("Bob");
            game.addPlayer("Charles");
            for (int i = 0; i < 3; i++) {
                for (Player player : game.getPlayers()) {
                    game.getDeck().giveCardToPlayerWithi(player, 1);
                }
            }
            ICard cardDeck = game.getDeck().getCards().get(0);
            game.getPile().addCard(cardDeck);
            game.getDeck().removeCard(cardDeck);

            alice = game.getPlayers().get(0);
            bob = game.getPlayers().get(1);
            charles = game.getPlayers().get(2);
        }

        @Test
        public void LegalMoveWithSkipTest() {
            if (!game.getCurrentPlayer().equals(alice))
                fail("Alice is not current player.");
            System.out.println("Alice is current player.");

            try {
                alice.playCard(game, alice.getiCard(0));
                System.out.println("Alice played Skip Red.");
                game.nextRound();
                System.out.println("Alice end her round.");
            } catch (UserException e) {
                fail("Alice couldn't play skip card.");
            }

            if (!game.getCurrentPlayer().equals(charles))
                fail("Charles is not current player.");
            System.out.println("Charles is current player.");

            try {
                charles.playCard(game, charles.getiCard(1));
                System.out.println("Charles played Skip Green.");
                game.nextRound();
                System.out.println("Charles end his round.");
            } catch (UserException e) {
                fail("Charles couldn't play skip card.");
            }

            if (!game.getCurrentPlayer().equals(bob))
                fail("Bob is not current player.");
            System.out.println("Bob is current player.");

            try {
                if (!game.getPile().getTopCard().equals(new Skip(ECardColor.Green)))
                    fail("Top card in pile is not Skip Green.");
                System.out.println("Top card in pile is Skip Green.");
            } catch (CardException e) {
                throw new RuntimeException(e);
            }

            try {
                bob.playCard(game, bob.getiCard(1));
                System.out.println("Bob played 6 Green.");
                game.nextRound();
                System.out.println("Bob end his round.");
            } catch (UserException e) {
                throw new RuntimeException(e);
            }

            if (!game.getCurrentPlayer().equals(charles))
                fail("Charles is not current player.");
            System.out.println("Charles is current player.");

            try {
                if (!game.getPile().getTopCard().equals(new CardNumber(6, ECardColor.Green)))
                    fail("Top card in pile is not 6 Green.");
                System.out.println("Top card in pile is 6 Green.");
            } catch (CardException e) {
                throw new RuntimeException(e);
            }


        }

        @Test
        public void IllegalMoveWithSimpleCardOnSkip() {
            // TODO
        }

        @Test
        public void IllegalMoveWithSkipOnSimpleCardTest() {
            if (!game.getCurrentPlayer().equals(alice))
                fail("Alice is not current player.");
            System.out.println("Alice is current player.");

            try {
                alice.playCard(game, alice.getiCard(1));
                System.out.println("Alice played a 9 Blue.");
                game.nextRound();
                System.out.println("Alice end her round.");
            } catch (UserException e) {
                fail("Alice couldn't play a 9 Blue.");
            }

            try {
                bob.playCard(game, bob.getiCard(2));
                System.out.println("Bob played a 7 Blue.");
                game.nextRound();
            } catch (UserException e) {
                fail("Bob couldn't play a 7 Blue.");
            }

            if (!game.getCurrentPlayer().equals(charles))
                fail("Charles is not current player.");
            System.out.println("Charles is current player.");

            if (charles.getNbCard() != 3)
                fail("Charles don't have 3 cards.");
            System.out.println("Charles have 3 cards.");

            try {
                charles.playCard(game, charles.getiCard(1));
                fail("Charles played a Skip Green.");
                game.nextRound();
                System.out.println("Charles end his round.");
            } catch (UserException e) {
                System.out.println("Charles couldn't play a Skip Green.");
                if (charles.getNbCard() != 3)
                    fail("Charles don't have 3 cards");
                System.out.println("Charles have 3 cards");
            }

        }
    }
}
