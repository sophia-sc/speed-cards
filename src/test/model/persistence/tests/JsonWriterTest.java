package model.persistence.tests;

import model.FullDeck;
import model.persistence.JsonReader;
import model.persistence.JsonWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Unit tests for JsonWriter class
// This class references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterTest {

    FullDeck testDeck;
    FullDeck comparisonDeck;

    @BeforeEach
    public void runBefore() {
        testDeck = new FullDeck();
        comparisonDeck = new FullDeck();
    }

    @Test
    public void testIOException() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterNoData() {

        JsonWriter writer = new JsonWriter("./data/testWriterNoData.json");
        String comparisonString = comparisonDeck.deckToString();
        try {
            writer.open();
            writer.write(testDeck);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNoData.json");
            testDeck = reader.read();

            assertEquals(comparisonString, testDeck.deckToString());
        } catch (IOException e) {
            fail("Caught IOException when shouldn't have");
        }
    }

    @Test
    public void testWriterData() {
        JsonWriter writer = new JsonWriter("./data/testWriterData.json");
        testDeck.editCard("C1", "p1", "a1", "o1");
        testDeck.editCard("H2", "p2", "a2", "o2");
        comparisonDeck.editCard("C1", "p1", "a1", "o1");
        comparisonDeck.editCard("H2", "p2", "a2", "o2");
        String comparisonString = comparisonDeck.deckToString();
        try {
            writer.open();
            writer.write(testDeck);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterData.json");
            testDeck = reader.read();

            assertEquals(comparisonString, testDeck.deckToString());
        } catch (IOException e) {
            fail("Caught IOException when shouldn't have");
        }
    }

}
