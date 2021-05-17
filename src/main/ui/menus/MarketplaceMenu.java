package ui.menus;

import model.Business;
import model.Marketplace;
import model.sneakers.Sneaker;
import ui.Menu;

/*
 * Represents a menu to interact with the marketplace.
 */

// TODO: exits the marketplace if all items have been purchased or something similar
public class MarketplaceMenu extends Menu {

    Marketplace myMarketplace;
    Business myBusiness;

    // EFFECTS: constructs an object which runs the menu to interact with marketplace
    public MarketplaceMenu(Business business) {
        myBusiness = business;
        myMarketplace = new Marketplace();
    }

    // EFFECTS: prints listings and menu displaying purchasing options
    @Override
    protected void displayMenu() {
        System.out.println("Welcome to \"Sneaker Marketplace\"!");
        System.out.println("Current Listings: \n");
        for (String s : myMarketplace.getListingsInfo()) {
            System.out.println(s);
        }
        System.out.println("Enter the number corresponding to the sneaker you wish to buy, "
                + "or enter \"q\" to go back to the main menu.");
        printBalance(myBusiness);
        printSneakerOptions(myMarketplace.getListings());
    }

    @Override
    protected void processMenuCommand(String command) {
        switch (command) {
            case "1":
                processMarketplacePurchase(1);
                break;
            case "2":
                processMarketplacePurchase(2);
                break;
            case "3":
                processMarketplacePurchase(3);
                break;
            case "4":
                processMarketplacePurchase(4);
                break;
            case "5":
                processMarketplacePurchase(5);
                break;
            default:
                printError();
        }

    }

    // EFFECTS: If any of the following conditions fail to hold, print an appropriate error message:
    //          (1) i <= listing size
    //          (2) your collection < max collection size
    //          (3) you can afford to buy the sneaker at position i in the listings
    //          if all three conditions hold, purchase the sneaker, removing it from the marketplace and adding
    //          it to your collection.
    private void processMarketplacePurchase(int i) {
        if (i <= myMarketplace.getListings().size()) {
            Sneaker s = myMarketplace.getListings().get(i - 1);
            if (myBusiness.getCollection().size() < Business.getMaxCollectionSize()) {
                if (myBusiness.buy(s)) {
                    System.out.println("You have successfully purchased the " + s.getName()
                            + " for $" + s.getMarketPrice());
                    printBalance(myBusiness);
                    myMarketplace.getListings().remove(i - 1);
                    printSneakerOptions(myMarketplace.getListings());
                } else {
                    System.out.println("You have insufficient funds to purchase this item.");
                }
            } else {
                System.out.println("You do not have room in your collection to purchase this item.");
            }
        } else {
            printError();
        }
    }
}
