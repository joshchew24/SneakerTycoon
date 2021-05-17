package ui.menus;

import model.Clicker;
import ui.Menu;

/*
 * Represents a menu used to upgrade your clicker.
 */

public class UpgradeMenu extends Menu {

    Clicker clicker;

    // EFFECTS: constructs an object which runs the menu in which you can upgrade your business
    public UpgradeMenu(Clicker clicker) {
        this.clicker = clicker;
    }

    @Override
    protected void displayMenu() {
        float potentialGainedPerClick = (clicker.getGainedPerClick() + 1);
        System.out.print("Enter \"u\" to increase the your clicking power to $"
                + potentialGainedPerClick + " per click. ");
        System.out.print("Upgrading will cost: $" + clicker.getUpgradeClickCost() + ". ");
        printBalance(clicker.getMyBusiness());
        System.out.println("Enter \"q\" to return to the main menu.");
    }

    @Override
    protected void processMenuCommand(String command) {
        if (command.equals("u")) {
            if (clicker.upgradeClick()) {
                System.out.println("You have successfully upgraded your clicking power to $"
                        + clicker.getGainedPerClick() + "!");
                displayMenu();
            } else {
                System.out.println("You cannot afford to upgrade. Click more or resell sneakers to earn more money!");
            }
        } else {
            printError();
        }
    }
}
