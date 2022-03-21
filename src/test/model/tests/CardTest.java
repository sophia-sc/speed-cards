package model.tests;

import model.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for Card class
class CardTest {

    Card card;

    @BeforeEach
    public void runBefore() {
        card = new Card();
        card.setPerson("Sophia");
        card.setAction("Running");
        card.setObject("Pen");
    }

    @Test
    public void testStringBankEntry() {
        String result = card.stringCardData();
        String expected = "PERSON: Sophia ACTION: Running OBJECT: Pen";
        assertEquals(expected, result);
    }

    @Test
    public void testTranslateZero() {
        assertEquals("Sophia ", card.translate(0));
    }

    @Test
    public void testTranslateOne() {
        assertEquals("Running ", card.translate(1));
    }

    @Test
    public void testTranslateTwo() {
        assertEquals("with Pen\n", card.translate(2));
    }
}