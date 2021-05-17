package ui;

import model.Business;
import model.Clicker;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.menus.CollectionMenu;
import ui.menus.DeveloperMenu;
import ui.menus.MarketplaceMenu;
import ui.menus.UpgradeMenu;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/*
 * Represents the main menu in which the Sneaker Tycoon game is played.
 */

public class SneakerTycoonApp extends Menu {

    private static final String JSON_STORE = "./data/game.json";

    private Business myBusiness;
    private Clicker clicker;

    public SneakerTycoonApp() {
        runMenu();
    }

    @Override
    protected void init() {
        input = new Scanner(System.in);
        System.out.println("Welcome to Sneaker Tycoon!");
        System.out.println("Do you want to load a file or start a new game?");
        System.out.println("Type \"load\" to load your saved game, or enter the name of your new business.");
        String command = input.nextLine();
        if (command.equals("load")) {
            loadGame();
        } else {
            myBusiness = new Business(command);
            clicker = new Clicker(myBusiness);
            System.out.println("If this is your first time playing, enter \"h\" for some help!");
        }
    }

    @Override
    // EFFECTS: displays the main menu of options
    protected void displayMenu() {
        System.out.println("Welcome to " + myBusiness.getBusinessName() + ", your sneaker-reselling business!");
        System.out.println("Select from: ");
        System.out.println("\tClick (f)");
        System.out.println("\tUpgrade (u)");
        System.out.println("\tMy Collection (c)");
        System.out.println("\tMarketplace (m)");
        System.out.println("\tDeveloper Menu (?)");
        System.out.println("\tHelp (h)");
        System.out.println("\tSave (s)");
        System.out.println("\tQuit (q)");
    }

    // MODIFIES: this
    // EFFECTS: processes user command if one of (f, u, c, m), else sends command to secondary method
    @Override
    protected void processMenuCommand(String command) {
        switch (command) {
            case "f":
                click();
                break;
            case "u":
                UpgradeMenu upgradeMenu = new UpgradeMenu(clicker);
                upgradeMenu.runMenu();
                displayMenu();
                break;
            case "c":
                CollectionMenu collectionMenu = new CollectionMenu(myBusiness);
                collectionMenu.runMenu();
                displayMenu();
                break;
            case "m":
                MarketplaceMenu marketplaceMenu = new MarketplaceMenu(myBusiness);
                marketplaceMenu.runMenu();
                displayMenu();
                break;
            default:
                processMenuCommandSecondary(command);
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    protected void processMenuCommandSecondary(String command) {
        switch (command) {
            case "?":
                DeveloperMenu developerMenu = new DeveloperMenu(myBusiness);
                developerMenu.runMenu();
                displayMenu();
                break;
            case "d":
                displayMenu();
                break;
            case "h":
                printInstructions();
                break;
            case "s":
                saveGame();
                break;
            default:
                printError();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: clicks one time, prints result information.
    private void click() {
        clicker.click();
        System.out.print("You earned $" + clicker.getGainedPerClick() + ". ");
        System.out.print("Your total balance is $" + myBusiness.getBalance() + ". ");
        System.out.println("Enter \"d\" to display the main menu.");
    }

    // EFFECTS: prints out game instructions
    private void printInstructions() {
        System.out.println("Earn money by clicking. (f)");
        System.out.println("Spend your money to upgrade your clicking power. (u)");
        System.out.println("View your collection and sell sneakers for a profit. (c)");
        System.out.println("Shop in a marketplace. Look for good deals and rare shoes! (m)");
        System.out.println("Test the game without actually spending time playing it (?)");
    }

    // Referenced JsonSerializationDemo
    // EFFECTS: saves game state to file
    private void saveGame() {
        JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
        try {
            jsonWriter.open();
            jsonWriter.write(clicker);
            jsonWriter.close();
            System.out.println("Saved game state to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }

    }

    // Referenced JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: loads game state from file
    private void loadGame() {
        JsonReader jsonReader = new JsonReader(JSON_STORE);
        try {
            clicker = jsonReader.read();
            myBusiness = clicker.getMyBusiness();
            System.out.println("Loaded " + myBusiness.getBusinessName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
