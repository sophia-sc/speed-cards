package model;


import model.exceptions.InvalidCardException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

// Represents a full deck of playing cards, each with their own associations
public class FullDeck {

    private HashMap<String, Card> cards;

    // EFFECTS: creates new FullDeck and initializes a bucket for each card.
    public FullDeck() {
        cards = new HashMap<>(52);
        this.init();
    }

    // getter
    public Map<String, Card> getCards() {
        return cards;
    }

    // REQUIRES: cardName is a valid card name
    // EFFECTS: returns card at given key
    public Card getCard(String cardName) {
        return cards.get(cardName);
    }

    // EFFECTS: creates a string of all card names and corresponding data
    public String deckToString() {
        String stringBank = "";
        for (CardNames c : CardNames.values()) {
            String cardName = c.toString();
            Card e = cards.get(cardName);
            String cardNameString = cardName;
            if (cardNameString.length() == 2) {
                cardNameString = cardNameString + " ";
            }
            stringBank = stringBank + cardNameString + " " + e.stringCardData() + "\n";
        }
        return stringBank.substring(0, stringBank.length() - 1);
    }

    // EFFECTS: throws InvalidCardException if cardName is not in CardNames
    public void checkValid(String cardName) throws InvalidCardException {
        boolean validCard = false;
        for (CardNames cn : CardNames.values()) {
            validCard = validCard || cardName.equals(cn.toString());
        }
        if (!validCard) {
            throw new InvalidCardException();
        }
    }

    // REQUIRES: cardName is a valid card name
    // MODIFIES: this
    // EFFECTS: changes the data of given card
    public String editCard(String cardName, String person, String action, String object) {

        Card cardToEdit = getCard(cardName);

        String skip = "skip";
        if (!person.equals(skip)) {
            cardToEdit.setPerson(person);
        }
        if (!action.equals(skip)) {
            cardToEdit.setAction(action);
        }
        if (!object.equals(skip)) {
            cardToEdit.setObject(object);
        }

        return cardToEdit.stringCardData();
    }

    // MODIFIES: this
    // EFFECTS: loads this with a bucket corresponding to each card in Cards
    private void init() {
        for (CardNames cn : CardNames.values()) {
            cards.put(cn.toString(), new Card());
        }
    }

    // EFFECTS: returns this as a JSONObject
    // This method references code from this repo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        for (CardNames cn : CardNames.values()) {
            String cardName = cn.toString();
            Card card = getCard(cardName);
            jsonObject.put(cardName, card.toJson());
        }

        return jsonObject;
    }

}
