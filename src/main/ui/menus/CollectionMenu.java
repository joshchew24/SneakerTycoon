package ui.menus;

import model.Business;
import model.sneakers.Sneaker;
import ui.Menu;

import java.util.List;

/*
 * Represents a menu to interact with the player's sneaker collection.
 */

public class CollectionMenu extends Menu {

    Business myBusiness;

    // EFFECTS: constructs an object which runs the menu to interact with your collection
    public CollectionMenu(Business business) {
        myBusiness = business;
    }

    // TODO: *later* unlimited collection size, and collection statistics
    // EFFECTS: displays collection info, and a menu with options to sell Sneakers in collection
    @Override
    protected void displayMenu() {
        myBusiness.randomizeCollectionMarketPrices();
        System.out.println("Welcome to your collection. You have " + myBusiness.getCollection().size() + " sneakers.");
        System.out.println(myBusiness.getCollectionInfo());
        printBalance(myBusiness);
        printSneakerOptions(myBusiness.getCollection());
        System.out.println("Enter the number of the shoe you wish to sell, or \"q\" to exit the collection.");
    }

    // MODIFIES: this
    // EFFECTS: processes selection if in [1,4], otherwise use secondary processor
    protected void processMenuCommand(String command) {
        switch (command) {
            case "1":
                printSellResult(myBusiness.sell(1));
                printSneakerOptions(myBusiness.getCollection());
                break;
            case "2":
                printSellResult(myBusiness.sell(2));
                printSneakerOptions(myBusiness.getCollection());
                break;
            case "3":
                printSellResult(myBusiness.sell(3));
                printSneakerOptions(myBusiness.getCollection());
                break;
            case "4":
                printSellResult(myBusiness.sell(4));
                printSneakerOptions(myBusiness.getCollection());
                break;
            default:
                processMenuCommandSecondary(command);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes selection if in [5,8], otherwise use tertiary processor
    private void processMenuCommandSecondary(String command) {
        switch (command) {
            case "5":
                printSellResult(myBusiness.sell(5));
                printSneakerOptions(myBusiness.getCollection());
                break;
            case "6":
                printSellResult(myBusiness.sell(6));
                printSneakerOptions(myBusiness.getCollection());
                break;
            case "7":
                printSellResult(myBusiness.sell(7));
                printSneakerOptions(myBusiness.getCollection());
                break;
            case "8":
                printSellResult(myBusiness.sell(8));
                printSneakerOptions(myBusiness.getCollection());
                break;
            default:
                processMenuCommandTertiary(command);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes selection if in [9,10], or quit or default
    private void processMenuCommandTertiary(String command) {
        switch (command) {
            case "9":
                printSellResult(myBusiness.sell(9));
                printSneakerOptions(myBusiness.getCollection());
                break;
            case "10":
                printSellResult(myBusiness.sell(10));
                printSneakerOptions(myBusiness.getCollection());
                break;
            case "q":
                printSneakerOptions(myBusiness.getCollection());
                break;
            default:
                printError();
                break;
        }
    }

    // EFFECTS: if sneakerSold is not null, prints a success statement, otherwise prints error statement
    private void printSellResult(Sneaker sneakerSold) {
        if (sneakerSold != null) {
            System.out.println("You have successfully sold " + sneakerSold.getName() + " for $"
                    + sneakerSold.getMarketPrice() + "!");
            printBalance(myBusiness);
        } else {
            printError();
        }
    }

    // EFFECTS: prints menu of your sneakers and the potential profit if you sell them
    @Override
    protected void printSneakerOptions(List<Sneaker> sneakers) {
        float mp;
        float pp;
        for (int i = 1; i <= sneakers.size(); i++) {
            mp = sneakers.get(i - 1).getMarketPrice();
            pp = sneakers.get(i - 1).getPurchasedPrice();
            System.out.println("(" + i + "): "
                    + sneakers.get(i - 1).getName() + ": "
                    + "Profit of $" + (mp - pp));
        }
    }
}
