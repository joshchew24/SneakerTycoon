package ui;

import ui.graphics.SneakerTycoonGUI;

/*
 * Driver class.
 */
// TODO: 3rd and 4th phase 2 user stories?
// TODO: refactor money to use BigDecimal
// - As a user, I want to be reminded to save my progress before quitting
// - As a user, I want to have the option of loading a new game file
public class Main {
    public static void main(String[] args) {
        // console ui:
//        new SneakerTycoonApp();

        // graphic ui:
        new SneakerTycoonGUI();

    }
}
