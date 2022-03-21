package ui;

import model.*;
import model.exceptions.InvalidCardException;
import model.exceptions.InvalidNumCardsException;
import model.persistence.JsonReader;
import model.persistence.JsonWriter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

// Speed cards application

// This class references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/TellerApp
public class SpeedCardsApp extends JFrame {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private static final String JSON_STORE = "./data/deck.json";
    private FullDeck fullDeck;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private PrintTool printTool;

    // EFFECTS: runs speed cards app
    public SpeedCardsApp() {
        runSpeedCards();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runSpeedCards() {
        System.out.println("Welcome to the Speed Cards App!");
        boolean running = true;
        String command = null;

        init();

        while (running) {
            displayMenu();
            command = input.next();

            if (command.equals("quit")) {
                goodbye();
                running = false;

            } else {
                try {
                    processCommand(command);
                } catch (InvalidCardException e) {
                    System.out.println("Invalid card. Type \"help\" for info on how to enter card names.");
                } catch (InvalidNumCardsException e) {
                    System.out.println("Number of cards must be between 1 and 52.");
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes FullDeck and scanner
    private void init() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        try {
            fullDeck = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to load PAO deck at" + JSON_STORE + ". New blank deck was created.");
            fullDeck = new FullDeck();
        }
        input = new Scanner(System.in);
        input.useDelimiter("\n");

        initTools();
    }

    private void initTools() {
        printTool = new PrintTool();
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("------------------------------------------------------------------------------------"
                + "\nType \"print\" to view your deck."
                + "\nType \"edit\" to edit your deck."
                + "\nType \"save\" to save your deck."
                + "\nType \"reset\" to reset your deck."
                + "\nType \"practice\" to practice memorizing."
                + "\nType \"quit\" to quit.");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    //          throws InvalidNumCardsException if number of cards to practice with is not an int
    private void processCommand(String command) throws InvalidCardException, InvalidNumCardsException {
        if (command.equals("print")) {
            printTool.doPrint(fullDeck);
        } else if (command.equals("edit")) {
            doEdit();
        } else if (command.equals("save")) {
            doSave();
        } else if (command.equals("reset")) {
            doReset();
        } else if (command.equals("practice")) {
            System.out.println("How many cards to practice with?");
            try {
                doPractice(input.nextInt());
            } catch (InputMismatchException e) {
                command = input.next();
                throw new InvalidNumCardsException();
            }
        } else if (command.equals("help")) {
            doHelp();
        }
    }

    // MODIFIES: this
    // EFFECTS: edits a card in fullDeck
    private void doEdit() throws InvalidCardException {
        System.out.println("Card to edit: ");
        String cardToEdit = input.next();
        fullDeck.checkValid(cardToEdit);

        System.out.println("Type skip if you do not wish to change the data.");
        System.out.println("Change PERSON to: ");
        String person = input.next();

        System.out.println("Change ACTION to: ");
        String action = input.next();

        System.out.println("Change OBJECT to: ");
        String object = input.next();

        String cardData = fullDeck.editCard(cardToEdit, person, action, object);
        System.out.println("New card data is:\n" + cardData);
    }

    // MODIFIES: this
    // EFFECTS: saves fullDeck to file
    private void doSave() {
        try {
            jsonWriter.open();
            jsonWriter.write(fullDeck);
            jsonWriter.close();
            System.out.println("Saved deck to " + JSON_STORE + ".");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save deck to" + JSON_STORE + ".");
        }
    }

    // EFFECTS: conducts a timed practice session
    private void doPractice(int numCardsInDeck) throws InvalidNumCardsException {

        RandomDeck practiceDeck = new RandomDeck(numCardsInDeck);
        System.out.println("Begin memorization. Type \"d\" when finished.");
        System.out.println(practiceDeck.deckCardsToString());
        long start = System.currentTimeMillis();

        boolean running = true;
        while (running) {
            String command = input.next();
            if (command.equals("d")) {
                running = false;
            }
        }
        long end = System.currentTimeMillis();

        System.out.println("Correct answer was: \n" + practiceDeck.translatePAO(fullDeck));
        System.out.println("Your time was: " + formatTime(end - start));
    }

    // EFFECTS: prints help menu
    private void doHelp() {
        System.out.println("Cards should be entered as a capital letter representing the suit followed by a "
                + "number.\nThe suits are represented as follows:\nC - clubs\nD - diamonds\nH - hearts\n"
                + "S - spades\nThe numbers are represented as follows:\n1 - ace\n11 - jack\n12 - queen\n13 - king"
                + "\nFor example, C12 would be the queen of clubs.");
    }

    // MODIFIES: this
    // EFFECTS: resets fullDeck to a blank deck
    private void doReset() {
        System.out.println("Are you sure you would like to empty all data from your deck?"
                + " You will not be able to undo this action. (yes/no)");
        String command = input.next();
        if (command.equals("yes")) {
            fullDeck = new FullDeck();
            doSave();
        } else {
            System.out.println("Deck was not reset.");
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to save deck
    private void goodbye() {
        System.out.println("Would you like to save your deck before quitting? (yes/no)");
        String command = input.next();
        if (command.equals("yes")) {
            doSave();
        } else if (command.equals("no")) {
            System.out.println("Deck was not saved.");
        } else {
            goodbye();
        }
    }

    // EFFECTS: translates milliseconds into minutes and seconds
    private String formatTime(long time) {
        int seconds = (int) time / 1000;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return minutes + " minutes " + seconds + " seconds";
    }
}
