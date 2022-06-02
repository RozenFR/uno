# PROJET UNO
*Université de Metz - UFR MIM*\
*Créé par Romain LY alias Rozen*

## Version
Java 18.0.1.1
JavaFX 18.0.1 

# Conception et UML
![UML UNO 2021-2022](/images/uml.png)
Cette UML est divisé en 4 partie :
- card : Concernant les Cartes
  - card : Représentation des cartes
  - playcard : la façon dont les cartes sont joués et interagissent entre elles
- parse : Une chaîne de responsabilité pour récupérer les cartes dans un fichier
- test : un package contenant des tests de fonctionnements
- user : La représentation des joueurs et du jeux

# Résultat des tests
## Partie 1
### Alice Joue une carte simple avec la bonne couleur
```text
Alice is current player.
Alice have 3 cards in her hand.
Alice played 2 green.
Alice have 6 yellow and 1 red.
The pile has 2 cards.
Bob is current player.
```

### Bob joue une carte de couleur différente mais de même valeur
```text
Alice is current player.
Alice have 3 cards in her hand.
Alice played 2 green.
Alice have 6 yellow and 1 red.
The pile has 2 cards.
Bob is current player.
Bob is the current player.
Bob have 3 cards in his hand.
Bob played 2 blue.
Bob have 4 yellow and 9 red.
Pile have 3 cards.
Charles is current player.
```

### Alice joue une carte illégal
```text
Alice can't play 6 yellow.
Alice couldn't play her 6 yellow.
Alice have 6 Yellow in Hand.
Alice have 3 cards.
```

### Charles joue 2 cartes d'affilés
```text
Alice lay the 2 Green and pass.
Bob lay the 2 Blue and pass.
Charles have 2 cards in Hand.
Charles couldn't lay his 7 Blue.
Charles have 2 cards.
```

### Alice met fin à son tour
```text
Alice have 3 cards in her Hand.
```

### Alice joue et pioche
```text
Alice played a 2 Green.
Alice didn't picked a card.
Alice have 2 cards in hand.
The 6 Yellow is in the deck.
```

### Alice action illégal
```text
Alice got punished.
It's the round of bob.
Alice have 5 cards.
Alice has 6 Yellow and 4 Red.
Nex card in deck is 2 Green.
```

### Bob joue alors que c'est pas son tour
```text
It's alice round.
Bob got punished.
It's alice round.
Bob have 6 Yellow and 4 Red
```

## Partie 2
### Alice Dit Uno !
```text
Alice have 2 cards.
Alice have 1 card.
Top Pile is 2 Green.
Current Player is Bob.
```

### Alice oublie de dire Uno et Bob dit contre Uno
```text
Alice got punished.
Alice have 4 cards.
Top card in pile is 8 Green.
Bob is current player.
```

### Bob essaie de dire Uno alors que c'est pas son tour
```text
Alice is current player.
Bob got punished.
Bob have 4 cards.
Alice is current player.
Top card in pile is 8 Green.
```

## Partie 2
### Action légal avec le passe tour
```text
Alice is current player.
Alice played Skip Red.
Alice end her round.
Charles is current player.
Top card in pile is Skip Red.
Charles played Skip Green.
Charles end his round.
Bob is current player.
Top card in pile is Skip Green.
Bob played 6 Green.
Bob end his round.
Charles is current player.
Top card in pile is 6 Green.
```

### Action Illégal avec le passe tour sur une carte simple
```text
Alice is current player.
Alice played a 9 Blue.
Alice end her round.
Bob played a 7 Blue.
Bob end his round.
Charles is current player.
Charles have 3 cards.
Charles couldn't play a Skip Green.
Charles have 3 cards
```

### Action Illegal avec le passe tour sur un carte passe tour
```text
Alice is current player.
Alice play her Skip Red.
Alice end her round.
Bob end his round.
Charles is current player.
Charles have 3 cards.
Charles couldn't play his 1 Blue.
Charles have 3 cards.
```

## Partie 4
### Action légal avec le +2
```text
Alice is current player.
Bob have 3 cards.
Alice play +2 Green
Alice end her round.
Bob is current player.
Bob is forced to picked 2 cards and end his round.
Bob have 5 cards.
Bob end his round.
Bob have 5 cards
Charles is current player.
Charles play his 1 Green.
Charles end his round.
Charles have 2 cards.
```

### Action Legal avec +2 en cumul (plusieurs +2)
```text
Alice is current player.
Alice picked a card from deck.
Alice end her round.
Bob is current player.
Bob picked a card.
Bob end his round.
Charles is current player.
Charles play his +2 Green.
Charles end his round.
Alice is current player.
Bob have 4 cards.
Alice played her +2 Green.
Alice end her round.
Bob is current player.
Bob have 8 cards.
Bob end his round.
Charles is current player.
```

## Partie 5
### Action Légal avec changement de couleur
```text
Alice is current player.
Alice play a Change Color and set Blue
Alice end her round
Bob is current player.
Bob play a 7 Blue.
Bob end his round.
```

### Action Illegal avec changement de couleur
```text
Alice is current player.
Alice play a Change Color and set Blue
Alice end her round
Bob is current player.
Bob couldn't play his 7 Blue.
Bob end his round.
```
