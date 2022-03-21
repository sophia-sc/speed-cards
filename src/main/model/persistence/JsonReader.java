package model.persistence;

import model.CardNames;
import model.FullDeck;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
// This class references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    //          throws IOException if an error occurs reading data from file
    public FullDeck read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseFullDeck(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses FullDeck from JSON object and returns it
    private FullDeck parseFullDeck(JSONObject jsonObject) {
        FullDeck fullDeck = new FullDeck();
        for (CardNames cn : CardNames.values()) {
            String cardName = cn.toString();
            JSONObject cardJsonObject = jsonObject.getJSONObject(cardName);
            parseCard(fullDeck, cardName, cardJsonObject);
        }
        return fullDeck;
    }

    // MODIFIES: fullDeck
    // EFFECTS: parses card from JSONObject and adds it to FullDeck
    private void parseCard(FullDeck fullDeck, String cardName, JSONObject jsonObject) {
        String person = jsonObject.getString("person");
        String action = jsonObject.getString("action");
        String object = jsonObject.getString("object");
        fullDeck.editCard(cardName, person, action, object);
    }

}
