package model.tests;

import model.CardNames;
import model.FullDeck;
import model.RandomDeck;
import model.exceptions.InvalidNumCardsException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

// Unit tests for RandomDeck class
public class RandomDeckTest {

    FullDeck fullDeck = new FullDeck();
    RandomDeck deck;

    @Test
    public void testConstructor() {
        try {
            deck = new RandomDeck(8);
        } catch (InvalidNumCardsException e) {
            fail("Caught invalid card number when shouldn't have");
        }
        ArrayList<String> cards = deck.getCards();
        assertEquals(8, cards.size());

        boolean valid = true;
        for (String s : cards) {
            boolean contains = false;
            for (CardNames cn : CardNames.values()) {
                contains = contains || s.equals(cn.toString());
            }
            valid = valid && contains;
        }
        assertTrue(valid);
    }

    @Test
    public void testConstructorZeroCardsException() {
        try {
            deck = new RandomDeck(0);
            fail("Expected InvalidNumCardsException");
        } catch (InvalidNumCardsException e) {

        }
        assertNull(deck);
    }

    @Test
    public void testConstructorTooManyCardsException() {
        try {
            deck = new RandomDeck(53);
            fail("Expected InvalidNumCardsException");
        } catch (InvalidNumCardsException e) {

        }
        assertNull(deck);
    }

    @Test
    public void testDeckCardsToString() {
        try {
            deck = new RandomDeck(6);
        } catch (InvalidNumCardsException e) {
            fail("Caught invalid card exception when shouldn't have");
        }
        ArrayList<String> cards = deck.getCards();
        String deckData = deck.deckCardsToString();

        boolean valid = true;
        for (String s : cards) {
            valid = valid && deckData.contains(s);
        }
        assertTrue(valid);
    }

    @Test
    public void testTranslateThree() {
        try {
            deck = new RandomDeck(3);
        } catch (InvalidNumCardsException e) {
            fail("Caught invalid card number when shouldn't have");
        }
        String expected = "blank blank with blank";
        assertEquals(expected, deck.translatePAO(fullDeck));
    }

    @Test
    public void testTranslateFour() {
        try {
            deck = new RandomDeck(4);
        } catch (InvalidNumCardsException e) {
            fail("Caught invalid card number when shouldn't have");
        }
        String expected = "blank blank with blank\nblank ";
        assertEquals(expected, deck.translatePAO(fullDeck));
    }
}
