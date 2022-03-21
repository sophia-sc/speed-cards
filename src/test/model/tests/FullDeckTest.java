package model.tests;

import model.Card;
import model.FullDeck;
import model.exceptions.InvalidCardException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for FullDeck class
public class FullDeckTest {

    FullDeck deck;

    @BeforeEach
    public void runBefore() {
        deck = new FullDeck();
    }

    @Test
    public void testConstructor() {
        Map<String, Card> cards = deck.getCards();
        assertEquals(52, cards.size());

        Card card = deck.getCard("C1");
        assertEquals("blank", card.getPerson());
    }

    @Test
    public void testCheckValidTrue() {
        try {
            deck.checkValid("H3");
        } catch (InvalidCardException e) {
            fail("Caught invalid card name when shouldn't have");
        }
    }

    @Test
    public void testCheckValidFalse() {
        try {
            deck.checkValid("g");
            fail("Expected InvalidCardException");

        } catch (InvalidCardException e) {

        }
    }

    @Test
    public void testEditCard() {
        deck.editCard("H12", "Sophia", "Typing", "Computer");
        deck.editCard("S2", "Maria", "Reading", "Poster");

        Card testCardH = deck.getCard("H12");
        assertEquals("Sophia", testCardH.getPerson());
        assertEquals("Typing", testCardH.getAction());
        assertEquals("Computer", testCardH.getObject());

        Card testCardS = deck.getCard("S2");
        assertEquals("Maria", testCardS.getPerson());
        assertEquals("Reading", testCardS.getAction());
        assertEquals("Poster", testCardS.getObject());
    }

    @Test
    public void testEditCardPersonSkip() {
        deck.editCard("H12", "skip", "Typing", "Computer");

        Card testCard = deck.getCard("H12");
        assertEquals("blank", testCard.getPerson());
        assertEquals("Typing", testCard.getAction());
        assertEquals("Computer", testCard.getObject());
    }

    @Test
    public void testEditCardActionSkip() {
        deck.editCard("H12", "Sophia", "skip", "Computer");

        Card testCard = deck.getCard("H12");
        assertEquals("Sophia", testCard.getPerson());
        assertEquals("blank", testCard.getAction());
        assertEquals("Computer", testCard.getObject());
    }

    @Test
    public void testEditCardObjectSkip() {
        deck.editCard("H12", "Sophia", "Typing", "skip");

        Card testCard = deck.getCard("H12");
        assertEquals("Sophia", testCard.getPerson());
        assertEquals("Typing", testCard.getAction());
        assertEquals("blank", testCard.getObject());
    }

    @Test
    public void testDeckToString() {
        deck.editCard("C2", "Sophia", "Typing", "Computer");

        String deckString = deck.deckToString();
        String expected = "C1  PERSON: blank ACTION: blank OBJECT: blank";
        assertEquals(expected, deckString.substring(0, 45));

        expected = "C2  PERSON: Sophia ACTION: Typing OBJECT: Computer";
        assertEquals(expected, deckString.substring(46, 96));
    }
}
