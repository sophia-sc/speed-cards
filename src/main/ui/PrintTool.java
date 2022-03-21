package ui;

import model.FullDeck;

public class PrintTool {
    // EFFECTS: prints fullDeck
    public void doPrint(FullDeck fullDeck) {
        System.out.println(fullDeck.deckToString());
    }
}
