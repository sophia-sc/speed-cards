package model.persistence.tests;

import model.FullDeck;
import model.persistence.JsonReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Unit tests for JsonReader class
// This class references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReaderTest {

    FullDeck comparisonDeck;

    @BeforeEach
    public void runBefore() {
        comparisonDeck = new FullDeck();
    }

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            FullDeck deck = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderNoData() {
        JsonReader reader = new JsonReader("./data/testReaderNoData.json");
        String comparisonString = comparisonDeck.deckToString();
        try {
            FullDeck testDeck = reader.read();
            assertEquals(comparisonString, testDeck.deckToString());
        } catch (IOException e) {
            fail("Caught IOException when shouldn't have");
        }
    }

    @Test
    public void testReaderData() {
        JsonReader reader = new JsonReader("./data/testReaderData.json");
        comparisonDeck.editCard("C11", "p11", "a11", "o11");
        comparisonDeck.editCard("C10", "p10", "a10", "o10");
        String comparisonString = comparisonDeck.deckToString();
        try {
            FullDeck testDeck = reader.read();
            assertEquals(comparisonString, testDeck.deckToString());
        } catch (IOException e) {
            fail("Caught IOException when shouldn't have");
        }
    }

}
