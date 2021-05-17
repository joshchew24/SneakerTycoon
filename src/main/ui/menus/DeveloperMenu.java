package ui.menus;

import model.Business;
import ui.Menu;

import java.util.InputMismatchException;

/*
 * Represents a menu to access developer commands.
 */

public class DeveloperMenu extends Menu {

    Business myBusiness;

    // EFFECTS: constructs an object which runs the developer menu containing methods to test the program
    public DeveloperMenu(Business business) {
        myBusiness = business;
    }

    // EFFECTS: displays menu options
    @Override
    protected void displayMenu() {
        System.out.println("Enter \"set\" to set a custom balance for your business account.");
        System.out.println("Enter \"q\" to return to main menu.");
    }

    // note: uses switch for modularity so that more developer methods can be added later as needed
    @Override
    protected void processMenuCommand(String command) {
        switch (command) {
            case "set":
                setAccountBalance();
                break;
            default:
                printError();
                break;
        }
        displayMenu();
    }

    // handling case when input is not a float was referenced from:
    // https://stackoverflow.com/questions/12702076/try-catch-with-inputmismatchexception-creates-infinite-loop
    // EFFECTS: takes next input as float and sets it as account balance.
    private void setAccountBalance() {
        float amount = 0;
        boolean error = false;
        System.out.print("Set Account Balance: $");
        do {
            try {
                amount = input.nextFloat();
                error = false;
            } catch (InputMismatchException e) {
                System.out.print("That is not a valid amount of currency, please try again: $");
                error = true;
                input.next();
            }
        } while (error);
        myBusiness.setBalance(amount);
        printBalance(myBusiness);
    }
}
