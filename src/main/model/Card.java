package model;

import org.json.JSONObject;

// Represents a Card with an associated person, action, and object
public class Card {

    private String person;
    private String action;
    private String object;

    // EFFECTS: creates a new Card with all fields blank
    public Card() {
        person = "blank";
        action = "blank";
        object = "blank";
    }

    // getters
    public String getPerson() {
        return person;
    }

    public String getAction() {
        return action;
    }

    public String getObject() {
        return object;
    }

    // setters
    public void setPerson(String person) {
        this.person = person;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setObject(String object) {
        this.object = object;
    }

    // EFFECTS: returns card data formatted as a single string
    public String stringCardData() {
        return ("PERSON: " + person + " ACTION: " + action + " OBJECT: " + object);
    }

    // REQUIRES: i is 0, 1, or 2
    // EFFECTS: translates card based on PAO system
    public String translate(int i) {
        if (i == 0) {
            return person + " ";
        } else if (i == 1) {
            return action + " ";
        } else {
            return "with " + object + "\n";
        }
    }

    // EFFECTS: returns this as a JSONObject
    // This method references code from this repo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("person", person);
        json.put("action", action);
        json.put("object", object);
        return json;
    }
}
