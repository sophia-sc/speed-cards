package model;

import model.exceptions.InvalidNumCardsException;

import java.util.*;

// Represents a random assortment of cards pulled from a FullDeck
public class RandomDeck {

    private ArrayList<String> cards;

    // REQUIRES: numCardsInDeck > 0
    // EFFECTS: creates Deck of numCardsInDeck cards
    //          throws InvalidNumCardsException if numCardsInDeck is not between 1 and 52 (inclusive)
    public RandomDeck(int numCardsInDeck) throws InvalidNumCardsException {
        if (numCardsInDeck <= 0 || numCardsInDeck > 52) {
            throw new InvalidNumCardsException();
        }
        cards = new ArrayList<>(numCardsInDeck);
        loadRandom(numCardsInDeck);
    }

    public ArrayList<String> getCards() {
        return cards;
    }

    // EFFECTS: translates deck according to PAO system
    public String translatePAO(FullDeck fullDeck) {
        String paoString = "";
        Integer counter = 0;
        for (String s : cards) {
            Card cardToTranslate = fullDeck.getCard(s);
            paoString = paoString + cardToTranslate.translate(counter % 3);
            counter++;
        }
        if (paoString.charAt(paoString.length() - 1) == '\n') {
            return paoString.substring(0, paoString.length() - 1);
        } else {
            return paoString;
        }
    }

    // EFFECTS: creates a string of only the card names of each card
    public String deckCardsToString() {
        String deckString = "";
        for (String s : cards) {
            deckString = deckString + s + " ";
        }
        return deckString.substring(0, deckString.length() - 1);
    }

    // REQUIRES: numCardsInDeck <= capacity of deck
    // MODIFIES: this
    // EFFECTS: loads this with cards from fullDeck in a random order
    public void loadRandom(int numCardsInDeck) {
        ArrayList<String> cardNames = new ArrayList<>();
        for (CardNames cn : CardNames.values()) {
            cardNames.add(cn.toString());
        }
        Collections.shuffle(cardNames);

        for (int i = 0; i < numCardsInDeck; i++) {
            cards.add(cardNames.get(i));
        }
    }
}
