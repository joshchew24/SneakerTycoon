package ui;

import model.Business;
import model.sneakers.Sneaker;

import java.util.List;
import java.util.Scanner;

/*
 * Abstract class containing menu behaviours.
 */

public abstract class Menu {

    protected Scanner input;

    // referenced TellerApp and FerryService
    // MODIFIES: this
    // EFFECTS: processes user input
    protected void runMenu() {
        boolean keepGoing = true;
        String command;
        init();

        displayMenu();

        while (keepGoing) {
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processMenuCommand(command);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    protected void init() {
        input = new Scanner(System.in);
    }

    // EFFECTS: prints a shortened list of Sneakers as menu options for purchasing/selling
    protected void printSneakerOptions(List<Sneaker> sneakers) {
        for (int i = 1; i <= sneakers.size(); i++) {
            System.out.println("(" + i + "): " + sneakers.get(i - 1).getName() + ": $"
                    + sneakers.get(i - 1).getMarketPrice());
        }
    }

    // EFFECTS: prints error message
    protected void printError() {
        System.out.println("That is not a valid command. Please try again or enter \"q\" to quit.");
    }

    // EFFECTS: prints business balance message
    protected void printBalance(Business business) {
        System.out.println("Your account balance is $" + business.getBalance() + ".");
    }


    // EFFECTS: displays menu of options
    protected abstract void displayMenu();

    // MODIFIES: this
    // EFFECTS: processes user command
    protected abstract void processMenuCommand(String command);

}
