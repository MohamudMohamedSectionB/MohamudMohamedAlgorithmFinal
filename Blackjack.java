
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Card {
    private final String suit;
    private final String value;

    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value + " of " + suit;
    }
}

class Deck {
    private final List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        for (String suit : suits) {
            for (String value : values) {
                cards.add(new Card(suit, value));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card dealCard() {
        return cards.remove(0);
    }
}

class Player {
    protected List<Card> hand;

    public Player() {
        hand = new ArrayList<>();
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void displayHand() {
        for (Card card : hand) {
            System.out.println(card);
        }
    }

    public int calculateHandValue() {
        int value = 0;
        for (Card card : hand) {
            String cardValue = card.getValue();
            switch (cardValue) {
                case "Jack", "Queen", "King" -> value += 10;
                case "Ace" -> value += 11;
                default -> value += Integer.parseInt(cardValue);
            }
        }
        return value;
    }
}

class Dealer extends Player {
    public void showPartialHand() {
        System.out.println("Dealer's hand:");
        System.out.println(hand.get(0)); // Show only the first card
    }
}

class Game {
    private final Deck deck;
    private final Player player;
    private final Dealer dealer;

    public Game() {
        deck = new Deck();
        player = new Player();
        dealer = new Dealer();
    }

    public void start() {
        deck.shuffle();
        player.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());
        player.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());

        player.displayHand();
        dealer.showPartialHand();

        playPlayerTurn();
        if (player.calculateHandValue() <= 21) {
            playDealerTurn();
        }
        determineWinner();
    }

    private void playPlayerTurn() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Do you want to hit or stand? (h/s)");
            String choice = scanner.next();
            if (choice.equalsIgnoreCase("h")) {
                player.addCard(deck.dealCard());
                player.displayHand();
                if (player.calculateHandValue() > 21) {
                    System.out.println("You busted!");
                    break;
                }
            } else {
                break;
            }
        }
    }

    private void playDealerTurn() {
        System.out.println("Dealer's turn:");
        dealer.displayHand();
        while (dealer.calculateHandValue() < 17) {
            dealer.addCard(deck.dealCard());
            dealer.displayHand();
        }
    }

    private void determineWinner() {
        int playerValue = player.calculateHandValue();
        int dealerValue = dealer.calculateHandValue();
        System.out.println("Player's hand value: " + playerValue);
        System.out.println("Dealer's hand value: " + dealerValue);

        if (playerValue > 21) {
            System.out.println("Dealer wins!");
        } else if (dealerValue > 21 || playerValue > dealerValue) {
            System.out.println("Player wins!");
        } else if (playerValue < dealerValue) {
            System.out.println("Dealer wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
